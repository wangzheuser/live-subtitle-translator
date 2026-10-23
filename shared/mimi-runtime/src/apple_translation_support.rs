//! Runtime text-language inventory and explicit Apple language-pack preparation.
//! Reading support/status and translating never requests a download.

use crate::apple_translation::{self, AppleTranslationCapabilities, AppleTranslationStatus};
use crate::core::models::{SourceLanguage, TargetLanguage};
use serde::Serialize;
use std::future::Future;
use std::sync::{Mutex, OnceLock};

#[derive(Debug, Default, Clone, PartialEq, Eq, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct AppleTranslationSupport {
    pub available: bool,
    pub source_languages: Vec<SourceLanguage>,
    pub target_languages: Vec<TargetLanguage>,
}

#[derive(Default)]
struct SupportCache {
    support: Option<AppleTranslationSupport>,
    // An unavailable inventory is a successful result; a failed scan stays
    // retryable without retaining an old availability claim.
    refresh_attempt: u64,
    last_refresh: Option<Result<(), String>>,
}

impl SupportCache {
    fn is_loaded(&self) -> bool {
        self.last_refresh.as_ref().is_some_and(Result::is_ok)
    }

    fn completed_since(&self, attempt: u64) -> Option<Result<AppleTranslationSupport, String>> {
        if self.is_loaded() || self.refresh_attempt != attempt {
            self.last_refresh
                .as_ref()
                .map(|result| result.clone().map(|()| self.snapshot()))
        } else {
            None
        }
    }

    fn snapshot(&self) -> AppleTranslationSupport {
        self.support.clone().unwrap_or_default()
    }

    fn record_refresh(&mut self, support: &Result<AppleTranslationSupport, String>) {
        self.support = support.as_ref().ok().cloned();
        self.refresh_attempt = self.refresh_attempt.wrapping_add(1);
        self.last_refresh = Some(support.as_ref().map(|_| ()).map_err(Clone::clone));
    }
}

static SUPPORT: OnceLock<Mutex<SupportCache>> = OnceLock::new();
static REFRESHING: tokio::sync::Mutex<()> = tokio::sync::Mutex::const_new(());
static PREPARING: tokio::sync::Mutex<()> = tokio::sync::Mutex::const_new(());

pub fn cached() -> AppleTranslationSupport {
    SUPPORT
        .get_or_init(Default::default)
        .lock()
        .unwrap()
        .snapshot()
}

pub fn is_loaded() -> bool {
    SUPPORT
        .get_or_init(Default::default)
        .lock()
        .unwrap()
        .is_loaded()
}

pub async fn refresh() -> Result<AppleTranslationSupport, String> {
    load(false).await
}

/// Retry failed inventory reads on a later snapshot request, while sharing a
/// read that completed after this caller started waiting for the refresh lock.
pub async fn ensure_loaded() -> Result<AppleTranslationSupport, String> {
    load(true).await
}

async fn load(reuse_completed: bool) -> Result<AppleTranslationSupport, String> {
    load_support(
        SUPPORT.get_or_init(Default::default),
        &REFRESHING,
        reuse_completed,
        async {
            apple_translation::capabilities()
                .await
                .map(map_capabilities)
                .map_err(|error| error_label(&error).to_string())
        },
    )
    .await
}

async fn load_support(
    cache: &Mutex<SupportCache>,
    refreshing: &tokio::sync::Mutex<()>,
    reuse_completed: bool,
    query: impl Future<Output = Result<AppleTranslationSupport, String>>,
) -> Result<AppleTranslationSupport, String> {
    // Explicit refresh and bootstrap share one lock; an old failed query
    // cannot finish after a newer scan and clear its recovered choices.
    let attempt = cache.lock().unwrap().refresh_attempt;
    let _refreshing = refreshing.lock().await;
    if reuse_completed {
        if let Some(result) = cache.lock().unwrap().completed_since(attempt) {
            return result;
        }
    }
    let result = query.await;
    cache.lock().unwrap().record_refresh(&result);
    result
}

/// Wire codes differ from Apple's BCP 47 identifiers for Chinese and Tagalog.
pub fn source_code(source: SourceLanguage) -> Result<&'static str, &'static str> {
    if source.raw_value() == "zh_tw" {
        return Ok("zh-Hant");
    }
    match source {
        SourceLanguage::Automatic => Err("apple_translation_language_unsupported"),
        SourceLanguage::Chinese => Ok("zh-Hans"),
        SourceLanguage::Filipino => Ok("fil"),
        source => Ok(source.raw_value()),
    }
}

pub fn target_code(target: TargetLanguage) -> Result<&'static str, &'static str> {
    match target {
        TargetLanguage::Original => Err("apple_translation_language_unsupported"),
        TargetLanguage::SimplifiedChinese => Ok("zh-Hans"),
        TargetLanguage::TraditionalChinese => Ok("zh-Hant"),
        TargetLanguage::Tagalog => Ok("fil"),
        target => Ok(target.raw_value()),
    }
}

fn canonical_language(code: &str) -> String {
    let normalized = code.replace('_', "-").to_ascii_lowercase();
    let base = normalized.split('-').next().unwrap_or_default();
    match base {
        "zh" if normalized.contains("hant")
            || normalized.ends_with("-tw")
            || normalized.ends_with("-hk") =>
        {
            "zh-hant".into()
        }
        "zh" => "zh-hans".into(),
        "tl" | "fil" => "fil".into(),
        "nb" | "no" => "no".into(),
        _ => base.into(),
    }
}

fn map_capabilities(native: AppleTranslationCapabilities) -> AppleTranslationSupport {
    if !native.available {
        return AppleTranslationSupport::default();
    }
    let available = |code: &str| {
        native.languages.iter().any(|language| {
            if code.contains('-') && !code.starts_with("zh-") {
                language.replace('_', "-").eq_ignore_ascii_case(code)
            } else {
                canonical_language(language) == canonical_language(code)
            }
        })
    };
    let source_languages = SourceLanguage::ALL
        .into_iter()
        .filter(|source| source_code(*source).is_ok_and(available))
        .collect::<Vec<_>>();
    let target_languages = TargetLanguage::ALL
        .into_iter()
        .filter(|target| target_code(*target).is_ok_and(available))
        .collect::<Vec<_>>();
    AppleTranslationSupport {
        available: !source_languages.is_empty() && !target_languages.is_empty(),
        source_languages,
        target_languages,
    }
}

pub async fn status(
    source: SourceLanguage,
    target: TargetLanguage,
) -> Result<AppleTranslationStatus, String> {
    let source = source_code(source).map_err(str::to_owned)?;
    let target = target_code(target).map_err(str::to_owned)?;
    apple_translation::status(source, target)
        .await
        .map_err(|error| error_label(&error).to_string())
}

/// Validate an actual pair before a saved or live selection changes. A same-
/// language route retains the existing subtitle passthrough without claiming
/// that Apple owns an installed same-language translation model.
pub async fn validate_pair(
    source: SourceLanguage,
    target: TargetLanguage,
    installed_required: bool,
) -> Result<(), String> {
    if target == TargetLanguage::Original {
        return Ok(());
    }
    source_code(source).map_err(str::to_owned)?;
    if target.matches_reported_asr(Some(source.raw_value())) {
        let support = refresh().await?;
        return if support.available
            && support.source_languages.contains(&source)
            && support.target_languages.contains(&target)
        {
            Ok(())
        } else {
            Err("apple_translation_unavailable".into())
        };
    }
    match status(source, target).await? {
        AppleTranslationStatus::Installed => Ok(()),
        AppleTranslationStatus::Supported if !installed_required => Ok(()),
        AppleTranslationStatus::Supported => Err("apple_translation_assets_missing".into()),
        AppleTranslationStatus::Unsupported => Err("apple_translation_language_unsupported".into()),
        AppleTranslationStatus::Unavailable => Err("apple_translation_unavailable".into()),
    }
}

pub async fn require_installed(
    source: SourceLanguage,
    target: TargetLanguage,
) -> Result<(), String> {
    validate_pair(source, target, true).await
}

pub async fn prepare(
    source: SourceLanguage,
    target: TargetLanguage,
    ui_language: &str,
) -> Result<(), String> {
    if !apple_translation::supports_preparation_ui_language(ui_language) {
        return Err("apple_translation_invalid_input".into());
    }
    let _preparing = PREPARING
        .try_lock()
        .map_err(|_| "apple_translation_busy".to_string())?;
    let source_code = source_code(source).map_err(str::to_owned)?;
    let target_code = target_code(target).map_err(str::to_owned)?;
    apple_translation::prepare(source_code, target_code, ui_language)
        .await
        .map_err(|error| error_label(&error).to_string())?;
    // Apple can return from preparation while another process downloads. Do not
    // announce readiness until the current pair is actually installed.
    require_installed(source, target).await
}

/// Only stable allowlisted labels may leave the native adapter or enter logs.
pub fn error_label(error: &apple_translation::AppleTranslationError) -> &'static str {
    match error.to_string().as_str() {
        "apple_translation_unavailable" => "apple_translation_unavailable",
        "apple_translation_assets_missing" => "apple_translation_assets_missing",
        "apple_translation_language_unsupported" => "apple_translation_language_unsupported",
        "apple_translation_invalid_input" => "apple_translation_invalid_input",
        "apple_translation_cancelled" => "apple_translation_cancelled",
        "apple_translation_busy" => "apple_translation_busy",
        "apple_translation_timeout" => "apple_translation_timeout",
        "apple_translation_invalid_result" => "apple_translation_invalid_result",
        _ => "apple_translation_failed",
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[tokio::test]
    async fn snapshot_loads_share_completed_attempts_and_retry_failed_scans() {
        use std::sync::atomic::{AtomicUsize, Ordering};
        let ready = map_capabilities(AppleTranslationCapabilities {
            available: true,
            languages: vec!["en".into(), "ja".into()],
        });
        for initial in [
            Ok(ready.clone()),
            Ok(AppleTranslationSupport::default()),
            Err("apple_translation_status_failed".into()),
        ] {
            let cache = Mutex::new(SupportCache::default());
            let refreshing = tokio::sync::Mutex::new(());
            let held = refreshing.lock().await;
            let queries = AtomicUsize::new(0);
            let mut first = Box::pin(load_support(&cache, &refreshing, true, async {
                queries.fetch_add(1, Ordering::SeqCst);
                initial.clone()
            }));
            let mut second = Box::pin(load_support(&cache, &refreshing, true, async {
                queries.fetch_add(1, Ordering::SeqCst);
                initial.clone()
            }));
            assert!(futures_util::poll!(first.as_mut()).is_pending());
            assert!(futures_util::poll!(second.as_mut()).is_pending());
            drop(held);
            let (first, second) = tokio::join!(first, second);
            assert_eq!(first, initial);
            assert_eq!(second, initial);
            assert_eq!(
                queries.load(Ordering::SeqCst),
                1,
                "Concurrent snapshots must share the completed query, including failure"
            );

            let retry = load_support(&cache, &refreshing, true, async {
                queries.fetch_add(1, Ordering::SeqCst);
                Ok(ready.clone())
            })
            .await;
            assert_eq!(retry, Ok(initial.clone().unwrap_or_else(|_| ready.clone())));
            assert_eq!(
                queries.load(Ordering::SeqCst),
                if initial.is_ok() { 1 } else { 2 },
                "Only a new request after failure should start another scan"
            );
            assert!(cache.lock().unwrap().is_loaded());
        }
    }

    #[tokio::test]
    async fn explicit_refreshes_serialize_before_publishing_the_newest_inventory() {
        let ready = map_capabilities(AppleTranslationCapabilities {
            available: true,
            languages: vec!["en".into(), "ja".into()],
        });
        let cache = Mutex::new(SupportCache::default());
        let refreshing = tokio::sync::Mutex::new(());
        let (release, released) = tokio::sync::oneshot::channel();
        let mut first = Box::pin(load_support(&cache, &refreshing, false, async {
            released.await.unwrap();
            Err("apple_translation_status_failed".into())
        }));
        assert!(futures_util::poll!(first.as_mut()).is_pending());
        let mut second = Box::pin(load_support(&cache, &refreshing, false, async {
            Ok(ready.clone())
        }));
        assert!(
            futures_util::poll!(second.as_mut()).is_pending(),
            "A newer refresh must wait for the older native query"
        );
        release.send(()).unwrap();
        let (first, second) = tokio::join!(first, second);
        assert!(first.is_err());
        assert_eq!(second, Ok(ready.clone()));
        assert_eq!(cache.lock().unwrap().snapshot(), ready);
        assert!(cache.lock().unwrap().is_loaded());
    }

    #[tokio::test]
    async fn a_cancelled_inventory_query_releases_the_next_snapshot_retry() {
        let ready = map_capabilities(AppleTranslationCapabilities {
            available: true,
            languages: vec!["en".into(), "ja".into()],
        });
        let cache = Mutex::new(SupportCache::default());
        let refreshing = tokio::sync::Mutex::new(());
        let mut cancelled = Box::pin(load_support(
            &cache,
            &refreshing,
            true,
            std::future::pending(),
        ));
        assert!(futures_util::poll!(cancelled.as_mut()).is_pending());
        let mut retry = Box::pin(load_support(&cache, &refreshing, true, async {
            Ok(ready.clone())
        }));
        assert!(futures_util::poll!(retry.as_mut()).is_pending());
        drop(cancelled);
        assert_eq!(retry.await, Ok(ready));
        assert!(cache.lock().unwrap().is_loaded());
    }

    #[test]
    fn failed_inventory_queries_clear_claims_and_remain_retryable() {
        let ready = map_capabilities(AppleTranslationCapabilities {
            available: true,
            languages: vec!["en".into(), "ja".into()],
        });
        let mut cache = SupportCache::default();
        assert!(!cache.is_loaded());
        let failed = Err("apple_translation_timeout".into());
        cache.record_refresh(&failed);
        assert!(
            !cache.is_loaded(),
            "A failed initial scan must permit retry"
        );
        assert_eq!(cache.snapshot(), AppleTranslationSupport::default());

        cache.record_refresh(&Ok(ready.clone()));
        assert!(cache.is_loaded());
        assert_eq!(cache.snapshot(), ready);
        cache.record_refresh(&failed);
        assert!(!cache.is_loaded(), "A failed rescan must permit retry");
        assert_eq!(cache.snapshot(), AppleTranslationSupport::default());
        cache.record_refresh(&failed);
        assert!(!cache.is_loaded());

        cache.record_refresh(&Ok(AppleTranslationSupport::default()));
        assert!(
            cache.is_loaded(),
            "A successful unavailable scan is authoritative"
        );
        assert_eq!(cache.snapshot(), AppleTranslationSupport::default());
        cache.record_refresh(&Ok(ready.clone()));
        assert!(cache.is_loaded());
        assert_eq!(cache.snapshot(), ready);
    }

    #[test]
    fn runtime_inventory_preserves_chinese_scripts_and_hides_auto_original() {
        let support = map_capabilities(AppleTranslationCapabilities {
            available: true,
            languages: vec![
                "en-US".into(),
                "ja".into(),
                "zh-Hant".into(),
                "fil".into(),
                "xx".into(),
            ],
        });
        assert!(support.available);
        assert!(support.source_languages.contains(&SourceLanguage::English));
        assert!(support.source_languages.contains(&SourceLanguage::Filipino));
        assert!(!support
            .source_languages
            .contains(&SourceLanguage::Automatic));
        assert!(!support.source_languages.contains(&SourceLanguage::Chinese));
        assert!(support
            .target_languages
            .contains(&TargetLanguage::TraditionalChinese));
        assert!(!support
            .target_languages
            .contains(&TargetLanguage::SimplifiedChinese));
        assert!(!support.target_languages.contains(&TargetLanguage::Original));
        assert_eq!(
            target_code(TargetLanguage::TraditionalChinese),
            Ok("zh-Hant")
        );
        assert_eq!(source_code(SourceLanguage::Chinese), Ok("zh-Hans"));
    }
    #[test]
    fn unavailable_or_unrepresentable_inventory_has_no_choices() {
        for available in [false, true] {
            assert_eq!(
                map_capabilities(AppleTranslationCapabilities {
                    available,
                    languages: vec!["xx".into()]
                }),
                AppleTranslationSupport::default()
            );
        }
    }

    #[test]
    fn runtime_inventory_does_not_invent_regions_from_a_base_language() {
        let support = map_capabilities(AppleTranslationCapabilities {
            available: true,
            languages: vec!["en".into(), "pt".into(), "ar".into()],
        });
        for code in ["en", "pt", "ar"] {
            let source = serde_json::from_value(serde_json::json!(code)).unwrap();
            let target = serde_json::from_value(serde_json::json!(code)).unwrap();
            assert!(support.source_languages.contains(&source));
            assert!(support.target_languages.contains(&target));
        }
        for code in [
            "en-US", "en-GB", "pt-BR", "pt-PT", "ar-EG", "ar-SA", "ar-AE",
        ] {
            let source = serde_json::from_value(serde_json::json!(code)).unwrap();
            let target = serde_json::from_value(serde_json::json!(code)).unwrap();
            assert!(!support.source_languages.contains(&source), "{code}");
            assert!(!support.target_languages.contains(&target), "{code}");
        }
    }

    #[test]
    fn runtime_inventory_accepts_only_the_exact_reported_region_and_preserves_scripts() {
        let support = map_capabilities(AppleTranslationCapabilities {
            available: true,
            languages: vec![
                "en_US".into(),
                "pt-BR".into(),
                "ar-EG".into(),
                "zh-Hant".into(),
            ],
        });
        for code in ["en-US", "pt-BR", "ar-EG", "zh_tw"] {
            let source = serde_json::from_value(serde_json::json!(code)).unwrap();
            let target = serde_json::from_value(serde_json::json!(code)).unwrap();
            assert!(support.source_languages.contains(&source), "{code}");
            assert!(support.target_languages.contains(&target), "{code}");
        }
        for code in ["en-GB", "pt-PT", "ar-SA", "zh"] {
            let source = serde_json::from_value(serde_json::json!(code)).unwrap();
            let target = serde_json::from_value(serde_json::json!(code)).unwrap();
            assert!(!support.source_languages.contains(&source), "{code}");
            assert!(!support.target_languages.contains(&target), "{code}");
        }
    }
}

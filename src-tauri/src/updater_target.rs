//! Move an Intel build running under Rosetta to the native Apple silicon package.

use std::cmp::Ordering;

const APPLE_SILICON_TARGET: &str = "darwin-aarch64";

pub fn builder() -> tauri_plugin_updater::Builder {
    let builder = tauri_plugin_updater::Builder::new();
    match migration_target(
        std::env::consts::OS,
        std::env::consts::ARCH,
        process_translation_status(),
    ) {
        Some(target) => builder
            .target(target)
            // The Intel release containing this migration can already be the
            // latest version. Permit its native equivalent without downgrading.
            // After relaunching the ARM build, ordinary version checks resume.
            .default_version_comparator(|current, release| {
                accepts_migration_version(release.version.cmp(&current))
            }),
        None => builder,
    }
}

fn migration_target(
    operating_system: &str,
    architecture: &str,
    translated: Option<bool>,
) -> Option<&'static str> {
    (operating_system == "macos" && architecture == "x86_64" && translated == Some(true))
        .then_some(APPLE_SILICON_TARGET)
}

fn accepts_migration_version(ordering: Ordering) -> bool {
    ordering != Ordering::Less
}

fn process_translation_status() -> Option<bool> {
    #[cfg(all(target_os = "macos", target_arch = "x86_64"))]
    {
        let mut translated: std::ffi::c_int = 0;
        let mut size = std::mem::size_of_val(&translated);
        // SAFETY: the name is NUL-terminated, both output pointers refer to
        // initialized writable values of the declared size, and null newp with
        // zero newlen makes this a read-only query of the current process.
        let result = unsafe {
            libc::sysctlbyname(
                c"sysctl.proc_translated".as_ptr(),
                std::ptr::from_mut(&mut translated).cast(),
                &mut size,
                std::ptr::null_mut(),
                0,
            )
        };
        decode_translation_status(result, size, translated)
    }
    #[cfg(not(all(target_os = "macos", target_arch = "x86_64")))]
    {
        None
    }
}

#[cfg(any(test, all(target_os = "macos", target_arch = "x86_64")))]
fn decode_translation_status(
    result: std::ffi::c_int,
    size: usize,
    value: std::ffi::c_int,
) -> Option<bool> {
    if result != 0 || size != std::mem::size_of::<std::ffi::c_int>() {
        return None;
    }
    match value {
        0 => Some(false),
        1 => Some(true),
        _ => None,
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn only_a_confirmed_rosetta_process_migrates() {
        assert_eq!(
            migration_target("macos", "x86_64", Some(true)),
            Some("darwin-aarch64")
        );
        for translated in [None, Some(false)] {
            assert_eq!(migration_target("macos", "x86_64", translated), None);
        }
        for (operating_system, architecture) in [
            ("macos", "aarch64"),
            ("windows", "x86_64"),
            ("linux", "x86_64"),
        ] {
            assert_eq!(
                migration_target(operating_system, architecture, Some(true)),
                None
            );
        }
    }

    #[test]
    fn failed_or_malformed_queries_never_enable_migration() {
        let size = std::mem::size_of::<std::ffi::c_int>();
        for (result, returned_size, value) in [
            (-1, size, 1),
            (1, size, 1),
            (0, 0, 1),
            (0, size - 1, 1),
            (0, size + 1, 1),
            (0, size, -1),
            (0, size, 2),
        ] {
            let translated = decode_translation_status(result, returned_size, value);
            assert_eq!(translated, None);
            assert_eq!(migration_target("macos", "x86_64", translated), None);
        }
    }

    #[test]
    fn valid_queries_distinguish_native_and_translated_processes() {
        let size = std::mem::size_of::<std::ffi::c_int>();
        assert_eq!(decode_translation_status(0, size, 0), Some(false));
        assert_eq!(decode_translation_status(0, size, 1), Some(true));
    }

    #[test]
    fn native_replacement_allows_the_same_or_newer_version_and_never_downgrades() {
        assert!(!accepts_migration_version(Ordering::Less));
        assert!(accepts_migration_version(Ordering::Equal));
        assert!(accepts_migration_version(Ordering::Greater));
    }
}

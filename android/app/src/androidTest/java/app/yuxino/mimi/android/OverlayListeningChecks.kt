package app.yuxino.mimi.android

import android.app.Activity
import android.app.Instrumentation
import android.app.LocaleManager
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.inspector.WindowInspector
import android.widget.TextView
import app.yuxino.mimi.android.capture.MimiService
import app.yuxino.mimi.android.provider.SubtitleBus
import java.io.File

/** API 33+ native service-resource and waiting-card regression; no capture or network. */
internal class OverlayListeningChecks(private val test: Instrumentation) {
    private val context get() = test.targetContext
    private var stage = "preconditions"
    private fun ui(action: () -> Unit) {
        var failure: Throwable? = null
        test.runOnMainSync { try { action() } catch (error: Throwable) { failure = error } }
        test.waitForIdleSync()
        failure?.let { throw it }
    }
    private fun await(predicate: () -> Boolean) {
        repeat(80) {
            var ready = false
            ui { ready = predicate() }
            if (ready) return
            SystemClock.sleep(50)
        }
        error("Waiting overlay did not settle at $stage")
    }
    private fun root() = WindowInspector.getGlobalWindowViews().first { it.tag == "mimi-overlay" }
    private fun compact() = root().findViewWithTag<ViewGroup>("compact-subtitle")
    private fun running(value: Boolean) {
        // Fixture only: never invokes startCapture, MediaProjection or a provider engine.
        MimiService::class.java.getDeclaredField("isRunning").apply { isAccessible = true; set(null, value) }
    }
    private fun capture(name: String) {
        test.waitForIdleSync(); SystemClock.sleep(350)
        val bitmap = checkNotNull(test.uiAutomation.takeScreenshot())
        File(context.getExternalFilesDir("ui-preview"), "listening-$name.png").outputStream().use {
            check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, it))
        }
        bitmap.recycle()
    }

    fun run(arguments: Bundle?) {
        check(Build.VERSION.SDK_INT >= 33 && !MimiService.isRunning)
        val localeManager = context.getSystemService(LocaleManager::class.java)
        val previousLocales = localeManager.applicationLocales
        val prefs = context.getSharedPreferences("first_run", 0)
        val hadSeen = prefs.contains("seen")
        val seen = prefs.getBoolean("seen", false)
        val immersive = SettingsStore.immersiveSubtitles(context)
        val mode = SettingsStore.displayMode(context)
        val dividers = SettingsStore.subtitleDividers(context)
        val position = SettingsStore.overlayYOffset(context)
        val source = SettingsStore.sourceLang(context)
        val target = SettingsStore.targetLang(context)
        val tag = (arguments?.getString("locale") ?: "zh-Hans").let { if (it == "system") "" else it }
        var home: MainActivity? = null
        var host: CaptureProbeActivity? = null
        var failure: Throwable? = null
        try {
            ui {
                localeManager.applicationLocales = LocaleList.forLanguageTags(tag)
                SettingsStore.setImmersiveSubtitles(context, false)
                SettingsStore.setDisplayMode(context, CaptionDisplayMode.BILINGUAL)
                SettingsStore.setSubtitleDividers(context, false)
                check(prefs.edit().putBoolean("seen", true).commit())
            }
            stage = "launch_home_and_service_locale"
            home = test.startActivitySync(Intent(context, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) as MainActivity
            val expected = home.getString(R.string.overlay_listening_empty)
            ui {
                // A display/window context can have a different resource locale from an
                // AppCompat activity. App-selected language must remain authoritative.
                val foreignTag = if (home.resources.configuration.locales[0].language == "en") "de" else "en"
                val foreign = context.createConfigurationContext(Configuration(context.resources.configuration).apply {
                    setLocales(LocaleList.forLanguageTags(foreignTag))
                })
                check(foreign.getString(R.string.overlay_listening_empty) != expected)
                check(InterfaceLanguage.context(foreign).getString(R.string.overlay_listening_empty) == expected) {
                    "Service resources ignored the selected interface language"
                }
            }
            stage = "normal_preview_created"
            context.startService(Intent(context, MimiService::class.java).setAction(MimiService.ACTION_UI_PREVIEW))
            await { WindowInspector.getGlobalWindowViews().any { it.tag == "mimi-overlay" } }
            ui { running(true); SubtitleBus.clear() }
            host = test.startActivitySync(Intent(context, CaptureProbeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) as CaptureProbeActivity
            stage = "waiting_panel_visible"
            await { root().isShown }
            val window = root()
            fun assertWaiting() = ui {
                val panel = root().findViewWithTag<ViewGroup>("expanded-subtitles")
                val status = panel.findViewWithTag<TextView>("expanded-status")
                check(panel.isShown && !compact().isShown) { "A session must show the interactive panel" }
                check(panel.findViewWithTag<View>("collapse-overlay") == null) { "Obsolete collapse control remains" }
                check(status.isShown && status.text.toString() == expected) { "Waiting copy changed language" }
                check(status.layout.height <= status.height - status.compoundPaddingTop - status.compoundPaddingBottom)
                check(localeManager.applicationLocales.toLanguageTags() == tag)
            }
            assertWaiting()
            capture("$tag-panel-waiting")
            for (id in listOf(R.id.tab_general, R.id.tab_service, R.id.tab_caption)) {
                stage = "settings_hide_$id"
                ui { host!!.finish(); host = null }
                await { home.hasWindowFocus() && !root().isShown }
                ui { home.findViewById<View>(id).performClick() }
                check(home.getString(R.string.overlay_listening_empty) == expected)
                host = test.startActivitySync(Intent(context, CaptureProbeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) as CaptureProbeActivity
                stage = "settings_restore_$id"
                await { root().isShown }
                assertWaiting()
            }
            capture("$tag-panel-returned-from-settings")
            ui {
                SubtitleBus.onSourceDraft("Synthetic source.", "en")
                SubtitleBus.onTranslationDraft("合成示例。")
            }
            stage = "waiting_replaced_by_caption"
            await { root().findViewWithTag<TextView>("expanded-status").visibility == View.GONE }
            ui {
                check(SettingsStore.overlayYOffset(context) == position)
                check(SettingsStore.sourceLang(context) == source && SettingsStore.targetLang(context) == target)
            }
            val readingHeight = root().height
            capture("$tag-expanded-caption")
            stage = "bounded_history_without_dividers"
            context.startService(Intent(context, MimiService::class.java).setAction(MimiService.ACTION_UI_PREVIEW_HISTORY))
            await { SubtitleBus.historySnapshot().isNotEmpty() &&
                root().findViewWithTag<CaptionPanelView>("expanded-subtitles").history.isShown }
            ui {
                check(root().height == readingHeight) { "History grew the window over the playback app" }
                check(root() === window) { "Reading resize replaced the existing window" }
                val panel = root().findViewWithTag<CaptionPanelView>("expanded-subtitles")
                check(panel.history.text.isNotBlank()) { "Prior confirmed history was not rendered" }
                check(!panel.findViewWithTag<View>("history-divider").isShown) { "Disabled history divider remained visible" }
            }
            stage = "bounded_history_with_dividers"
            ui { SettingsStore.setSubtitleDividers(context, true) }
            await { root().findViewWithTag<View>("history-divider").isShown }
            ui {
                check(root().height == readingHeight && root() === window) { "History divider changed the reading window" }
            }
            capture("$tag-expanded-history")
            stage = "immersive_waiting_restoration"
            ui {
                SubtitleBus.clear()
                SettingsStore.setImmersiveSubtitles(context, true)
            }
            await { WindowInspector.getGlobalWindowViews().any { it.tag == "exit-immersive" } }
            ui { check(compact().minimumHeight == 0) { "Immersive captions acquired a fixed panel height" } }
            check(MimiService.captureProjectionForTests == null)
        } catch (error: Throwable) { failure = error }
        finally {
            ui {
                running(false)
                context.stopService(Intent(context, MimiService::class.java))
                host?.finish(); home?.finish()
                SettingsStore.setImmersiveSubtitles(context, immersive)
                SettingsStore.setDisplayMode(context, mode)
                SettingsStore.setSubtitleDividers(context, dividers)
                localeManager.applicationLocales = previousLocales
                val edit = prefs.edit()
                if (hadSeen) edit.putBoolean("seen", seen) else edit.remove("seen")
                check(edit.commit())
            }
            check(SettingsStore.flushPendingWritesForTests(context))
        }
        test.finish(if (failure == null) Activity.RESULT_OK else Activity.RESULT_CANCELED, Bundle().apply {
            putString("stream", if (failure == null)
                "Waiting overlay passed ($tag/API ${Build.VERSION.SDK_INT}): explicit/system locale, single panel, bounded history viewport, settings return and caption/immersive restoration; no capture or providers.\n"
            else "Waiting overlay failed at $stage: ${failure.javaClass.simpleName}: ${failure.message}; ${failure.stackTrace.firstOrNull { it.className.contains("OverlayListeningChecks") }}\n")
        })
    }
}

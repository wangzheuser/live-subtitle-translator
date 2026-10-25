package app.yuxino.mimi.android

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.inspector.WindowInspector
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import app.yuxino.mimi.android.capture.MimiService
import com.google.android.material.materialswitch.MaterialSwitch
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID

/** Synthetic archive only; preserves real settings and files, starts no capture or provider. */
internal class SavedSessionChecks(private val test: Instrumentation) {
    private val context get() = test.targetContext
    private var stage = "preconditions"
    private var listSelections = 0
    private var buttonSelections = 0
    private var lastTextClickable = false
    private var monitorHits = 0
    private var exportBusy = false
    private var exportExists = false
    private var exportBytes = 0L
    private lateinit var activity: SavedSessionsActivity
    private lateinit var model: SavedSessionsViewModel
    private fun onUi(action: () -> Unit) {
        var failure: Throwable? = null
        test.runOnMainSync { try { action() } catch (error: Throwable) { failure = error } }
        failure?.let { throw it }
    }
    private fun await(condition: () -> Boolean) {
        val deadline = SystemClock.elapsedRealtime() + 10_000
        while (true) {
            var done = false
            onUi { done = condition() }
            if (done) break
            check(SystemClock.elapsedRealtime() < deadline) { "history_timeout" }
            SystemClock.sleep(25)
        }
        test.waitForIdleSync()
    }
    private fun view(tag: String): View = checkNotNull(activity.window.decorView.findViewWithTag<View>(tag))
    private fun texts(root: View): List<TextView> = buildList {
        if (root is TextView) add(root)
        if (root is ViewGroup) for (index in 0 until root.childCount) addAll(texts(root.getChildAt(index)))
    }
    private fun clickText(resource: Int) {
        onUi {
            val expected = activity.getString(resource)
            val item = checkNotNull(WindowInspector.getGlobalWindowViews().flatMap(::texts)
                .lastOrNull { it.text.toString() == expected && it.isShown }) { "dialog_action_missing" }
            lastTextClickable = item.isClickable
            val list = generateSequence<View>(item) { it.parent as? View }
                .filterIsInstance<android.widget.ListView>().firstOrNull()
            if (list != null) {
                val position = list.getPositionForView(item)
                check(position >= 0 && list.isEnabled && list.performItemClick(item, position, list.adapter.getItemId(position))) {
                    "dialog_list_selection_failed"
                }
                listSelections++
            } else {
                val clickable = generateSequence<View>(item) { it.parent as? View }.firstOrNull { it.isClickable }
                check(clickable != null && clickable.isEnabled && clickable.performClick()) { "dialog_button_selection_failed" }
                buttonSelections++
            }
        }
        test.waitForIdleSync()
    }
    private fun capture(name: String) {
        await { activity.hasWindowFocus() && activity.window.decorView.isShown }
        SystemClock.sleep(300)
        val bitmap = checkNotNull(test.uiAutomation.takeScreenshot())
        try {
            File(context.cacheDir, "mimi-saved-sessions-$name.png").outputStream().use {
                check(bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, it))
            }
        } finally { bitmap.recycle() }
    }
    fun run() {
        val id = UUID.randomUUID().toString()
        val history = File(context.filesDir, "session-history")
        val existed = history.exists()
        val file = File(history, "$id.json")
        val outputs = mutableListOf<File>()
        var prefs: SharedPreferences? = null
        var previous: Boolean? = null
        var failure: String? = null
        var checks = 0
        var listScreenshot = false
        try {
            check(!MimiService.isRunning && !MimiService.isStopping) { "active_session" }
            // Inspect and restore only the opt-in slot, never credentials or other settings.
            SettingsStore.retainSessionHistory(context)
            prefs = SettingsStore::class.java.getDeclaredField("prefs").apply { isAccessible = true }.get(null) as SharedPreferences
            previous = if (prefs.contains("retain_session_history")) prefs.getBoolean("retain_session_history", false) else null
            check(prefs.edit().remove("retain_session_history").commit())
            stage = "default_off"
            check(!SettingsStore.retainSessionHistory(context)); checks++
            check(history.mkdirs() || history.isDirectory)
            android.system.Os.chmod(history.absolutePath, 448)
            val fixture = JSONObject().put("version", 1).put("id", id).put("startedAtMs", 1000L)
                .put("endedAtMs", 61_000L).put("limited", false).put("entries", JSONArray().apply {
                    for (number in 1..60) put(JSONObject().put("source", "Synthetic source $number")
                        .put("translation", "Synthetic translation $number").put("createdAt", 1000L + number * 1000)
                        .put("audioSource", if (number % 2 == 0) "microphone" else "system"))
                }).toString()
            file.writeText(fixture); android.system.Os.chmod(file.absolutePath, 384)
            activity = test.startActivitySync(Intent(context, SavedSessionsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) as SavedSessionsActivity
            onUi { model = ViewModelProvider(activity)[SavedSessionsViewModel::class.java] }
            await { !model.busy && model.initialized }
            // A list screenshot must never include an existing user's archive metadata.
            if (model.items.all { it.id == id }) { capture("list"); listScreenshot = true }
            stage = "native_list_and_toggle"
            onUi {
                val controls = SessionStorageControls(activity)
                check(!controls.findViewWithTag<MaterialSwitch>("session-history-save").isChecked)
                check(view("session-view-$id").performClick())
                check(model.busy)
                check(!view("session-export-$id").isEnabled)
            }
            await { !model.busy && model.page != null }
            capture("detail")
            stage = "latest_and_pagination"
            check(model.page!!.entries.first().index == 60)
            onUi { check(view("session-next").isEnabled); view("session-next").performClick() }
            await { !model.busy }
            check(model.page!!.entries.last().index == 1)
            onUi { check(!view("session-next").isEnabled); check(view("session-previous").isEnabled); view("session-previous").performClick() }
            await { !model.busy }
            check(model.page!!.entries.first().index == 60); checks++
            stage = "storage_error_and_retry"
            check(file.delete())
            onUi { model.refresh() }
            await { !model.busy }
            check(model.failed)
            file.writeText(fixture); android.system.Os.chmod(file.absolutePath, 384)
            onUi { view("session-retry").performClick() }
            await { !model.busy }
            check(!model.failed); checks++
            for (markdown in listOf(false, true)) {
                stage = if (markdown) "markdown_document_result" else "text_document_result"
                val output = File(context.cacheDir, "history-export-$id.${if (markdown) "md" else "txt"}")
                outputs += output
                val exports = File(context.filesDir, "session-exports")
                val existingExports = exports.listFiles().orEmpty().map { it.name }.toSet()
                val filter = IntentFilter(Intent.ACTION_CREATE_DOCUMENT).apply { addCategory(Intent.CATEGORY_OPENABLE); addDataType(if (markdown) "text/markdown" else "text/plain") }
                // Exercise the real ActivityResult + ContentResolver stream path;
                // the system picker's returned destination is synthetic, not a claim about provider UI.
                val monitor = test.addMonitor(filter, Instrumentation.ActivityResult(Activity.RESULT_OK,
                    Intent().setData(Uri.fromFile(output))), true)
                try {
                    onUi { check(view("session-export-$id").performClick()) { "export_action_failed" } }
                    clickText(if (markdown) R.string.session_history_markdown else R.string.session_history_txt)
                    stage = if (markdown) "markdown_document_interception" else "text_document_interception"
                    await { monitor.hits >= 1 }
                    check(monitor.hits == 1) { "duplicate_document_launch" }
                    stage = if (markdown) "markdown_document_write" else "text_document_write"
                    await { !model.busy && output.exists() && output.length() > 0 }
                    stage = if (markdown) "markdown_document_content" else "text_document_content"
                    val result = output.readText()
                    check("Synthetic source 1" in result && "Synthetic translation 60" in result) { "export_bilingual_content_missing" }
                    check("Microphone" in result && "System" in result) { "export_source_labels_missing" }
                    check(exports.listFiles().orEmpty().map { it.name }.toSet() == existingExports) { "export_temporary_not_cleaned" }
                    checks++
                } finally {
                    monitorHits = monitor.hits
                    onUi { exportBusy = model.busy }
                    exportExists = output.exists(); exportBytes = output.length()
                    test.removeMonitor(monitor)
                }
            }
            stage = "export_error_preserves_existing_document"
            val existingDocument = File(context.cacheDir, "history-existing-$id.txt").apply {
                writeText("Synthetic existing document")
            }
            outputs += existingDocument
            onUi { model.export(UUID.randomUUID().toString(), false, Uri.fromFile(existingDocument)) }
            await { !model.busy }
            check(existingDocument.readText() == "Synthetic existing document") { "existing_document_changed_after_format_failure" }
            checks++
            stage = "delete_confirmation"
            onUi { view("session-more-$id").performClick() }
            clickText(R.string.session_history_delete)
            check(file.exists())
            clickText(android.R.string.cancel)
            check(file.exists())
            onUi { view("session-more-$id").performClick() }
            clickText(R.string.session_history_delete)
            clickText(R.string.session_history_delete)
            await { !model.busy && !file.exists() }
            check(model.items.none { it.id == id }); checks++
        } catch (error: Throwable) {
            val line = error.stackTrace.firstOrNull { it.className.startsWith(SavedSessionChecks::class.java.name) }?.lineNumber
            val label = error.message?.takeIf { it.matches(Regex("[a-z_]+")) } ?: "unexpected"
            failure = "$stage:${error.javaClass.simpleName}:line=${line ?: -1}:$label"
        } finally {
            runCatching { if (::activity.isInitialized) onUi { activity.finish() } }
            outputs.forEach { it.delete() }; file.delete()
            if (!existed && history.listFiles().orEmpty().isEmpty()) history.delete()
            prefs?.let { settings ->
                val edit = settings.edit()
                if (previous == null) edit.remove("retain_session_history") else edit.putBoolean("retain_session_history", previous!!)
                if (!edit.commit()) failure = failure ?: "restore_preference"
            }
        }
        test.finish(if (failure == null) Activity.RESULT_OK else Activity.RESULT_CANCELED, Bundle().apply {
            putString("stream", "SAVED_SESSIONS_RESULT passed=${failure == null}; checks=$checks; listScreenshot=$listScreenshot; listSelections=$listSelections; buttonSelections=$buttonSelections; lastTextClickable=$lastTextClickable; monitorHits=$monitorHits; exportBusy=$exportBusy; exportExists=$exportExists; exportBytes=$exportBytes; failure=${failure ?: "none"}; synthetic archive and document result; no provider, capture or real session changes.\n")
        })
    }
}

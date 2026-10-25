package app.yuxino.mimi.android

import android.app.Application
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.DocumentsContract
import androidx.lifecycle.AndroidViewModel
import java.util.concurrent.Executors

/** Retains one bounded page across rotation. Workers only hold the application context. */
internal class SavedSessionsViewModel(application: Application) : AndroidViewModel(application) {
    private val main = Handler(Looper.getMainLooper())
    private val worker = Executors.newSingleThreadExecutor()
    private val sessions = SavedSessions(application.filesDir)
    private var cleared = false
    var observer: (() -> Unit)? = null
    var items: List<SavedSessionItem> = emptyList(); private set
    var selected: SavedSessionItem? = null; private set
    var page: SavedSessionPage? = null; private set
    var busy = false; private set
    var failed = false; private set
    var notice: Int? = null
    var initialized = false; private set

    private fun <T> run(work: () -> T, complete: (T) -> Unit, failure: (() -> Unit)? = null) {
        if (busy || cleared) return
        busy = true; failed = false; observer?.invoke()
        worker.execute {
            val result = runCatching(work)
            main.post {
                if (!cleared) {
                    busy = false
                    result.onSuccess(complete).onFailure {
                        if (failure == null) failed = true else failure()
                    }
                    observer?.invoke()
                }
            }
        }
    }
    fun refresh() {
        val item = selected
        if (item == null) run({ sessions.list() }, { items = it; initialized = true })
        else loadPage(page?.page ?: 0)
    }
    fun open(item: SavedSessionItem) {
        if (busy) return
        selected = item; page = null; loadPage(0)
    }
    fun list() {
        if (busy) return
        selected = null; page = null; refresh()
    }
    fun loadPage(number: Int) {
        val id = selected?.id ?: return
        run({ sessions.page(id, number.coerceAtLeast(0)) }, { page = it })
    }
    fun delete(item: SavedSessionItem) = run({ sessions.delete(item.id); sessions.list() }, {
        items = it; if (selected?.id == item.id) { selected = null; page = null }
        notice = R.string.session_history_deleted
    }, { notice = R.string.session_history_delete_error })

    fun export(id: String, markdown: Boolean, destination: Uri) = run({
        val resolver = getApplication<Application>().contentResolver
        exportSavedSession({ sessions.export(id, markdown) }, { file ->
            checkNotNull(resolver.openOutputStream(destination, "wt")).use { output ->
                file.inputStream().use { input -> input.copyTo(output) }
            }
        }, { DocumentsContract.deleteDocument(resolver, destination) })
    }, { notice = R.string.session_history_exported }, { notice = R.string.session_history_export_error })

    override fun onCleared() {
        cleared = true; observer = null; page = null; items = emptyList()
        // A running SAF copy still executes its finally block and deletes its private temporary file.
        worker.shutdown()
    }
}

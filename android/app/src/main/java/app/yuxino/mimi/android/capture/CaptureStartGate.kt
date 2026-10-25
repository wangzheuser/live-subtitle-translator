package app.yuxino.mimi.android.capture

/** Native service delivery ownership, independent of provider/session business state. */
internal class CaptureStartGate {
    private var sequence = 0L
    private var pending: Long? = null
    val isPending: Boolean get() = synchronized(this) { pending != null }
    @Synchronized fun begin(): Long {
        check(pending == null) { "capture_start_pending" }
        return (++sequence).also { pending = it }
    }
    @Synchronized fun waiting(request: Long): Boolean = pending == request
    @Synchronized fun complete(request: Long): Boolean {
        if (pending != request) return false
        pending = null
        return true
    }
}

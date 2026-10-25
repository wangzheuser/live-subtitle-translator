package app.yuxino.mimi.android.capture

/** Native capture selection. Sources never share PCM or a recognition session. */
enum class CaptureSource(val id: String, val channels: Int) {
    SYSTEM("system", 2), MICROPHONE("microphone", 1),
}

enum class CaptureInput(val id: String, val sources: List<CaptureSource>) {
    SYSTEM("system", listOf(CaptureSource.SYSTEM)),
    MICROPHONE("microphone", listOf(CaptureSource.MICROPHONE)),
    BOTH("both", listOf(CaptureSource.SYSTEM, CaptureSource.MICROPHONE));

    val needsProjection get() = CaptureSource.SYSTEM in sources
    val usesMicrophone get() = CaptureSource.MICROPHONE in sources

    /** Missing legacy preferences retain system capture; malformed requests fail closed. */
    companion object {
        fun fromId(value: String?): CaptureInput = entries.firstOrNull { it.id == value } ?: SYSTEM
        fun request(value: String?): CaptureInput? = if (value == null) SYSTEM else entries.firstOrNull { it.id == value }
    }
}

/** Surface a stalled selected lane even if another source still has audio. */
internal fun captureObservationForSources(values: Collection<CaptureHealth.Snapshot>): CaptureHealth.Snapshot? {
    if (values.isEmpty()) return null
    val state = when {
        values.any { it.state == CaptureHealth.State.NO_PCM } -> CaptureHealth.State.NO_PCM
        values.any { it.state == CaptureHealth.State.AUDIO } -> CaptureHealth.State.AUDIO
        values.any { it.state == CaptureHealth.State.WAITING } -> CaptureHealth.State.WAITING
        else -> CaptureHealth.State.SILENT
    }
    return CaptureHealth.Snapshot(state, values.mapNotNull { it.pcmAgeMs }.maxOrNull(),
        values.mapNotNull { it.soundAgeMs }.minOrNull())
}

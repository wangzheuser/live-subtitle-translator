package app.yuxino.mimi.android.capture

import org.junit.Assert.*
import org.junit.Test

class CaptureInputTest {
    @Test fun microphoneSelectionNeverNeedsProjectionAndBothKeepsSeparateSources() {
        assertFalse(CaptureInput.MICROPHONE.needsProjection)
        assertEquals(listOf(CaptureSource.MICROPHONE), CaptureInput.MICROPHONE.sources)
        assertTrue(CaptureInput.BOTH.needsProjection)
        assertTrue(CaptureInput.BOTH.usesMicrophone)
        assertEquals(listOf(CaptureSource.SYSTEM, CaptureSource.MICROPHONE), CaptureInput.BOTH.sources)
        assertFalse(CaptureInput.SYSTEM.usesMicrophone)
        assertEquals(CaptureInput.SYSTEM, CaptureInput.fromId(null))
        assertEquals(CaptureInput.SYSTEM, CaptureInput.request(null))
        assertNull(CaptureInput.request("unsupported"))
    }

    @Test fun liveMicrophoneCannotMaskStalledSystemCapture() {
        val microphone = CaptureHealth(0)
        val system = CaptureHealth(0)
        microphone.observe(byteArrayOf(1, 0), 6_000)
        val observations = listOf(system.snapshot(6_000), microphone.snapshot(6_000))
        assertEquals(CaptureHealth.State.NO_PCM, captureObservationForSources(observations)?.state)
        system.observe(byteArrayOf(0, 0), 6_001)
        assertEquals(CaptureHealth.State.AUDIO,
            captureObservationForSources(listOf(system.snapshot(6_001), microphone.snapshot(6_001)))?.state)
        assertEquals(CaptureHealth.State.SILENT, system.snapshot(6_001).state)
        assertEquals(CaptureHealth.State.AUDIO, microphone.snapshot(6_001).state)
    }
}

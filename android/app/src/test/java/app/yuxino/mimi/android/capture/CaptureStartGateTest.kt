package app.yuxino.mimi.android.capture

import org.junit.Assert.*
import org.junit.Test

class CaptureStartGateTest {
    @Test fun queuedStartRemainsOwnedAcrossActivityRecreationAndCannotBeDuplicated() {
        val gate = CaptureStartGate()
        val restoredToken = gate.begin()
        assertTrue(gate.isPending)
        assertTrue(gate.waiting(restoredToken))
        assertThrows(IllegalStateException::class.java) { gate.begin() }
        assertTrue(gate.complete(restoredToken))
        assertFalse(gate.waiting(restoredToken))
        assertFalse(gate.isPending)
        // After process recreation an old saved token cannot leave controls disabled.
        assertFalse(CaptureStartGate().waiting(restoredToken))
    }

    @Test fun expiryOrFailedDeliveryCannotCompleteAReplacementStart() {
        val gate = CaptureStartGate()
        val expired = gate.begin()
        assertTrue(gate.complete(expired))
        val replacement = gate.begin()
        assertFalse(gate.complete(expired))
        assertFalse(gate.waiting(expired))
        assertTrue(gate.waiting(replacement))
        assertTrue(gate.complete(replacement))
        assertFalse(gate.complete(replacement))
    }
}

package app.yuxino.mimi.android

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.widget.TextView
import app.yuxino.mimi.android.capture.MimiService
import app.yuxino.mimi.android.capture.CaptureInput

/** Debug-only consent host: never opens settings or reads service credentials. */
class CaptureProbeActivity : Activity() {
    @Volatile var requests = 0
        private set
    @Volatile var consentResult: Int? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this).apply { text = "Android system playback capture test" })
    }

    private var selectedInput = CaptureInput.SYSTEM
    private var useStartGate = false

    fun requestProjection(input: CaptureInput = CaptureInput.SYSTEM, guarded: Boolean = false) {
        check(input.needsProjection)
        selectedInput = input
        useStartGate = guarded
        requests++
        consentResult = null
        @Suppress("DEPRECATION")
        startActivityForResult(getSystemService(MediaProjectionManager::class.java).createScreenCaptureIntent(), 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 1) return
        consentResult = resultCode
        if (resultCode == RESULT_OK && data != null) {
            val intent = MimiService.startIntent(this, resultCode, data, selectedInput).setAction(MimiService.ACTION_CAPTURE_TEST)
            if (useStartGate) intent.putExtra(MimiService.EXTRA_START_REQUEST, MimiService.prepareStart(this))
            MimiService.startFromActivity(this, intent)
        }
    }
}

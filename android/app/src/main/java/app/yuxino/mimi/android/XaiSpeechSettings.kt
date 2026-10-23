package app.yuxino.mimi.android

import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.yuxino.mimi.android.provider.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

/** Native editor and diagnostic lifecycle only. Shared Rust owns both xAI transports. */
internal class XaiSpeechSettings(
    private val activity: AppCompatActivity,
    saved: ServiceConfiguration,
    draft: ServiceEditorDraft?,
    private val configuration: (speechOnly: Boolean) -> JSONObject?,
) {
    private val savedTextModel = saved.xaiTextModel
    private val root = LinearLayout(activity).apply { orientation = LinearLayout.VERTICAL }
    private val mode = Spinner(activity).apply {
        tag = "xai-mode"; background = null
        adapter = ArrayAdapter(activity, R.layout.mimi_spinner_item,
            listOf(activity.getString(R.string.xai_asr_mt), activity.getString(R.string.xai_voice))).apply {
            setDropDownViewResource(R.layout.mimi_spinner_dropdown)
        }
        contentDescription = activity.getString(R.string.xai_mode)
        setSelection(draft?.xaiMode ?: if (saved.xaiSpeechMode == "asrMt") 0 else 1)
    }
    private val model = TextInputEditText(activity).apply {
        tag = "xai-text-model"; setSingleLine(true); textSize = 16f
        inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        setText(draft?.xaiTextModel ?: saved.xaiTextModel)
        isSaveEnabled = false
    }
    private val modelBox = TextInputLayout(activity).apply { addView(model) }
    private val checks = LinearLayout(activity).apply { orientation = LinearLayout.VERTICAL }
    private val requests = mutableMapOf<Boolean, TranslationCall>()
    private val generations = mutableMapOf(false to 0, true to 0)
    private val buttons = mutableMapOf<Boolean, MaterialButton>()
    private val results = mutableMapOf<Boolean, TextView>()
    val view: View get() = root
    val selectedPosition: Int get() = mode.selectedItemPosition
    val speechMode: String get() = if (selectedPosition == 0) "asrMt" else "voice"
    val textModel: String get() = if (speechMode == "voice") savedTextModel else model.text.toString().trim()

    init {
        root.addView(HelpUi.heading(activity, activity.getString(R.string.xai_mode),
            activity.getString(R.string.xai_help), 18f, "xai-mode-help"))
        root.addView(mode, LinearLayout.LayoutParams(-1, dp(56)))
        ServiceSettingsUi.addField(activity, root, modelBox, model, activity.getString(R.string.xai_text_model), dp(16))
        for (speech in listOf(true, false)) {
            val button = MaterialButton(activity, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply {
                tag = if (speech) "xai-speech-check" else "xai-text-check"
                setText(if (speech) R.string.xai_check_speech else R.string.translation_check)
                setIconResource(R.drawable.ic_check); isAllCaps = false; textSize = 16f
                setOnClickListener { check(speech) }
            }
            val result = ServiceSettingsUi.label(activity, "", 16f).apply {
                tag = if (speech) "xai-speech-status" else "xai-text-status"
                visibility = View.GONE; accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE
            }
            buttons[speech] = button; results[speech] = result
            checks.addView(button, LinearLayout.LayoutParams(-2, -2).apply { gravity = Gravity.END })
            checks.addView(result, LinearLayout.LayoutParams(-2, -2).apply { gravity = Gravity.END; bottomMargin = dp(8) })
        }
        root.addView(checks)
        mode.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) { invalidate(); render() }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) = Unit
        }
        model.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { invalidate(); modelBox.error = null }
            override fun afterTextChanged(s: android.text.Editable?) = Unit
        })
        render()
    }

    private fun render() {
        ServiceSettingsUi.fieldVisible(modelBox, speechMode == "asrMt")
        checks.visibility = if (speechMode == "asrMt") View.VISIBLE else View.GONE
    }

    fun validate(): Boolean {
        val valid = speechMode != "asrMt" || textModel.toByteArray(Charsets.UTF_8).size in 1..256 && textModel.none(Char::isISOControl)
        modelBox.error = if (valid) null else activity.getString(R.string.xai_model_invalid)
        return valid
    }

    fun invalidate() {
        for (speech in listOf(true, false)) {
            generations[speech] = generations.getValue(speech) + 1
            requests.remove(speech)?.cancel(); results[speech]?.visibility = View.GONE
            buttons[speech]?.apply { isEnabled = mode.isEnabled; setText(if (speech) R.string.xai_check_speech else R.string.translation_check) }
        }
    }

    private fun check(speech: Boolean) {
        val session = configuration(speech) ?: return
        val epoch = generations.getValue(speech) + 1
        generations[speech] = epoch; requests.remove(speech)?.cancel()
        buttons.getValue(speech).apply { isEnabled = false; setText(R.string.translation_checking) }
        results.getValue(speech).visibility = View.GONE
        val client = if (speech) NativeTextProbeClient(networkSnapshot = NativeRuntimeNetwork::snapshot, speech = true) else NativeTextProbeClient()
        val input = if (speech) session else JSONObject().put("sessionConfiguration", session)
        requests[speech] = client.check(input) { outcome -> activity.runOnUiThread {
            if (generations[speech] != epoch || activity.isFinishing || activity.isDestroyed) return@runOnUiThread
            requests.remove(speech)
            buttons.getValue(speech).apply { isEnabled = mode.isEnabled; setText(if (speech) R.string.xai_check_speech else R.string.translation_check) }
            results.getValue(speech).apply {
                visibility = View.VISIBLE
                text = when (outcome) {
                    is TranslationResult.Success -> activity.getString(R.string.translation_check_success, outcome.elapsedMs)
                    is TranslationResult.Failure -> activity.getString(when (outcome.code) {
                        "translation_http_401" -> R.string.translation_check_auth
                        "translation_http_429" -> R.string.translation_check_busy
                        "translation_timeout" -> R.string.translation_check_timeout
                        else -> R.string.translation_check_failure
                    }, outcome.elapsedMs)
                }
            }
        } }
    }

    fun setEditingEnabled(enabled: Boolean) {
        mode.isEnabled = enabled; model.isEnabled = enabled
        buttons.forEach { (speech, button) -> button.isEnabled = enabled && !requests.containsKey(speech) }
        if (!enabled) invalidate()
    }
    fun dispose() = invalidate()
    private fun dp(value: Int) = ServiceSettingsUi.dp(activity, value)
}

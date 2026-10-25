package app.yuxino.mimi.android

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

internal enum class TextTranslationSetupPage { API_KEY, GUIDE }

/** Only catalog-owned public URLs may leave the editor; editable addresses are never browser targets. */
internal fun textTranslationSetupUrl(presetId: String, page: TextTranslationSetupPage): String? {
    val preset = TextTranslationPresets.entries.firstOrNull { it.id == presetId } ?: return null
    return when (page) {
        TextTranslationSetupPage.API_KEY -> preset.apiKeyUrl.takeIf { preset.setupKind == "cloud" && it.isNotBlank() }
        TextTranslationSetupPage.GUIDE -> preset.guideUrl.takeIf(String::isNotBlank)
    }
}

internal fun openTextTranslationSetup(activity: AppCompatActivity, presetId: String, page: TextTranslationSetupPage) {
    val url = textTranslationSetupUrl(presetId, page) ?: return
    try { activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
    catch (_: ActivityNotFoundException) { Toast.makeText(activity, R.string.guide_no_browser, Toast.LENGTH_SHORT).show() }
    catch (_: SecurityException) { Toast.makeText(activity, R.string.translation_setup_open_failed, Toast.LENGTH_SHORT).show() }
}

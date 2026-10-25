package app.yuxino.mimi.android

import android.content.Context

internal class TextTranslationPresetAdapter(context: Context) : TranslationIconAdapter(context,
    listOf(context.getString(R.string.translation_preset_custom)) + TextTranslationPresets.entries.map { it.label }) {
    override fun iconResource(position: Int): Int = translationPresetIcon(TextTranslationPresets.entries.getOrNull(position - 1)?.id)
}

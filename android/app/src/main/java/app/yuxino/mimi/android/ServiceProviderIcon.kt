package app.yuxino.mimi.android

import app.yuxino.mimi.android.provider.ServiceProvider

/** Same provider artwork as desktop, with neutral controls separate from each brand mark. */
internal fun serviceProviderIcon(provider: ServiceProvider): Int = when (provider) {
    ServiceProvider.DASHSCOPE -> R.drawable.ic_translation_alibaba
    ServiceProvider.OPENAI -> R.drawable.ic_provider_openai
    ServiceProvider.GEMINI -> R.drawable.ic_provider_gemini
    ServiceProvider.AZURE -> R.drawable.ic_provider_azure
    ServiceProvider.VOLCANO -> R.drawable.ic_provider_volcano
    ServiceProvider.TENCENT -> R.drawable.ic_provider_tencent
    ServiceProvider.BAIDU -> R.drawable.ic_provider_baidu
    ServiceProvider.XAI -> R.drawable.ic_provider_xai
}

internal fun translationProviderIcon(provider: app.yuxino.mimi.android.provider.TextTranslationProvider): Int = when (provider) {
    app.yuxino.mimi.android.provider.TextTranslationProvider.BUILTIN -> R.drawable.ic_translation_alibaba
    app.yuxino.mimi.android.provider.TextTranslationProvider.DEEPL -> R.drawable.ic_translation_deepl
    app.yuxino.mimi.android.provider.TextTranslationProvider.DEEPLX -> R.drawable.ic_translation_deeplx
    app.yuxino.mimi.android.provider.TextTranslationProvider.NONE -> R.drawable.ic_translation_captions
    app.yuxino.mimi.android.provider.TextTranslationProvider.XAI -> R.drawable.ic_provider_xai
    else -> R.drawable.ic_service_languages
}

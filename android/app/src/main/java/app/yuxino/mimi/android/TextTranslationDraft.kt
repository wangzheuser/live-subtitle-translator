package app.yuxino.mimi.android

import app.yuxino.mimi.android.provider.TextTranslationProvider
import app.yuxino.mimi.android.provider.TranslationConfiguration
import app.yuxino.mimi.android.provider.NativeRuntimeConfiguration

/** Editor-owned replacement values; a blank key retains only this saved destination's key. */
internal class TextTranslationDraft(val saved: TranslationConfiguration) {
    var endpoint = saved.endpoint.ifBlank {
        if (saved.provider == TextTranslationProvider.CHAT_MOCK) "http://127.0.0.1:8000/v1" else ""
    }
    var model = saved.model.ifBlank { if (saved.provider == TextTranslationProvider.XAI) "grok-4.3" else "" }
    var key = ""
    var localHttp = saved.allowLocalHttp
    var forgetKey = false

    fun reusesSavedKey() = !forgetKey && saved.apiKey.isNotBlank() &&
        (!saved.provider.hasCustomEndpoint || NativeRuntimeConfiguration.textEndpointsEquivalent(saved.provider, saved.endpoint, endpoint))

    fun configuration(): TranslationConfiguration = TranslationConfiguration(
        endpoint = if (saved.provider.hasCustomEndpoint) endpoint.trim() else "",
        model = if (saved.provider.usesOpenAIProtocol) model.trim() else "",
        apiKey = key.trim().ifBlank { if (reusesSavedKey()) saved.apiKey else "" },
        allowLocalHttp = saved.provider.hasCustomEndpoint && localHttp,
        provider = saved.provider,
    )
}

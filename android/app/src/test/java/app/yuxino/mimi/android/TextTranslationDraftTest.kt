package app.yuxino.mimi.android

import app.yuxino.mimi.android.provider.TextTranslationProvider
import app.yuxino.mimi.android.provider.TranslationConfiguration
import org.junit.Assert.*
import org.junit.Test

class TextTranslationDraftTest {
    private fun draft(provider: TextTranslationProvider = TextTranslationProvider.OPENAI_COMPATIBLE) =
        TextTranslationDraft(TranslationConfiguration(provider = provider,
            endpoint = "https://example.invalid/v1", model = "synthetic-model", apiKey = "synthetic-saved-key"))

    @Test fun equivalentAddressesRetainSavedTextKeysInTheSubmittedConfiguration() {
        for (provider in listOf(TextTranslationProvider.OPENAI_COMPATIBLE, TextTranslationProvider.CHAT_MOCK, TextTranslationProvider.DEEPLX)) {
            val draft = draft(provider)
            val suffix = if (provider == TextTranslationProvider.DEEPLX) "translate" else "chat/completions"
            for (endpoint in listOf("https://example.invalid/v1/", "https://EXAMPLE.invalid:443/v1", "https://example.invalid/v1/$suffix")) {
                draft.endpoint = endpoint
                assertTrue("Saved key must remain available for an equivalent address", draft.reusesSavedKey())
                assertEquals("synthetic-saved-key", draft.configuration().apiKey)
            }
        }
    }

    @Test fun changedDestinationsAndExplicitRemovalDoNotInheritSavedKeys() {
        val draft = draft()
        for (endpoint in listOf("", "https://other.invalid/v1", "https://example.invalid/v2", "https://example.invalid:8443/v1", "https://example.invalid/v1?token=synthetic", "invalid")) {
            draft.endpoint = endpoint
            assertFalse(draft.reusesSavedKey())
            assertEquals("", draft.configuration().apiKey)
        }
        draft.endpoint = "https://example.invalid/v1/"
        draft.forgetKey = true
        assertFalse(draft.reusesSavedKey())
        assertEquals("", draft.configuration().apiKey)
        draft.key = "synthetic-replacement-key"
        assertEquals("synthetic-replacement-key", draft.configuration().apiKey)
    }

    @Test fun routeDraftsKeepTheirOwnKeysAndEmptySavedEndpointsNeverMatch() {
        val compatible = draft()
        val chatMock = TextTranslationDraft(TranslationConfiguration(provider = TextTranslationProvider.CHAT_MOCK,
            endpoint = compatible.endpoint, model = "synthetic-other-model"))
        compatible.endpoint += "/"
        assertEquals("synthetic-saved-key", compatible.configuration().apiKey)
        assertEquals("", chatMock.configuration().apiKey)
        val missing = TextTranslationDraft(TranslationConfiguration(apiKey = "synthetic-saved-key"))
        assertFalse(missing.reusesSavedKey())
        assertEquals("", missing.configuration().apiKey)
    }
}

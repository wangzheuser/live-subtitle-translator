package app.yuxino.mimi.android

import org.junit.Assert.*
import org.junit.Test

class TextTranslationSetupTest {
    @Test fun onlyKnownCatalogIdentitiesResolveBrowserDestinations() {
        assertNull(textTranslationSetupUrl("", TextTranslationSetupPage.GUIDE))
        assertNull(textTranslationSetupUrl("https://untrusted.example/setup", TextTranslationSetupPage.API_KEY))
        val cloud = TextTranslationPresets.entries.first { it.id == "openai" }
        assertEquals(cloud.apiKeyUrl, textTranslationSetupUrl(cloud.id, TextTranslationSetupPage.API_KEY))
        assertEquals(cloud.guideUrl, textTranslationSetupUrl(cloud.id, TextTranslationSetupPage.GUIDE))
    }

    @Test fun localAndKeylessServicesNeverOfferAnApiKeyDestination() {
        for (preset in TextTranslationPresets.entries.filter { it.setupKind != "cloud" }) {
            assertNull(textTranslationSetupUrl(preset.id, TextTranslationSetupPage.API_KEY))
            assertEquals(preset.guideUrl, textTranslationSetupUrl(preset.id, TextTranslationSetupPage.GUIDE))
        }
    }
}

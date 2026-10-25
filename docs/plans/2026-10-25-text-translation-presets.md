# Compatible text translation presets

The translation service picker keeps ChatMock last on desktop and Android.
Existing service marks and neutral menu styling remain; normalize the padded
DeepLX artwork and reduce the visual weight of the xAI favicon in shared pickers.

The OpenAI-compatible route offers editable setup presets for OpenAI, DeepSeek,
Qwen, Gemini, Ollama and LM Studio. Keep one protocol and credential slot: presets
are UI defaults, not new persisted provider identities. Source labels, addresses
and example models from `shared/provider-copy.json`; the existing generator emits
desktop TypeScript and native Android Kotlin plus eight-language help.

Selecting a preset only changes the draft. Choosing a different destination preset clears the
typed key and disallows reusing the old saved key, including after switching back.
Equivalent destinations retain their key. Custom preserves the editable fields;
an explicit save and connection check remain separate user actions. Local HTTP
on Android still requires its existing explicit opt-in. Do not populate Gemini
or local model IDs without knowing the user's available models.

Regression checks cover draft/save isolation, keyless local setup, equivalent
URLs, generated-output drift, native Spinner callback ownership and existing
keyboard navigation. Review menu geometry in all interface languages at minimum
and default widths in light/dark themes. Browser fixtures establish layout only;
native app/device and real provider checks remain separate evidence.

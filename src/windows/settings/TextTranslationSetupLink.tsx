import { Icon } from "../../components/Icon";
import { effectiveUiLanguage } from "../../lib/i18n";
import { OPENAI_COMPATIBLE_PRESETS, PROVIDER_COPY } from "../../lib/generated/providerCopy";
import { openTextTranslationSetupPage, type TextTranslationSetupPage } from "../../lib/ipc";
import { useSettingsToast } from "./useSettingsToast";

type Preset = typeof OPENAI_COMPATIBLE_PRESETS[number];

/** Explicit navigation through native catalog IDs; never opens an editable URL. */
export function TextTranslationSetupLink({ preset, page, disabled = false }: {
  preset: Preset;
  page: TextTranslationSetupPage;
  disabled?: boolean;
}) {
  const copy = PROVIDER_COPY[effectiveUiLanguage()];
  const { runWithToast } = useSettingsToast();
  if (page === "apiKey" && !preset.apiKeyUrl) return null;
  const label = page === "apiKey" ? copy.compatibleSetupGetKey
    : preset.setupKind === "local" ? copy.compatibleSetupLocalGuide : copy.compatibleSetupGuide;
  return <button type="button" className="settings-link translation-setup-link" disabled={disabled} aria-label={`${label}: ${preset.label}`}
    onClick={() => { void runWithToast(() => openTextTranslationSetupPage(preset.id, page), copy.compatibleSetupOpenFailed); }}>
    {label}<Icon name="chevron-right" />
  </button>;
}

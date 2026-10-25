import type { ComponentProps } from "react";
import { OPENAI_COMPATIBLE_PRESETS } from "../lib/generated/providerCopy";
import { ProviderIcon } from "./ProviderIcon";
import deepseek from "../assets/providers/deepseek.svg";
import kimi from "../assets/providers/kimi.svg";
import siliconflow from "../assets/providers/siliconflow.svg";
import openrouter from "../assets/providers/openrouter.svg";
import openrouterDark from "../assets/providers/openrouter-white.svg";
import indexTranslate from "../assets/providers/index-translate.svg";
import ollama from "../assets/providers/ollama.svg";
import lmStudio from "../assets/providers/lm-studio.svg";
import lmStudioDark from "../assets/providers/lm-studio-white.svg";
import baiduQianfan from "../assets/providers/baidu-qianfan.svg";

type PresetId = typeof OPENAI_COMPATIBLE_PRESETS[number]["id"];
type Mark = { provider: ComponentProps<typeof ProviderIcon>["provider"] }
  | { light: string; dark?: string };

const MARKS: Record<PresetId, Mark> = {
  openai: { provider: "openAIRealtime" },
  deepseek: { light: deepseek },
  qwen: { provider: "alibabaCloud" },
  gemini: { provider: "googleGeminiLive" },
  kimi: { light: kimi },
  siliconflow: { light: siliconflow },
  openrouter: { light: openrouter, dark: openrouterDark },
  indexTranslate: { light: indexTranslate },
  tencentTokenHub: { provider: "tencentCloud" },
  baiduQianfan: { light: baiduQianfan },
  volcanoArk: { provider: "volcanoEngine" },
  ollama: { light: ollama },
  lmStudio: { light: lmStudio, dark: lmStudioDark },
};

/** A setup preset is a UI destination, not a separate saved provider identity. */
export function TextTranslationPresetIcon({ presetId }: { presetId: PresetId }) {
  const mark = MARKS[presetId];
  return <span className="provider-icon translation-preset-icon" data-preset={presetId} aria-hidden="true">
    {"provider" in mark ? <ProviderIcon provider={mark.provider} size={32} /> : <>
      <img className="provider-icon__image provider-icon__image--light" src={mark.light} width={32} height={32} alt="" draggable={false} />
      <img className={["provider-icon__image provider-icon__image--dark", !mark.dark && "provider-icon__image--inverted"].filter(Boolean).join(" ")} src={mark.dark ?? mark.light} width={32} height={32} alt="" draggable={false} />
    </>}
  </span>;
}

import { effectiveUiLanguage, I18N } from "../../lib/i18n";
import type { ServiceProfile, XAISpeechMode } from "../../lib/types";
import { AutoSaveNameField } from "./AutoSaveNameField";
import { SettingsHelp } from "./SettingsHelp";
import { SettingsSelect } from "./SettingsPrimitives";
import { useSettingsToast } from "./useSettingsToast";

const copy = {
  zh: { mode: "语音模式", asr: "流式识别 + 文字翻译", model: "文字翻译模型", help: "使用 xAI 语音识别，再由所选 Grok 文字模型翻译；两步共用此配置的 API Key。Grok Voice 在检测到说话停顿后分段翻译。停止字幕后可切换。", failed: "语音模式保存失败，请重试。" },
  "zh-TW": { mode: "語音模式", asr: "串流辨識 + 文字翻譯", model: "文字翻譯模型", help: "使用 xAI 語音辨識，再由所選 Grok 文字模型翻譯；兩步共用此設定的 API Key。Grok Voice 偵測到說話停頓後分段翻譯。停止字幕後可切換。", failed: "語音模式儲存失敗，請重試。" },
  en: { mode: "Speech mode", asr: "Streaming recognition + text translation", model: "Text translation model", help: "xAI transcribes speech, then the selected Grok text model translates it. Both stages use this configuration’s API key. Grok Voice translates in segments after pauses. Stop subtitles to switch.", failed: "Could not save the speech mode. Try again." },
  ja: { mode: "音声モード", asr: "ストリーミング認識 + テキスト翻訳", model: "テキスト翻訳モデル", help: "xAI が音声を認識し、選択した Grok テキストモデルが翻訳します。両方でこの設定の API Key を使います。Grok Voice は発話の切れ目ごとに翻訳します。字幕を停止して切り替えてください。", failed: "音声モードを保存できませんでした。再試行してください。" },
  de: { mode: "Sprachmodus", asr: "Streaming-Erkennung + Textübersetzung", model: "Textübersetzungsmodell", help: "xAI transkribiert Sprache, dann übersetzt das gewählte Grok-Textmodell. Beide Schritte verwenden denselben API-Schlüssel. Grok Voice übersetzt nach Sprechpausen abschnittsweise. Zum Wechseln Untertitel stoppen.", failed: "Sprachmodus konnte nicht gespeichert werden. Bitte erneut versuchen." },
  ko: { mode: "음성 모드", asr: "스트리밍 인식 + 텍스트 번역", model: "텍스트 번역 모델", help: "xAI가 음성을 인식하고 선택한 Grok 텍스트 모델이 번역합니다. 두 단계는 이 설정의 API 키를 공유합니다. Grok Voice는 말이 멈춘 뒤 구간별로 번역합니다. 자막을 중지한 후 변경하세요.", failed: "음성 모드를 저장하지 못했습니다. 다시 시도하세요." },
  fr: { mode: "Mode vocal", asr: "Reconnaissance en flux + traduction de texte", model: "Modèle de traduction de texte", help: "xAI transcrit la parole, puis le modèle de texte Grok choisi la traduit. Les deux étapes partagent la clé API de cette configuration. Grok Voice traduit par segments après les pauses. Arrêtez les sous-titres pour changer.", failed: "Impossible d’enregistrer le mode vocal. Réessayez." },
  th: { mode: "โหมดเสียง", asr: "รู้จำเสียงแบบสตรีม + แปลข้อความ", model: "โมเดลแปลข้อความ", help: "xAI รู้จำเสียง จากนั้นโมเดลข้อความ Grok ที่เลือกจะแปล ทั้งสองขั้นตอนใช้ API key ของการตั้งค่านี้ร่วมกัน Grok Voice แปลเป็นช่วงหลังหยุดพูด หยุดคำบรรยายก่อนเปลี่ยนโหมด", failed: "บันทึกโหมดเสียงไม่สำเร็จ ลองอีกครั้ง" },
};

export function XAISpeechSettings({ profile, disabled, onSave }: {
  profile: ServiceProfile;
  disabled: boolean;
  onSave: (options: { xaiSpeechMode?: XAISpeechMode; xaiTextModel?: string }) => Promise<unknown>;
}) {
  const strings = copy[effectiveUiLanguage()];
  const { runWithToast } = useSettingsToast();
  const mode = profile.xaiSpeechMode ?? "voice";
  return <section className="service-stage xai-speech-settings">
    <div className="settings-field service-stage__selector">
      <span className="service-stage__name-help">{strings.mode}<SettingsHelp text={strings.help} label={I18N.settings.helpLabel} /></span>
      <SettingsSelect label={strings.mode} value={mode} disabled={disabled}
        options={[{ value: "asrMt", label: strings.asr }, { value: "voice", label: "Grok Voice" }]}
        onChange={value => { if (value !== mode) void runWithToast(() => onSave({ xaiSpeechMode: value as XAISpeechMode }), strings.failed); }} />
    </div>
    {mode === "asrMt" && <AutoSaveNameField id={`profile-xai-model-${profile.id}`} draftKey="xaiModel"
      label={strings.model} value={profile.xaiTextModel ?? "grok-4.3"} placeholder={I18N.settings.modelNamePlaceholder}
      disabled={disabled} maxLength={256} allowEmpty={false} requiredMessage={I18N.settings.openAICompatibleModelInvalid} onSave={xaiTextModel => onSave({ xaiTextModel })} />}
  </section>;
}

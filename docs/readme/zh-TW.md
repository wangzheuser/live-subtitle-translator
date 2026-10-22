<div align="center">
  <img src="../../src-tauri/icons/128x128@2x.png" width="96" alt="mimi">
  <h1>Mimi</h1>
  <p>系統聲音或麥克風即時字幕與翻譯，支援 Apple 晶片和 Intel Mac（macOS 13+），以及 Windows / Linux x86_64。</p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/github/v/release/yuxino/mimi?style=flat&amp;logo=github&amp;logoColor=white" alt="最新版本"></a>
    <a href="https://github.com/yuxino/mimi/releases"><img src="https://img.shields.io/github/downloads/yuxino/mimi/total?style=flat&amp;labelColor=a85f82&amp;color=e889b5" alt="總下載量"></a>
    <a href="https://github.com/yuxino/mimi/actions/workflows/ci.yml?query=branch%3Amain"><img src="https://img.shields.io/github/actions/workflow/status/yuxino/mimi/ci.yml?style=flat&amp;logo=githubactions&amp;logoColor=white&amp;branch=main&amp;event=push&amp;label=CI" alt="main 分支 CI 狀態"></a>
    <a href="../../LICENSE"><img src="https://img.shields.io/github/license/yuxino/mimi?style=flat&amp;logo=opensourceinitiative&amp;logoColor=white" alt="MIT 授權條款"></a>
  </p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/macOS-13%2B-555?style=flat&amp;logo=apple&amp;logoColor=white" alt="macOS 13+"></a>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/Windows-x64-0078D4?style=flat&amp;logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI%2BPHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0wIDBoMTF2MTFIMHptMTMgMGgxMXYxMUgxM3pNMCAxM2gxMXYxMUgwem0xMyAwaDExdjExSDEzeiIvPjwvc3ZnPg%3D%3D&amp;logoColor=white" alt="Windows x64"></a>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/Linux-x86__64-FCC624?style=flat&amp;logo=linux&amp;logoColor=white" alt="Linux"></a>
    <a href="../../android/README.zh-CN.md"><img src="https://img.shields.io/badge/Android-app-3DDC84?style=flat&amp;logo=android&amp;logoColor=white" alt="Android"></a>
  </p>
  <p>
    <a href="../../README_EN.md">English</a> · <a href="../../README.md">简体中文</a> · <a href="zh-TW.md">繁體中文</a> · <a href="ja.md">日本語</a> · <a href="th.md">ภาษาไทย</a> · <a href="ko.md">한국어</a> · <a href="fr.md">Français</a> · <a href="de.md">Deutsch</a>
  </p>
</div>

![Mimi 雙語字幕示意：原創插畫與範例對白](../assets/readme-preview.png)

字幕顯示示意，使用範例對白。[觀看字幕與沉浸模式示範（舊版介面）](../demos/README.md)。

## 即時字幕與翻譯

- **懸浮字幕**：為電影、直播、線上課程即時顯示字幕，支援雙語、僅譯文或僅原文。
- **選擇音源**：擷取電腦聲音、麥克風，或同時擷取兩路。macOS / Windows 11 支援指定應用程式，Linux 支援選擇個別播放串流。
- **調整顯示**：桌面版可調整位置、14–48 px 字級和顏色，顯示全部或最近 1–10 則字幕，向上捲動查看既有歷史；沉浸模式只顯示文字，點擊穿透可讓你繼續操作下方應用程式。
- **儲存字幕和錄音**：桌面版可將字幕匯出為 TXT 或 Markdown，也可將所選音源錄製為 WAV。兩項儲存功能皆需手動開啟。

## 開始使用

[下載桌面版](https://github.com/yuxino/mimi/releases/latest) · [Android 安裝與使用（簡體中文）](../../android/README.zh-CN.md#start)

桌面版支援 macOS 13+（Apple 晶片和 Intel）、Windows x64、Linux x86_64。

1. **設定服務**：在「設定 → 模型與服務」中新增語音服務。服務選擇與金鑰取得方式見[設定指南（簡體中文）](../provider-setup.zh-CN.md#choose)。
2. **選擇聲音和語言**：在「聲音與語言」中選擇要擷取的音源、辨識語言和翻譯語言。
3. **開啟字幕**：在「字幕」頁開啟「即時字幕」，依提示授予所需權限，再播放想看的內容。

Mimi 本身免費，雲端服務可能收費。使用雲端辨識或翻譯時，音訊或辨識文字會傳送給相應服務。Apple 本機服務的適用條件見[設定指南（簡體中文）](../provider-setup.zh-CN.md#apple-speech)。

## 使用中遇到問題？

[使用指南](../usage.zh-TW.md)包含安裝、權限、字幕顯示和儲存記錄。沒有字幕或連線失敗，先看[辨識錯誤](../usage.zh-TW.md#errors)；仍未解決可[回報問題](https://github.com/yuxino/mimi/issues)。

## 貢獻者

想幫 Mimi 改進？從[貢獻指南（中英）](../../.github/CONTRIBUTING.md)開始。

感謝每一位寫程式碼、提問題、試用和分享的朋友 (๑•̀ㅂ•́)و✧

特別感謝 [@yebuwudong](https://github.com/yebuwudong) 貢獻 [Android 版](https://github.com/yuxino/mimi/pull/37)，以及 [@LLLin000](https://github.com/LLLin000) 貢獻[字幕動畫](https://github.com/yuxino/mimi/pull/67)和 [Windows 音訊來源改進](https://github.com/yuxino/mimi/pull/89)。

也感謝 [@Chtholly000](https://github.com/Chtholly000) 改進 [Gemini 連續字幕和連線輪換](https://github.com/yuxino/Mimi/pull/176)。

感謝 [@EmmetZ](https://github.com/EmmetZ) 修復 [Wayland 浮窗與控制元件不同步的問題](https://github.com/yuxino/Mimi/pull/242)，並新增 [Linux 個別播放串流擷取](https://github.com/yuxino/Mimi/pull/275)和[播放串流應用程式圖示](https://github.com/yuxino/Mimi/pull/304)。

感謝 [@wangmian0](https://github.com/wangmian0) 為 [DeepSeek 即時字幕翻譯關閉思考模式](https://github.com/yuxino/Mimi/pull/270)。

感謝 [@hubumulity](https://github.com/hubumulity) 貢獻[字幕顯示則數設定](https://github.com/yuxino/Mimi/pull/303)。

感謝 [@ryannyui](https://github.com/ryannyui) 貢獻 [Linux AppImage 圖形相容性修復與工具鏈升級](https://github.com/yuxino/Mimi/pull/312)。

<p>
  <a href="https://github.com/yuxino"><img src="../assets/contributors/yuxino.svg" width="40" height="40" alt="@yuxino"></a>
  <a href="https://github.com/LLLin000"><img src="../assets/contributors/LLLin000.svg" width="40" height="40" alt="@LLLin000"></a>
  <a href="https://github.com/yebuwudong"><img src="../assets/contributors/yebuwudong.svg" width="40" height="40" alt="@yebuwudong"></a>
  <a href="https://github.com/Chtholly000"><img src="../assets/contributors/Chtholly000.svg" width="40" height="40" alt="@Chtholly000"></a>
  <a href="https://github.com/inhome"><img src="../assets/contributors/inhome.svg" width="40" height="40" alt="@inhome"></a>
  <a href="https://github.com/EmmetZ"><img src="../assets/contributors/EmmetZ.svg" width="40" height="40" alt="@EmmetZ"></a>
  <a href="https://github.com/wangmian0"><img src="../assets/contributors/wangmian0.svg" width="40" height="40" alt="@wangmian0"></a>
  <a href="https://github.com/hubumulity"><img src="../assets/contributors/hubumulity.svg" width="40" height="40" alt="@hubumulity"></a>
  <a href="https://github.com/ryannyui"><img src="../assets/contributors/ryannyui.svg" width="40" height="40" alt="@ryannyui"></a>
</p>

[檢視所有貢獻者](https://github.com/yuxino/mimi/graphs/contributors)

## 社群致謝

也感謝 [V2EX](https://www.v2ex.com/)、[LINUX DO](https://linux.do/)、[小眾軟體](https://meta.appinn.net/)、[NodeLoc](https://www.nodeloc.com/)、[Solo](https://solo.xin/)、[新趣集](https://xinquji.com/posts/859305)和[電鴨](https://eleduck.com/)社群朋友的試用、意見回饋與分享。

[MIT](../../LICENSE) © 2026 yuxino

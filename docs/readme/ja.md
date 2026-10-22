<div align="center">
  <img src="../../src-tauri/icons/128x128@2x.png" width="96" alt="mimi">
  <h1>Mimi</h1>
  <p>パソコンやマイクの音声をリアルタイムで翻訳字幕に。</p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/github/v/release/yuxino/mimi?style=flat&amp;logo=github&amp;logoColor=white" alt="最新リリース"></a>
    <a href="https://github.com/yuxino/mimi/releases"><img src="https://img.shields.io/github/downloads/yuxino/mimi/total?style=flat&amp;labelColor=a85f82&amp;color=e889b5" alt="総ダウンロード数"></a>
    <a href="https://github.com/yuxino/mimi/actions/workflows/ci.yml?query=branch%3Amain"><img src="https://img.shields.io/github/actions/workflow/status/yuxino/mimi/ci.yml?style=flat&amp;logo=githubactions&amp;logoColor=white&amp;branch=main&amp;event=push&amp;label=CI" alt="main ブランチの CI 状態"></a>
    <a href="../../LICENSE"><img src="https://img.shields.io/github/license/yuxino/mimi?style=flat&amp;logo=opensourceinitiative&amp;logoColor=white" alt="MIT ライセンス"></a>
  </p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/macOS-13%2B-555?style=flat&amp;logo=apple&amp;logoColor=white" alt="macOS 13+"></a>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/Windows-x64-0078D4?style=flat&amp;logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI%2BPHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0wIDBoMTF2MTFIMHptMTMgMGgxMXYxMUgxM3pNMCAxM2gxMXYxMUgwem0xMyAwaDExdjExSDEzeiIvPjwvc3ZnPg%3D%3D&amp;logoColor=white" alt="Windows x64"></a>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/Linux-x86__64-FCC624?style=flat&amp;logo=linux&amp;logoColor=white" alt="Linux"></a>
    <a href="../../android/README.md"><img src="https://img.shields.io/badge/Android-app-3DDC84?style=flat&amp;logo=android&amp;logoColor=white" alt="Android"></a>
  </p>
  <p>
    <a href="../../README_EN.md">English</a> · <a href="../../README.md">简体中文</a> · <a href="zh-TW.md">繁體中文</a> · <a href="ja.md">日本語</a> · <a href="th.md">ภาษาไทย</a> · <a href="ko.md">한국어</a> · <a href="fr.md">Français</a> · <a href="de.md">Deutsch</a>
  </p>
</div>

![サンプルの会話を使った Mimi の二言語字幕イメージ](../assets/readme-preview.png)

サンプルの会話による画面イメージです。[字幕とイマーシブモードのデモ（旧 UI）](../demos/README.md)。

## 聞きながら字幕で読む

- **再生中の内容を理解する**：映画、配信、授業の音声をフローティング字幕に。二言語、訳文のみ、原文のみを選べます。
- **音源を選ぶ**：初期設定はパソコンの音声。マイクや両方の音源も自分で有効にできます。macOS / Windows 11 ではアプリを指定できます。Linux では個別の再生ストリームを選べます。
- **読みやすく調整する**：デスクトップ版では位置、14–48 px の文字サイズ、色を変更できます。最近の字幕をすべて、または最新の 1–10 件だけ表示し、上にスクロールして保持されている履歴を読めます。イマーシブモードは字幕だけを表示し、クリック透過で背後のアプリを操作できます。
- **字幕と音声を保存する**：デスクトップ版では字幕を TXT または Markdown、選択した音源の録音を WAV で書き出せます。どちらも自分で有効にするまで保存・録音しません。

## はじめに

[デスクトップ版をダウンロード](https://github.com/yuxino/mimi/releases/latest) · [Android の設定手順（英語）](../../android/README.md#start)

デスクトップ版は macOS 13+（Apple シリコン・Intel）、Windows x64、Linux x86_64 に対応しています。

1. **サービスを設定する**：「設定 → モデルとサービス」で音声サービスを追加します。サービスの選び方とキーの取得方法は[設定ガイド（英語）](../provider-setup.md#choose)をご覧ください。
2. **音源と言語を選ぶ**：音声と言語の設定で、取り込む音源、認識言語、翻訳先の言語を選びます。
3. **字幕を開始する**：「字幕」ページでライブ字幕をオンにし、必要な権限を許可してから、見たいコンテンツを再生します。

Mimi は無料ですが、クラウドサービスは有料の場合があります。クラウドでの認識・翻訳では、音声や認識した文字を選択したサービスに送信します。Apple のローカルサービスの利用条件は[設定ガイド（英語）](../provider-setup.md#apple-speech)をご覧ください。

## 困ったときは

[使い方ガイド](../usage.ja.md)でインストール、権限、字幕表示、保存を説明しています。字幕が出ない・接続できない場合は[音声認識のエラー](../usage.ja.md#errors)を確認し、解決しなければ[問題を報告](https://github.com/yuxino/mimi/issues)してください。

## 貢献者

開発に参加する方は[貢献ガイド（英語・中国語）](../../.github/CONTRIBUTING.md)をご覧ください。

コードの作成、不具合の報告、試用、紹介をしてくださる皆さん、ありがとうございます (๑•̀ㅂ•́)و✧

[@yebuwudong](https://github.com/yebuwudong) さんの [Android 版](https://github.com/yuxino/mimi/pull/37)、[@LLLin000](https://github.com/LLLin000) さんの[字幕アニメーション](https://github.com/yuxino/mimi/pull/67)と [Windows の音声取得の改善](https://github.com/yuxino/mimi/pull/89)に、特に感謝します。

[@Chtholly000](https://github.com/Chtholly000) さんによる [Gemini の連続字幕と計画的な接続切り替えの改善](https://github.com/yuxino/Mimi/pull/176)にも感謝します。

[@EmmetZ](https://github.com/EmmetZ) さんによる [Wayland の字幕操作パネルの修正](https://github.com/yuxino/Mimi/pull/242)、[Linux での個別再生ストリームの音声取得](https://github.com/yuxino/Mimi/pull/275)、[再生ストリームのアプリアイコン表示](https://github.com/yuxino/Mimi/pull/304)に感謝します。

[@wangmian0](https://github.com/wangmian0) さんは、[DeepSeek によるリアルタイム字幕翻訳の思考モードを無効化](https://github.com/yuxino/Mimi/pull/270)してくださいました。

[@hubumulity](https://github.com/hubumulity) さんによる[字幕表示件数の設定](https://github.com/yuxino/Mimi/pull/303)に感謝します。

[@ryannyui](https://github.com/ryannyui) さんによる [Linux AppImage のグラフィックス互換性修正とツールチェーン更新](https://github.com/yuxino/Mimi/pull/312)に感謝します。

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

[すべての貢献者を見る](https://github.com/yuxino/mimi/graphs/contributors)

## コミュニティへの感謝

[V2EX](https://www.v2ex.com/)、[LINUX DO](https://linux.do/)、[Appinn](https://meta.appinn.net/)、[NodeLoc](https://www.nodeloc.com/)、[Solo](https://solo.xin/)、[Xinquji](https://xinquji.com/posts/859305)、[Eleduck](https://eleduck.com/) のコミュニティの皆さんにも、試用、フィードバック、紹介をしていただき、ありがとうございます。

[MIT](../../LICENSE) © 2026 yuxino

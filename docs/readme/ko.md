<div align="center">
  <img src="../../src-tauri/icons/128x128@2x.png" width="96" alt="mimi">
  <h1>Mimi</h1>
  <p>macOS 13 이상(Apple silicon 및 Intel)과 Windows / Linux x86_64에서 시스템 오디오 또는 마이크를 실시간 자막으로 보고 번역하세요.</p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/github/v/release/yuxino/mimi?style=flat&amp;logo=github&amp;logoColor=white" alt="최신 릴리스"></a>
    <a href="https://github.com/yuxino/mimi/releases"><img src="https://img.shields.io/github/downloads/yuxino/mimi/total?style=flat&amp;labelColor=a85f82&amp;color=e889b5" alt="전체 다운로드"></a>
    <a href="https://github.com/yuxino/mimi/actions/workflows/ci.yml?query=branch%3Amain"><img src="https://img.shields.io/github/actions/workflow/status/yuxino/mimi/ci.yml?style=flat&amp;logo=githubactions&amp;logoColor=white&amp;branch=main&amp;event=push&amp;label=CI" alt="main 브랜치 CI 상태"></a>
    <a href="../../LICENSE"><img src="https://img.shields.io/github/license/yuxino/mimi?style=flat&amp;logo=opensourceinitiative&amp;logoColor=white" alt="MIT 라이선스"></a>
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

![예시 대화를 사용한 Mimi 이중 언어 자막 이미지](../assets/readme-preview.png)

예시 대화로 만든 화면 이미지입니다. [자막과 몰입 모드 데모 보기(이전 UI)](../demos/README.md).

## 들으면서 자막으로 읽기

- **재생 중인 내용 이해하기**: 영화, 방송, 수업의 음성을 플로팅 자막으로 표시합니다. 두 언어, 번역문만, 원문만 볼 수 있습니다.
- **들을 소리 선택하기**: 기본값은 컴퓨터 소리입니다. 마이크나 두 입력을 직접 켤 수 있으며, macOS / Windows 11에서는 앱을 지정할 수 있습니다. Linux에서는 개별 재생 스트림을 선택할 수 있습니다.
- **읽기 편하게 조정하기**: 데스크톱에서 위치, 14–48 px 글자 크기, 색상을 바꿀 수 있습니다. 최근 자막 전체 또는 최신 1–10개만 표시하고, 위로 스크롤해 남아 있는 기록을 읽을 수 있습니다. 몰입 모드는 자막만 남기고, 클릭 통과로 뒤에 있는 앱을 조작할 수 있습니다.
- **자막과 오디오 저장**: 데스크톱에서 자막을 TXT 또는 Markdown으로 내보내거나 선택한 오디오를 WAV로 녹음할 수 있습니다. 두 저장 기능 모두 직접 켜야 작동합니다.

## 시작하기

[데스크톱 버전 다운로드](https://github.com/yuxino/mimi/releases/latest) · [Android 설치 및 사용 안내(영어)](../../android/README.md#start)

데스크톱 버전은 macOS 13+(Apple silicon 및 Intel), Windows x64, Linux x86_64를 지원합니다.

1. **서비스 설정**: 설정 → 모델 및 서비스에서 음성 서비스를 추가합니다. 서비스 선택과 키 발급 방법은 [설정 안내(영어)](../provider-setup.md#choose)를 참고하세요.
2. **소리와 언어 선택**: 오디오 및 언어 설정에서 오디오 입력, 인식 언어, 번역 언어를 선택합니다.
3. **자막 시작**: 자막 페이지에서 실시간 자막을 켜고 필요한 권한을 허용한 뒤, 원하는 콘텐츠를 재생합니다.

Mimi는 무료지만 클라우드 서비스는 요금이 발생할 수 있습니다. 클라우드 인식이나 번역을 사용하면 오디오 또는 인식된 텍스트가 선택한 서비스로 전송됩니다. Apple 로컬 서비스의 이용 조건은 [설정 안내(영어)](../provider-setup.md#apple-speech)를 참고하세요.

## 도움이 필요한가요?

[사용 안내](../usage.ko.md)에서 설치, 권한, 자막 표시, 저장을 설명합니다. 자막이 나오지 않거나 연결에 실패하면 [인식 오류](../usage.ko.md#errors)를 먼저 확인하고, 해결되지 않으면 [문제를 알려 주세요](https://github.com/yuxino/mimi/issues).

## 기여자

참여하려면 [기여 안내(영어·중국어)](../../.github/CONTRIBUTING.md)를 확인하세요.

코드를 작성하고, 문제를 알려 주고, Mimi를 사용하거나 소개해 주시는 모든 분께 감사드립니다 (๑•̀ㅂ•́)و✧

[Android 앱](https://github.com/yuxino/mimi/pull/37)을 만들어 주신 [@yebuwudong](https://github.com/yebuwudong), [자막 애니메이션](https://github.com/yuxino/mimi/pull/67)과 [Windows 오디오 개선](https://github.com/yuxino/mimi/pull/89)에 기여해 주신 [@LLLin000](https://github.com/LLLin000)께 특별히 감사드립니다.

[Gemini 연속 자막과 계획된 연결 교체](https://github.com/yuxino/Mimi/pull/176)를 개선해 주신 [@Chtholly000](https://github.com/Chtholly000)께도 감사드립니다.

[Wayland 자막 컨트롤 수정](https://github.com/yuxino/Mimi/pull/242), [Linux 개별 재생 스트림 캡처](https://github.com/yuxino/Mimi/pull/275), [재생 스트림의 앱 아이콘 표시](https://github.com/yuxino/Mimi/pull/304)를 기여해 주신 [@EmmetZ](https://github.com/EmmetZ)께 감사드립니다.

[DeepSeek 실시간 자막 번역의 사고 모드를 비활성화](https://github.com/yuxino/Mimi/pull/270)해 주신 [@wangmian0](https://github.com/wangmian0)께 감사드립니다.

[자막 표시 개수 설정](https://github.com/yuxino/Mimi/pull/303)을 기여해 주신 [@hubumulity](https://github.com/hubumulity)께 감사드립니다.

[Linux AppImage 그래픽 호환성 수정과 도구 체인 업그레이드](https://github.com/yuxino/Mimi/pull/312)에 기여해 주신 [@ryannyui](https://github.com/ryannyui)께 감사드립니다.

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

[모든 기여자](https://github.com/yuxino/mimi/graphs/contributors)

## 커뮤니티

[V2EX](https://www.v2ex.com/), [LINUX DO](https://linux.do/), [Appinn](https://meta.appinn.net/), [NodeLoc](https://www.nodeloc.com/), [Solo](https://solo.xin/), [Xinquji](https://xinquji.com/posts/859305), [Eleduck](https://eleduck.com/)에서 Mimi를 사용하고 의견을 나누며 소개해 주신 분들께 감사드립니다.

[MIT](../../LICENSE) © 2026 yuxino

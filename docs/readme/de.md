<div align="center">
  <img src="../../src-tauri/icons/128x128@2x.png" width="96" alt="mimi">
  <h1>Mimi</h1>
  <p>Live-Untertitel und Übersetzung für Systemaudio oder dein Mikrofon unter macOS 13+ (Apple-Chip und Intel) und Windows / Linux x86_64.</p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/github/v/release/yuxino/mimi?style=flat&amp;logo=github&amp;logoColor=white" alt="Neueste Version"></a>
    <a href="https://github.com/yuxino/mimi/releases"><img src="https://img.shields.io/github/downloads/yuxino/mimi/total?style=flat&amp;labelColor=a85f82&amp;color=e889b5" alt="Downloads insgesamt"></a>
    <a href="https://github.com/yuxino/mimi/actions/workflows/ci.yml?query=branch%3Amain"><img src="https://img.shields.io/github/actions/workflow/status/yuxino/mimi/ci.yml?style=flat&amp;logo=githubactions&amp;logoColor=white&amp;branch=main&amp;event=push&amp;label=CI" alt="CI-Status von main"></a>
    <a href="../../LICENSE"><img src="https://img.shields.io/github/license/yuxino/mimi?style=flat&amp;logo=opensourceinitiative&amp;logoColor=white" alt="MIT-Lizenz"></a>
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

![Mimis zweisprachige Untertitel mit fiktivem Beispieldialog](../assets/readme-preview.png)

Oberflächenillustration mit Beispieldialog. [Untertitel und immersiven Modus ansehen (ältere Oberfläche)](../demos/README.md).

## Beim Hören mitlesen

- **Verstehen, was läuft**: schwebende Untertitel für Sprache in Filmen, Streams und Unterricht. Zeige beide Sprachen, nur die Übersetzung oder nur das Original.
- **Audio auswählen**: standardmäßig den Computerton; Mikrofon oder beide Quellen aktivierst du selbst. Unter macOS / Windows 11 kannst du eine einzelne App wählen. Unter Linux kannst du einen einzelnen Wiedergabestream auswählen.
- **Lesbarkeit anpassen**: Ändere am Desktop Position, Schriftgröße von 14–48 px und Farbe. Zeige alle zuletzt erfassten Untertitel oder nur die neuesten 1–10 Einträge und scrolle nach oben, um den verfügbaren Verlauf zu lesen. Im immersiven Modus bleibt nur der Text; mit Klickdurchleitung bedienst du die App darunter.
- **Untertitel und Audio speichern**: Exportiere am Desktop Untertitel als TXT oder Markdown oder nimm die gewählte Audioquelle als WAV auf. Beide Speicherfunktionen sind zunächst ausgeschaltet.

## Erste Schritte

[Desktop-Version herunterladen](https://github.com/yuxino/mimi/releases/latest) · [Android einrichten (Englisch)](../../android/README.md#start)

Die Desktop-Version unterstützt macOS 13+ (Apple-Chip und Intel), Windows x64 und Linux x86_64.

1. **Dienst einrichten**: Füge unter Einstellungen → Modelle & Dienste einen Sprachdienst hinzu. Die [Einrichtungsanleitung (Englisch)](../provider-setup.md#choose) erklärt die Auswahl und den Erhalt der Zugangsdaten.
2. **Audio und Sprachen wählen**: Wähle in den Audio- und Spracheinstellungen die Audioquelle, die gesprochene Sprache und die Zielsprache.
3. **Untertitel starten**: Aktiviere Live-Untertitel auf der Untertitelseite, erteile die angefragten Berechtigungen und spiele deinen Inhalt ab.

Mimi ist kostenlos; Cloud-Dienste können Gebühren erheben. Bei Erkennung oder Übersetzung in der Cloud werden Audio oder erkannter Text an den gewählten Dienst gesendet. Die Voraussetzungen für lokale Apple-Dienste stehen in der [Einrichtungsanleitung (Englisch)](../provider-setup.md#apple-speech).

## Hilfe benötigt?

Die [Nutzungsanleitung](../usage.de.md) erklärt Installation, Berechtigungen, Untertitelanzeige und gespeicherte Sitzungen. Bei fehlenden Untertiteln oder Verbindungsproblemen beginne mit [Erkennungsfehlern](../usage.de.md#errors). Bleibt das Problem bestehen, [melde es](https://github.com/yuxino/mimi/issues).

## Mitwirkende

Du möchtest mithelfen? Beginne mit der [Beitragsanleitung (Englisch/Chinesisch)](../../.github/CONTRIBUTING.md).

Danke an alle, die Code schreiben, Probleme melden, Mimi ausprobieren oder weiterempfehlen (๑•̀ㅂ•́)و✧

Besonderer Dank gilt [@yebuwudong](https://github.com/yebuwudong) für die [Android-App](https://github.com/yuxino/mimi/pull/37) und [@LLLin000](https://github.com/LLLin000) für die [Untertitelanimation](https://github.com/yuxino/mimi/pull/67) und [Verbesserungen am Windows-Audio](https://github.com/yuxino/mimi/pull/89).

Danke auch an [@Chtholly000](https://github.com/Chtholly000) für die Verbesserungen an [Geminis fortlaufenden Untertiteln und dem geplanten Verbindungswechsel](https://github.com/yuxino/Mimi/pull/176).

Danke an [@EmmetZ](https://github.com/EmmetZ) für die [Korrektur der Wayland-Untertitelsteuerung](https://github.com/yuxino/Mimi/pull/242), die [Aufnahme einzelner Wiedergabestreams unter Linux](https://github.com/yuxino/Mimi/pull/275) und die [App-Symbole für Wiedergabestreams](https://github.com/yuxino/Mimi/pull/304).

Danke an [@wangmian0](https://github.com/wangmian0) für die [Deaktivierung des Denkmodus von DeepSeek bei der Live-Untertitelübersetzung](https://github.com/yuxino/Mimi/pull/270).

Danke an [@hubumulity](https://github.com/hubumulity) für die [Einstellung der Anzahl sichtbarer Untertitel](https://github.com/yuxino/Mimi/pull/303).

Danke an [@ryannyui](https://github.com/ryannyui) für die [Grafikkompatibilitätskorrekturen für das Linux-AppImage und die Aktualisierung der Build-Werkzeuge](https://github.com/yuxino/Mimi/pull/312).

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

[Alle Mitwirkenden](https://github.com/yuxino/mimi/graphs/contributors)

## Community

Danke an die Menschen bei [V2EX](https://www.v2ex.com/), [LINUX DO](https://linux.do/), [Appinn](https://meta.appinn.net/), [NodeLoc](https://www.nodeloc.com/), [Solo](https://solo.xin/), [Xinquji](https://xinquji.com/posts/859305) und [Eleduck](https://eleduck.com/), die Mimi ausprobieren, Rückmeldungen geben und weiterempfehlen.

[MIT](../../LICENSE) © 2026 yuxino

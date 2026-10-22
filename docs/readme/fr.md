<div align="center">
  <img src="../../src-tauri/icons/128x128@2x.png" width="96" alt="mimi">
  <h1>Mimi</h1>
  <p>Sous-titres et traduction en direct pour l’audio du système ou le microphone sur macOS 13+ (puces Apple et Intel), Windows et Linux x86_64.</p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/github/v/release/yuxino/mimi?style=flat&amp;logo=github&amp;logoColor=white" alt="Dernière version"></a>
    <a href="https://github.com/yuxino/mimi/releases"><img src="https://img.shields.io/github/downloads/yuxino/mimi/total?style=flat&amp;labelColor=a85f82&amp;color=e889b5" alt="Nombre total de téléchargements"></a>
    <a href="https://github.com/yuxino/mimi/actions/workflows/ci.yml?query=branch%3Amain"><img src="https://img.shields.io/github/actions/workflow/status/yuxino/mimi/ci.yml?style=flat&amp;logo=githubactions&amp;logoColor=white&amp;branch=main&amp;event=push&amp;label=CI" alt="État de la CI sur main"></a>
    <a href="../../LICENSE"><img src="https://img.shields.io/github/license/yuxino/mimi?style=flat&amp;logo=opensourceinitiative&amp;logoColor=white" alt="Licence MIT"></a>
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

![Illustration des sous-titres bilingues de Mimi avec un dialogue fictif](../assets/readme-preview.png)

Illustration de l’interface avec un dialogue d’exemple. [Voir les sous-titres et le mode immersif (ancienne interface)](../demos/README.md).

## Lire tout en écoutant

- **Comprendre ce qui passe** : des sous-titres flottants pour les voix des films, diffusions et cours. Affichez les deux langues, la traduction seule ou l’original seul.
- **Choisir la source** : le son de l’ordinateur par défaut ; activez vous-même le microphone ou les deux sources. Sélectionnez une application sous macOS / Windows 11. Sous Linux, choisissez un flux de lecture individuel.
- **Adapter l’affichage** : sur ordinateur, réglez la position, la taille du texte de 14 à 48 px et la couleur. Affichez tous les sous-titres récents ou seulement les 1 à 10 derniers, et remontez pour lire l’historique disponible. Le mode immersif ne garde que le texte ; la transparence aux clics permet d’utiliser l’application dessous.
- **Enregistrer les sous-titres et le son** : sur ordinateur, exportez les sous-titres en TXT ou Markdown, ou enregistrez la source audio choisie en WAV. Ces deux options d’enregistrement doivent être activées manuellement.

## Premiers pas

[Télécharger pour ordinateur](https://github.com/yuxino/mimi/releases/latest) · [Installation sur Android (anglais)](../../android/README.md#start)

La version pour ordinateur prend en charge macOS 13+ (puce Apple et Intel), Windows x64 et Linux x86_64.

1. **Configurez un service** dans Réglages → Modèles et services. Le [guide de configuration (anglais)](../provider-setup.md#choose) explique comment choisir un service et obtenir ses identifiants.
2. **Choisissez le son et les langues** dans les réglages audio : source à capturer, langue parlée et langue de traduction.
3. **Lancez les sous-titres** depuis la page Sous-titres. Activez les sous-titres en direct, accordez les autorisations demandées, puis lancez votre contenu.

Mimi est gratuit ; les services cloud peuvent être payants. La reconnaissance ou la traduction dans le cloud transmet l’audio ou le texte reconnu au service choisi. Les conditions des services locaux Apple figurent dans le [guide de configuration (anglais)](../provider-setup.md#apple-speech).

## Besoin d’aide ?

Le [guide d’utilisation](../usage.fr.md) couvre l’installation, les autorisations, l’affichage et les sessions enregistrées. Si les sous-titres manquent ou si la connexion échoue, consultez les [erreurs de reconnaissance](../usage.fr.md#errors). Si le problème persiste, [signalez-le](https://github.com/yuxino/mimi/issues).

## Contributeurs

Pour participer, consultez le [guide de contribution (anglais/chinois)](../../.github/CONTRIBUTING.md).

Merci à toutes les personnes qui écrivent du code, signalent des problèmes, essaient Mimi ou le font connaître (๑•̀ㅂ•́)و✧

Un grand merci à [@yebuwudong](https://github.com/yebuwudong) pour l’[application Android](https://github.com/yuxino/mimi/pull/37), ainsi qu’à [@LLLin000](https://github.com/LLLin000) pour l’[animation des sous-titres](https://github.com/yuxino/mimi/pull/67) et les [améliorations audio sous Windows](https://github.com/yuxino/mimi/pull/89).

Merci aussi à [@Chtholly000](https://github.com/Chtholly000) pour les améliorations des [sous-titres continus de Gemini et du renouvellement planifié des connexions](https://github.com/yuxino/Mimi/pull/176).

Merci à [@EmmetZ](https://github.com/EmmetZ) pour la [correction des commandes sous Wayland](https://github.com/yuxino/Mimi/pull/242), la [capture de flux de lecture individuels sous Linux](https://github.com/yuxino/Mimi/pull/275) et les [icônes des applications dans la liste des flux](https://github.com/yuxino/Mimi/pull/304).

Merci à [@wangmian0](https://github.com/wangmian0) d’avoir [désactivé le mode de raisonnement de DeepSeek pour la traduction des sous-titres en direct](https://github.com/yuxino/Mimi/pull/270).

Merci à [@hubumulity](https://github.com/hubumulity) pour le [réglage du nombre de sous-titres affichés](https://github.com/yuxino/Mimi/pull/303).

Merci à [@ryannyui](https://github.com/ryannyui) pour les [correctifs de compatibilité graphique de l’AppImage Linux et la mise à jour des outils de compilation](https://github.com/yuxino/Mimi/pull/312).

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

[Tous les contributeurs](https://github.com/yuxino/mimi/graphs/contributors)

## Communauté

Merci aux membres de [V2EX](https://www.v2ex.com/), [LINUX DO](https://linux.do/), [Appinn](https://meta.appinn.net/), [NodeLoc](https://www.nodeloc.com/), [Solo](https://solo.xin/), [Xinquji](https://xinquji.com/posts/859305) et [Eleduck](https://eleduck.com/) d’avoir essayé Mimi, partagé leurs retours et fait connaître le projet.

[MIT](../../LICENSE) © 2026 yuxino

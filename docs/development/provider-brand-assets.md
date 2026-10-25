# Provider brand assets

Retrieved from official sites on 2026-10-02 and 2026-10-03. Assets are bundled locally in
`src/assets/providers`; service settings never fetch icons at runtime.
`ProviderIcon` uses a 32 px image inside a 32 or 36 px container and preserves
each image's aspect ratio and brand colors. Marks identify the selected service;
their ownership remains with the respective providers.

| Provider | Local asset | Official source | Preparation |
| --- | --- | --- | --- |
| Alibaba Cloud | `alibaba-cloud.svg` | [Official homepage](https://www.alibabacloud.com/en) header SVG | Extracted the original symbol's rect and two paths; omitted the wordmark, preserved geometry and orange fill. |
| OpenAI | `openai-black.svg`, `openai-white.svg` | [Brand guidelines](https://openai.com/brand/), [official logo ZIP](https://cdn.openai.com/brand/OpenAI-Logos-2025.zip) | Unmodified monoblossom SVGs; choose the provided white version in dark settings. |
| Gemini | `gemini.svg` | [Official Gemini site](https://gemini.google.com/), [official SVG](https://www.gstatic.com/lamda/images/gemini_sparkle_aurora_33f86dc0c0257da337c63.svg) | Unmodified SVG, including its embedded raster artwork. |
| Azure | `azure.svg` | [Microsoft shared media index](https://learn.microsoft.com/static/media/index.html), [official SVG](https://learn.microsoft.com/static/media/product/azure/azure.svg) | Unmodified Azure brand symbol; avoids the general Microsoft favicon. |
| Volcano Engine | `volcano-engine.png` | [Official homepage](https://www.volcengine.com/), [official icon](https://portal.volccdn.com/obj/volcfe/misc/favicon.png) | Original transparent 192 px PNG. |
| Tencent Cloud | `tencent-cloud.svg` | [Official homepage](https://cloud.tencent.com/), [official navigation SVG](https://cloudcache.tencent-cloud.com/open_proj/proj_qcloud_v2/gateway/portal/css/img/nav/slice/logo-9225-color.svg) | Extracted the original three-color symbol group, omitted the wordmark; paths and colors unchanged. |
| Baidu Translate | `baidu-translate.jpg` | [Official download page](https://fanyi.baidu.com/download?appchannel=wisebottom) links to [Baidu's App Store listing](https://apps.apple.com/cn/app/id605670941); [Apple metadata](https://itunes.apple.com/lookup?id=605670941&country=cn), [official artwork](https://is1-ssl.mzstatic.com/image/thumb/Purple221/v4/a3/39/44/a339447f-a0d6-6090-15af-e277f67511a4/AppIcon-0-0-1x_U007emarketing-0-8-0-sRGB-85-220.png/512x512bb.jpg) | Unmodified 512 × 512 marketing artwork. The metadata identifies `com.baidu.translate`, published by Beijing Baidu Netcom Science & Technology Co.,Ltd. Replaces the 32 px favicon; no local upscaling. |
| xAI | `xai.png` | [Official API console](https://console.x.ai/), [official favicon](https://console.x.ai/favicon.ico) | Lossless ICO to PNG conversion, original 32 px pixels. Uses the API console's xAI mark. |
| DeepL | `deepl-blue.svg`, `deepl-white.svg` | [Official press brand assets](https://www.deepl.com/en/press), [official logo ZIP](https://assets.ctfassets.net/pplyeawnfzc7/2vv9U6WXbhq7TteXDBMIAf/6da6c10c83676960468a3a8b58956757/DeepL_Official_Logo_-20250707T082253Z-1-001.zip) | Extracted the original symbol path from the digital RGB blue and white SVGs; omitted the wordmark, preserved path geometry and original fills. Choose the supplied white version in dark settings. |
| DeepLX adapter | `deeplx.svg` | [Legacy official documentation site](https://deeplx.owo.network/), [published header logo](https://deeplx.owo.network/logo.svg) | Cropped transparent viewBox padding to match the visible size of the DeepL mark; original paths, fills and aspect ratio are preserved. A white backing in dark settings keeps the original black artwork visible without recoloring it. |
| Apple Speech | `apple-black.svg`, `apple-white.svg` | [Apple homepage](https://www.apple.com/) global navigation Apple SVG, retrieved 2026-10-05 | Original navigation symbol path, unchanged geometry. Cropped empty navigation padding in the viewBox; explicit black/white fills reproduce the monochrome navigation mark in light/dark themes. Desktop macOS capability only; no Android asset or support implied. |

The DeepLX asset identifies Mimi's existing legacy adapter using the mark
published by its documentation site. It repeats DeepL's symbol; this does not
claim an independently designed mark or affiliation with DeepL. The
[current upstream repository](https://github.com/OwO-Network/DLX) was renamed
to DLX and removed DeepL branding; this asset does not identify that newer brand.

ChatMock and OpenAI-compatible text translation use the neutral Lucide `Languages` glyph.
Compatibility identifies an API format, not OpenAI ownership or affiliation.

The compatible route's Quick setup picker identifies concrete text services with
their own marks in both the selected value and every dropdown row. Custom keeps
the neutral language glyph. `TextTranslationPresetIcon` reuses existing company
marks where appropriate and bundles additional artwork documented in
[provider notices](../../src/assets/providers/NOTICE.md). Qianfan uses the Baidu
AI Cloud mark, not the separate Baidu Translation app icon. Monochrome marks
follow the selected light/dark theme; supplied OpenRouter and LM Studio variants
keep their original colors and proportions. These are UI assets only: preset
selection, credential ownership, model IDs and network behavior are unchanged.

Recognition and text translation call sites resolve their service separately.
Legacy DeepLX profiles still use Alibaba Cloud's icon for speech recognition;
their text translation uses the legacy DeepLX documentation mark.

Theme selection uses inherited `--provider-light-display`,
`--provider-dark-display`, and `--provider-backing-background` properties.
Portalled service menus copy these properties from their trigger so their
artwork keeps the settings window's selected theme outside its DOM ancestor.

## Android reuse

Android reuses these same marks in the service list, editor headings and text-translation
selector. SVGs become equivalent vector drawables or 128-pixel PNG exports; source fills
and geometry remain unchanged. Transparent padding is normalized for consistent visible
size. Supplied OpenAI/DeepL dark variants and the DeepLX white backing remain theme-aware.
Existing raster brand assets are copied directly. See `android/THIRD_PARTY_NOTICES.md`.

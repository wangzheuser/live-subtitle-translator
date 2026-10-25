# 共用服务说明

桌面和 Android 的服务能力、开通条件及阿里云模式说明，统一维护在
[`shared/provider-copy.json`](../../shared/provider-copy.json)。八种界面语言必须一起更新。

修改后运行：

```sh
python3 -B scripts/sync-provider-copy.py
python3 -B scripts/sync-provider-copy.py --check
```

生成结果是桌面的 `src/lib/generated/providerCopy.ts`、Android 各语言的
`provider_strings.xml` 和 `GeneratedTextTranslationPresets.kt`。不要直接改生成文件，也不要把同一句复制回平台语言文件。
桌面设置的服务商帮助和阿里云模式选择、Android 服务介绍／配置帮助／模式选择
都读取这些结果；Android 的服务开通提示也放在这份目录里。

检查会拒绝缺少语言、重复 Android 资源、失去桌面绑定以及未重新生成的文案。
阿里同传的翻译语言数取自共享协议目录，不在两端分别填写。

`compatiblePresets` 共用 OpenAI 兼容文字服务的名称、地址和示例模型。模型可为空，
由用户填写已开通或已下载的模型；预设只修改编辑草稿，不创建新协议、不保存或联网。
生成检查也覆盖预设字段、地址限制和两端输出。服务能力仍由共享 Rust 运行时决定。

维护预设时，对照共享协议契约和服务开通文档核查遗漏；预设数量不等于支持范围。
整链语音服务和同厂商的兼容文字 API 分开核对，并说明各自所需的凭据。

`scope` 标明适用平台。Apple 本地服务、自定义识别等仅桌面支持的功能，以及
录音权限、系统设置路径、导航、保存反馈等平台交互说明，仍由各平台维护。
不要为了共用一句话暗示另一端已有功能。

README 和服务开通教程负责入口、操作顺序与链接，不重复维护界面帮助全文。
服务能力改变时，先确认共享运行时及两端入口，再改这份目录；生成成功不代表
真实服务或原生界面已经验收。

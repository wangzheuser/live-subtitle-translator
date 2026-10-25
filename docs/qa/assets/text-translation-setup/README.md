# Text translation setup review

Actual Chromium renders of `scripts/fixtures/settings-layout.html`, captured on
2026-10-25 from the implementation in this PR. The app's real React components
and theme styles are used with synthetic settings and credential-presence
flags. Screenshots show no real account or key and do not prove native browser
opening, account activation, model access, provider translation or audio capture.

- `cloud-tokenhub-light.png`: 760 × 1000 CSS px, Chinese/light. Model setup guide
  and the selected text product's Get API key action stay beside their fields.
- `local-ollama-dark.png`: 520 × 1000 CSS px, Chinese/dark. Local installation and
  model setup use their own guide; no cloud-key acquisition action is offered.
- `keyless-index-light.png`: 760 × 1000 CSS px, Chinese/light. The public example
  explicitly shows that no API key is needed and retains its setup guide.

The fixture records catalog ID/page requests instead of opening external sites.
Explicit TokenHub navigation was checked without saving, revealing a credential,
probing a service or starting capture. Unit/IPC tests cover fixed catalog
destinations, Settings-only access, sanitized failures, selection without
navigation and preservation of unsaved model/key fields. Android source includes
native intent-interception checks; compiling those checks does not establish
that they were executed on a device. The running production app was preserved.

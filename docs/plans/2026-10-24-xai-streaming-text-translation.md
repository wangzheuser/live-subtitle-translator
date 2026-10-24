# xAI streaming recognition and text translation

**Goal:** Let xAI users receive captions during continuous playback and choose
the Grok text model, using their xAI API key for both stages (issue #313).

**Architecture:** Add an explicit xAI speech mode. Historical profiles retain
Grok Voice; new profiles select streaming recognition and text translation.
The shared Rust runtime owns xAI STT, transcript boundaries, the text request,
bounded scheduling, probes and recovery. Desktop and Android only store and
edit mode/model metadata and pass it into that runtime.

**Implementation:**

1. Preserve legacy mode on deserialization; default new xAI profiles to the new
   route. Validate model IDs and reject xAI options on unrelated providers.
2. Connect to xAI's dedicated `/v1/stt` WebSocket, wait for readiness, send
   binary PCM16 at 16 kHz, and enable intermediate recognition results.
   Translate authoritative chunk finals during continuous speech. Distinguish
   replaceable drafts, chunk finals and stitched utterance finals, without
   duplicating committed chunks or keeping a full-session transcript.
3. Reuse the existing bounded recognition/text pipeline and Chat Completions
   client. Fix the text endpoint to xAI and expose the text model. Both stages
   intentionally use the same profile-scoped xAI key. Default to `grok-4.3`
   with its documented non-reasoning request; do not adopt the retired Fast
   aliases suggested in the issue. No credentials are copied across profiles.
4. Preserve original-only captions, independent network routes, sanitized
   errors, per-stage checks, lifecycle ownership and a single xAI provider badge.
   Expose the same choice and model on desktop and Android with native controls.
5. Add protocol/loopback regressions, direct Rust and actual JNI coverage;
   inspect the signed development UI before full acceptance. Compare a continuous
   speech sample and the same sample with pauses if authorized xAI credentials
   are available. Synthetic tests do not establish live latency or quality.

**Acceptance:** Continuous chunk finals reach text translation before audio ends;
partial revisions cannot overwrite finals; utterance finals do not duplicate
chunk translations; clear/reconnect fence late events; Stop drains only within
the existing budget; original-only mode makes no text requests. Saved legacy
Voice profiles keep their route. Mode/model saves and both checks report errors
beside the proper action. Shared behavior passes direct Rust and actual JNI.

Keep the delivery PR open for review. No merge, issue closure or release is part
of this request.

References: [xAI STT](https://docs.x.ai/developers/model-capabilities/audio/speech-to-text),
[Chat Completions](https://docs.x.ai/developers/model-capabilities/legacy/chat-completions),
[model migration](https://docs.x.ai/developers/migration/may-15-retirement).

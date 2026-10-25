#!/usr/bin/env python3
"""Guard shared copy ownership, locale coverage and check-only drift detection."""
import contextlib
import importlib.util
import io
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

SCRIPT = Path(__file__).with_name("sync-provider-copy.py")
SPEC = importlib.util.spec_from_file_location("provider_copy", SCRIPT)
MODULE = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(MODULE)
ROOT = SCRIPT.parents[1]


class SharedProviderCopyTest(unittest.TestCase):
    def test_checked_in_outputs_are_current(self):
        for path, expected in MODULE.generated_files().items():
            self.assertEqual(expected, path.read_text(encoding="utf-8"), str(path.relative_to(ROOT)))

    def fixture(self, root):
        source = json.loads(MODULE.SOURCE.read_text(encoding="utf-8"))
        files = [MODULE.SOURCE, ROOT / "shared/translation-contracts.json", ROOT / "src/lib/i18n.ts",
                 ROOT / "src/windows/settings/AlibabaRealtimeSettings.tsx"]
        files += [ROOT / f"src/lib/locales/{locale}.ts" for locale in source["locales"] if locale not in {"zh", "en", "ja"}]
        for original in files:
            target = root / original.relative_to(ROOT)
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_text(original.read_text(encoding="utf-8"), encoding="utf-8")
        for directory in source["locales"].values():
            (root / "android/app/src/main/res" / directory).mkdir(parents=True)
        return patch.multiple(MODULE, ROOT=root, SOURCE=root / "shared/provider-copy.json",
                              TS_OUTPUT=root / "src/lib/generated/providerCopy.ts",
                              KOTLIN_OUTPUT=root / "android/app/src/main/java/app/yuxino/mimi/android/GeneratedTextTranslationPresets.kt",
                              RESOURCE_ROOT=root / "android/app/src/main/res")

    def test_generation_and_check_work_with_legacy_windows_locale(self):
        original_open = Path.open

        def legacy_open(path, mode="r", buffering=-1, encoding=None, errors=None, newline=None):
            if "b" not in mode and encoding is None:
                encoding = "cp1252"
            return original_open(path, mode, buffering, encoding, errors, newline)

        with patch.object(Path, "open", legacy_open), tempfile.TemporaryDirectory() as directory:
            with self.fixture(Path(directory)), contextlib.redirect_stdout(io.StringIO()):
                with patch("sys.argv", [str(SCRIPT)]):
                    self.assertEqual(0, MODULE.main())
                for path, expected in MODULE.generated_files().items():
                    self.assertEqual(expected, path.read_bytes().decode("utf-8").replace("\r\n", "\n"))
                with patch("sys.argv", [str(SCRIPT), "--check"]):
                    self.assertEqual(0, MODULE.main())

    def test_missing_translation_is_rejected(self):
        with tempfile.TemporaryDirectory() as directory:
            with self.fixture(Path(directory)):
                data = json.loads(MODULE.SOURCE.read_text(encoding="utf-8"))
                del data["entries"]["providerAlibabaDescription"]["translations"]["ja"]
                MODULE.SOURCE.write_text(json.dumps(data), encoding="utf-8")
                with self.assertRaisesRegex(ValueError, "Missing or extra translations"):
                    MODULE.generated_files()

    def test_restoring_an_old_platform_literal_is_rejected(self):
        with tempfile.TemporaryDirectory() as directory:
            with self.fixture(Path(directory)):
                duplicate = MODULE.RESOURCE_ROOT / "values/strings.xml"
                duplicate.write_text('<resources><string name="guide_detail_dashscope">Old text</string></resources>', encoding="utf-8")
                with self.assertRaisesRegex(ValueError, "Duplicate shared resource"):
                    MODULE.generated_files()
                duplicate.unlink()
                owner = MODULE.ROOT / "src/lib/i18n.ts"
                owner.write_text(owner.read_text(encoding="utf-8").replace('PROVIDER_COPY["zh"].providerAlibabaDescription', '"Old text"'), encoding="utf-8")
                with self.assertRaisesRegex(ValueError, "Missing desktop binding"):
                    MODULE.generated_files()

    def test_check_detects_drift_without_overwriting_it(self):
        with tempfile.TemporaryDirectory() as directory:
            with self.fixture(Path(directory)), contextlib.redirect_stdout(io.StringIO()), contextlib.redirect_stderr(io.StringIO()):
                with patch("sys.argv", [str(SCRIPT)]):
                    self.assertEqual(0, MODULE.main())
                with patch("sys.argv", [str(SCRIPT), "--check"]):
                    self.assertEqual(0, MODULE.main())
                    MODULE.TS_OUTPUT.write_text("// stale\n", encoding="utf-8")
                    self.assertEqual(1, MODULE.main())
                    self.assertEqual("// stale\n", MODULE.TS_OUTPUT.read_text(encoding="utf-8"))

    def test_compatible_presets_share_order_and_values(self):
        presets = json.loads(MODULE.SOURCE.read_text(encoding="utf-8"))["compatiblePresets"]
        outputs = MODULE.generated_files()
        ts = outputs[MODULE.TS_OUTPUT].split("export const OPENAI_COMPATIBLE_PRESETS = ", 1)[1]
        self.assertEqual(presets, json.loads(ts.removesuffix(" as const;\n")))
        kotlin = outputs[MODULE.KOTLIN_OUTPUT]
        positions = []
        for preset in presets:
            row = "TextTranslationPreset(" + ", ".join(MODULE.kotlin_string(preset[key]) for key in ("id", "label", "endpoint", "model")) + ")"
            positions.append(kotlin.index(row))
        self.assertEqual(sorted(positions), positions)
        self.assertEqual('"synthetic\\$value"', MODULE.kotlin_string("synthetic$value"))

    def test_invalid_compatible_presets_are_rejected(self):
        preset = {"id": "synthetic", "label": "Synthetic", "endpoint": "https://example.com/v1", "model": ""}
        cases = [
            [preset, preset],
            [{**preset, "id": "not-an-id"}],
            [{**preset, "label": ""}],
            [{**preset, "model": "bad\nmodel"}],
            [{**preset, "model": "bad\u0085model"}],
            [{**preset, "model": " model "}],
            [{**preset, "model": "x" * 257}],
            [{**preset, "endpoint": "http://example.com/v1"}],
            [{**preset, "endpoint": "https://key@example.com/v1"}],
            [{**preset, "endpoint": "https://example.com/v1?key=synthetic"}],
            [{**preset, "endpoint": "https://example.com/v1#fragment"}],
            [{**preset, "endpoint": "https://example.com:invalid/v1"}],
            [{**preset, "unexpected": "field"}],
        ]
        for presets in cases:
            with self.subTest(presets=presets), self.assertRaisesRegex(ValueError, "compatible preset"):
                MODULE.compatible_presets({"compatiblePresets": presets})
        self.assertEqual([preset], MODULE.compatible_presets({"compatiblePresets": [preset]}))
        for endpoint in ("http://127.0.0.1:11434/v1", "http://[::1]:1234/v1"):
            local = {**preset, "endpoint": endpoint}
            self.assertEqual([local], MODULE.compatible_presets({"compatiblePresets": [local]}))

    def test_check_detects_kotlin_drift_without_overwriting_it(self):
        with tempfile.TemporaryDirectory() as directory:
            with self.fixture(Path(directory)), contextlib.redirect_stdout(io.StringIO()), contextlib.redirect_stderr(io.StringIO()):
                with patch("sys.argv", [str(SCRIPT)]):
                    self.assertEqual(0, MODULE.main())
                MODULE.KOTLIN_OUTPUT.write_text("// stale\n", encoding="utf-8")
                with patch("sys.argv", [str(SCRIPT), "--check"]):
                    self.assertEqual(1, MODULE.main())
                self.assertEqual("// stale\n", MODULE.KOTLIN_OUTPUT.read_text(encoding="utf-8"))

    def test_language_count_tracks_shared_contract(self):
        with tempfile.TemporaryDirectory() as directory:
            with self.fixture(Path(directory)):
                contract = MODULE.ROOT / "shared/translation-contracts.json"
                data = json.loads(contract.read_text(encoding="utf-8"))
                count = len(data["alibabaRealtimeLanguages"]["languages"])
                data["alibabaRealtimeLanguages"]["languages"].append({"code": "synthetic"})
                contract.write_text(json.dumps(data), encoding="utf-8")
                output = MODULE.generated_files()[MODULE.RESOURCE_ROOT / "values/provider_strings.xml"]
                self.assertIn(f"{count + 1} translation languages", output)
                self.assertNotIn("{liveLanguageCount}", output)


if __name__ == "__main__":
    unittest.main()

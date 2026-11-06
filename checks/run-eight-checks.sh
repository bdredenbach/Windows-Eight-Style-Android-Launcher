#!/usr/bin/env bash
set -euo pipefail
launcher=com.nthexperience.desktop.eight
failed=0
collect() {
  local suite="$1" name
  mkdir -p "screenshots/$suite"
  for name in $(adb shell run-as "$launcher" ls files 2>/dev/null | tr -d '\r'); do
    case "$name" in *.png|*tree.txt|*capture.txt) adb exec-out run-as "$launcher" cat "files/$name" > "screenshots/$suite/$name" 2>/dev/null || true;; esac
  done
  adb logcat -d -t 400 > "screenshots/$suite/device-log.txt" || true
}
adb install -r fixture/build/outputs/apk/debug/fixture-debug.apk
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb install -r app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
for suite in EightSmoke EightImmersiveSmoke BrowserSmoke ExplorerSmoke PhotoSmoke MediaSmoke; do
  adb shell wm size reset
  adb shell wm density reset
  adb shell am force-stop "$launcher"
  adb shell pm clear "$launcher"
  adb shell appops set --uid "$launcher" MANAGE_EXTERNAL_STORAGE allow
  adb shell input keyevent KEYCODE_WAKEUP
  adb shell wm dismiss-keyguard
  output="eight-$suite.txt"
  if ! timeout -k 3s 300s adb shell am instrument -w "$launcher.test/com.nthexperience.desktop.$suite" | tee "$output"; then failed=1; fi
  if ! grep -q 'PASS:' "$output" || grep -Eq 'FAIL:|shortMsg=|Process crashed|INSTRUMENTATION_FAILED' "$output"; then failed=1; fi
  collect "$suite"
  if [[ "$failed" -ne 0 ]]; then exit "$failed"; fi
done
exit "$failed"

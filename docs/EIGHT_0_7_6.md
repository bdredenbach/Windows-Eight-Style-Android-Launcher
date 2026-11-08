# Nth Desktop Eight 0.7.6 — Window touches and Nota commands

The owner recording `224478.mp4` identifies touches on blank parts of built-in windows reaching desktop shortcuts underneath. The accompanying Nota screenshot has a browser-imported passage and Summarize task selected, so `open chatgpt` was sent to the writing assistant rather than interpreted as an app command.

## Updated behavior

`DesktopWindow` retains otherwise unhandled touch sequences after its children have had their normal dispatch. Blank body areas, side borders and chrome cannot activate desktop shortcuts underneath. Existing child controls, scrolling, caption dragging and corner resizing keep their handlers. Desktop outside the floating window remains usable. The shared Eight surface layer also retains otherwise unhandled touches while Start, PC settings or an immersive app covers Desktop; its transparent desktop-only state remains pass-through. The 8.1 peek taskbar remains a child of that layer and receives its normal events.

Nota has an always-visible **New command** action beside its task selector, including in compact landscape layouts. Choose it, type `open ChatGPT` or another installed app name and press **Send**. One matching installed app or supported built-in app opens on Send through the existing local Ten command resolver. Ambiguous names show distinct app-icon choices with 18sp bold labels. Same-name entries in the same package include their activity names before the package so the choices remain distinguishable. Unknown apps offer Start search. Settings, website and search shortcuts keep their explicit buttons. Returning to Nota never replays the request; editing removes old choices.

Fresh commands are resolved locally without a writing-model request. Imported browser text, private passages, restored writing history and generated replies never execute app commands. New command clears the preceding composer and waits for new input. Choosing Apps & settings on an imported passage explains how to start a new command instead of summarizing or launching the passage. Ordinary drafts retain the selected task and writing origin through refresh and settings backup/restore; legacy drafts without origin metadata are treated as writing. Private passages remain excluded from ordinary draft persistence and generic window snapshots. Nota restores its full-length saved draft rather than the generic 2,000-character text snapshot. Legacy path-based snapshots cannot overwrite the composer.

## Validation

- [PR #12](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/12), tested source `a875073913ba7f4dcbe21df65bded5e0185a2ed3`, tree `ea9117892d652303dbbbc3a1b4f6d2b264f64067`, based on main `1abfd53619bd1d50ba77cf0696224004df0127a6`. Merged as `ee98f2638ce8474185e9dfbf30ede86ee2dd640a` with the exact tested tree.
- [Run 42489804673](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42489804673): 37 unit tests, zero failures/errors/skips, and all fifteen native suites passed on Android 11/API 30 and Android 16/API 36 against the non-debuggable release.
- API 30 passed on attempt 1. API 36 passed on an unchanged attempt-2 rerun after the bundled emulator WebView aborted in Maps with `partition_alloc_support.cc` / `Chrome_IOThread` dangling-pointer detection. The final run passed Maps and the complete suite; checks were not skipped or weakened.
- `EightInteractionSmoke` injects actual pointer events over a sentinel desktop target and tests blank bodies, borders, drag ownership, child buttons, scrolling, caption movement, corner resizing and uncovered Desktop. Start, PC settings and immersive blank-area ownership are exercised independently.
- Nota checks launch a specific installed fixture on Send, require its actual alias identity and foreground presentation before Back, verify distinct same-package choices and icons, no replay on return, editing clears choices, unknown-app search, imported/private/generated-text boundaries, writing-origin restoration/backup, exclusion from generic snapshots and compact command controls in both orientations.
- API 30 produced 121 captures and API 36 produced 122. The five new settled native View renders were visually reviewed on both platforms. The existing location, camera-inset, connected-app, shell/profile/defaults, Start-drag, Control Panel/Back, retained-session/snap, research utility, browser, Explorer, photo and media scenarios remain included.
- 77,325 tile placements and 308,182 supported snap combinations passed.

The new interaction captures are rendered from the settled native View hierarchy. Initial compositor screenshots were black on API 36 or captured stale/rotating frames on API 30; the capture helper was corrected before the final visual review. The pointer and external-app checks use actual Android input and tasks. The ChatGPT/Audible/duplicate-name launch targets are isolated fixture aliases, not the real third-party apps. No email, cloud request or model download is triggered by the new app-command tests. Physical Pixel 10/Android 17 testing was not performed here.

## Delivery

- `Nth-Desktop-Eight-0.7.6.apk`, version code 16, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36; non-debuggable.
- APK SHA-256: `c8d86a0560a5ea0c23a49c0698f99ea6508ddd2a490af628cf846b3581ee57c7`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained identity as delivered 0.6.1 through 0.7.5; install over 0.7.5 without uninstalling to retain layouts and local data.
- All 526 non-signature APK ZIP entries match the passing API 36 release artifact byte for byte. Release artifact archive SHA-256: `8d930c875571f06d129c0a4b447a881795dd1a72edca3fbe5103d9692755197b`. Signature Schemes v2/v3 and 16 KB alignment verified; no native libraries.
- Download ZIP SHA-256: `45633ef4660bf1fe45dfb7c129595f12e30f9b7e52d94ebbe4cc3cca2ed1a113`. CRC and exact contained-file byte comparisons passed. It contains only the signed APK, install notes and public build verification; no private key or test/fixture APK.

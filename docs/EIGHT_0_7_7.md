# Nth Desktop Eight 0.7.7 — Nota summaries and 8.1 taskbar spacing

The owner screenshots `224481.png`, `224482.png` and `224483.png` request concise guidance for each Nota task in place of the fixed launch-app sentence, and separation between the 8.1 Start icon and app buttons.

## Updated behavior

The fixed “Type open Audible…” helper is removed. A task summary now occupies that position and changes with the task dropdown, writing shortcuts, New chat, New command and restored selection. Imported passages keep a separate source label below the summary. Selecting a task does not alter the composer, reply or command eligibility.

| Task | Summary |
| --- | --- |
| Ask or write | Ask questions, write new content, or give a simple app command. |
| Brainstorm | Explore ideas, suggestions and new directions for a topic. |
| Proofread | Correct spelling, grammar and punctuation while keeping your wording. |
| Polish | Improve clarity, tone and flow while keeping your meaning. |
| Summarize | Condense a passage into its key points and important details. |
| Apps & settings | Open apps and settings, switch apps, or search the web and linked files. |

The 8.1 Start button has an 8dp trailing margin before the app strip. Its existing touch target is retained, and the app strip continues to scroll within its available space. The margin is specific to the 8.1 profile; original Eight uses its existing spacing. Profile recreation and taskbar peeking reuse the same layout.

## Validation

- [PR #13](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/13); tested source `6ce3838d8e9caaf6e931be40ccfb85f4cceeb355`, tree `0f6c3f222e7b939ece89c9f8b4f3d00282eb0c07`, based on `e5ef356b5512a24c4ef2c5b48da663661d1606ac`. Merged as `09f9e17dfcc71148e3d93b2813bec08e116f6542` with the exact tested tree.
- [Run 42493196608](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42493196608): 37 existing unit tests, zero failures/errors/skips, and all fifteen native suites passed on Android 11/API 30 and Android 16/API 36 against the non-debuggable release. Final source passed both jobs on their first attempt.
- The existing interaction scenario collects six additional settled native View captures, one for each task. The six descriptions and compact landscape layout were visually reviewed on both platforms. Existing original Eight and 8.1 desktop captures were compared for Start/app-strip spacing. API 30 produced 127 captures and API 36 produced 128. No unit tests were added solely to duplicate the cosmetic descriptions or margin value.
- Existing app-launch/no-replay, imported/private/generated-text boundaries, window touch ownership, shell switching/defaults, Control Panel/Back, location/insets, connected apps, gestures, retained sessions/snap, browser, Explorer, photo and media scenarios remain included.
- 77,325 tile placements and 308,182 supported snap combinations passed.

Physical Pixel 10/Android 17 testing was not performed. The six new task captures are rendered from settled native Views; they verify presentation without sending writing requests to an AI service.

## Delivery

- `Nth-Desktop-Eight-0.7.7.apk`, version code 17, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36; non-debuggable.
- APK SHA-256: `95cb570c015f2a2da6b4aeadfeeae5cfff19d921dc939bd010d2fb28dfc6507a`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained identity as delivered 0.6.1 through 0.7.6. Install over 0.7.6 without uninstalling to retain local data and layouts.
- All 526 non-signature APK ZIP entries match the passing API 36 release artifact byte for byte. Release artifact archive SHA-256: `34ca9a5b1045bdf9c11c8b08529cb64660b1f68d265d585fa236be7f20ec9974`. Signature v2/v3 and 16 KB alignment verified; no native libraries.
- Download ZIP SHA-256: `6030533df52782ae946abf8e50882167d3b8349358787c757c5214ba694e0c81`. CRC and exact contained-file byte comparisons passed. It includes the signed APK, install notes and public build verification; no private key or test/fixture APK.

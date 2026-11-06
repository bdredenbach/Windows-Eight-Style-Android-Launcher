# Eight 0.1.0 validation and delivery

Validated 6 November 2026.

## Device and engine results

[Final Android 11 / Android 16 regression](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42286234585) — **success**, both OS jobs.

Native-tested implementation: `ecea43b91dab66cc818e489476a2754f647609e2`.

Each OS job builds the app, test APK and fixtures, passes the 10 shared unit tests, checks the packaged Eight identities and runners, and runs these four native scenarios:

| Scenario | Covered behavior | API 30 | API 36 |
|---|---|---|---|
| EightSmoke | Start/Desktop continuity, independent profile layouts, tile selection, real search, five Charms, contextual Settings, backup isolation, both orientations, activity recreation, Home and keyboard routing | Pass | Pass |
| BrowserSmoke | Real WebView page state, JavaScript/storage, navigation, tabs, TLS rejection, downloads, chooser, video rotation/fullscreen, private-data isolation and recreation | Pass | Pass |
| ExplorerSmoke | Actual SAF providers, file operations/cancellation/verification, menus, navigation and grants to a separate app UID | Pass | Pass |
| PhotoSmoke | Real image pixels/EXIF/large decode, viewer state, source preservation, slideshow, taskbar restoration and revoked grants | Pass | Pass |

The packer property check passed **77,325 tile placements** for bounds, non-overlap, repeatability and profile-specific sizes. The earlier full run at `3e088393f9a7717c43358a273679a924ba87d45c` also passed both OS jobs.

## Visual evidence

The final run retains portrait/landscape Start, Desktop, profile and PC settings images. API 30 produces normal device captures. The API 36 test image's compositor captures were black, so EightSmoke detects that condition and records explicitly named `-view.png` native View renders, with the capture method in `eight-capture.txt`. These are actual laid-out native controls, not generated mockups. API 36 browser/photo runtime checks do not constitute a visual comparison of their blank compositor images.

Both orientations were visually reviewed. The remaining immersive surfaces and period artwork work are documented in EIGHT_0_1.md and the README.

## Delivered package

Packaged source: `145d6d9d5eae922a4ee004e34e24fad1eee592da`. It differs from the native-tested implementation only in product wording, the Steps export filename and the feed HTTP User-Agent. The final app was rebuilt locally; all 10 unit tests and packaged identity checks passed after those wording corrections. App behavior did not change after the native run.

- App: Nth Desktop Eight 0.1.0, versionCode 1.
- Package: `com.nthexperience.desktop.eight`.
- Android: min SDK 26; target/compile SDK 36.
- Delivered APK SHA-256: `2705423deb4241ec386000a738631cefcff6765a93d5fdc3c8f77c9e90dcb598`.
- Certificate SHA-256: `3a7ab565951c7a62fd785c60df3a1f3975ec8447d1307b46e77293a69017c72d`.
- APK v2 and v3 signature verification passed. The ZIP's APK bytes match the standalone APK.
- Dedicated signing identity backed up privately; see EIGHT_SIGNING.md for future upgrades. Private signing files are not in git or the public download ZIP.

This is the first shell preview. These results do not claim that the unfinished immersive hosts, immersive snap or full 8.1-era presentation are implemented.

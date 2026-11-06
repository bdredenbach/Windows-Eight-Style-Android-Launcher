# Eight 0.2.0 validation and delivery

Validated 6 November 2026.

## Device and engine results

Final regression: https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42292395488

Native-tested source: `13fc9f0498e6a66225112268e9a120551841304a`.

**Success: all six native scenarios passed on both Android 11 and Android 16.**

Each OS job builds the app, test APK and fixtures, runs the 10 shared unit tests, checks the packaged Eight identities and six runner declarations, and exercises these native scenarios:

| Scenario | Covered behavior | API 30 | API 36 |
|---|---|---|---|
| EightSmoke | Start/Desktop continuity, independent profiles and layouts, selection, search, Charms, settings, backups, orientations, recreation, Home and keyboard routing | Pass | Pass |
| EightImmersiveSmoke | All 17 icon mappings and distinct empty/full bins; same live BrowserPanel/WebView and draft across presentations; actual HTTP history and Back; Nota draft/assistant continuity; left switcher; classic 320dp and flexible 8.1 snap; narrow fallback; 8.1 settings/Update; contextual volume/brightness; focused app commands; snapped-pair recreation and independent close | Pass | Pass |
| BrowserSmoke | Actual WebView pages and JavaScript/storage, navigation, tabs, TLS rejection, downloads, chooser, video rotation/fullscreen, private isolation and recreation | Pass | Pass |
| ExplorerSmoke | Real SAF providers, file operations/cancellation/verification, menus, navigation and grants across app UIDs | Pass | Pass |
| PhotoSmoke | Actual pixels/EXIF/large decode, viewer state, preserved sources, slideshow, taskbar restoration and revoked grants | Pass | Pass |
| MediaSmoke | Actual audio/video decoding and playback, queues, rotation, fullscreen, state, and transfer of the same live player/engine into immersive presentation and back | Pass | Pass |

Independent policy checks cover **77,325 tile placements** and **59,192 snap combinations**. They assert bounds, non-overlap, repeatability, profile-specific formats, minimum widths, classic compact sizing, narrow fallback and exclusion of arbitrary external apps.

Browser navigation uses real local HTTP fixture pages and waits for a native committed Back entry. Wide-device checks exercise a viewport above the actual snap policy minimum; the narrow fallback is asserted separately.

## Visual evidence and assets

The workflow retains actual native Start, Desktop, PC settings, immersive browser and both snap layouts. API 30 provides compositor screenshots. API 36 AOSP ATD can return black compositor images; the Eight runners detect that and write explicitly named `-view.png` renders of the laid-out native View tree. Browser and photo behavioral checks on API 36 do not establish a visual comparison of blank compositor captures.

The owner's 17 supplied 256px ICO frames were decoded to lossless RGBA PNG. Their original/converted checksums and provenance are recorded in EIGHT_ICON_PROVENANCE.json. The assets are used by desktop shortcuts, Explorer/navigation, captions and related destinations. Unsupported kinds retain Nth fallbacks.

## Delivered package

The locally built application source matches the native-tested source. Delivery documentation is added afterward without changing application code. All 10 local unit tests and the packaged identity/runner check pass.

- App: Nth Desktop Eight 0.2.0, versionCode 2.
- Package: `com.nthexperience.desktop.eight`.
- Android: minimum SDK 26; target/compile SDK 36.
- APK SHA-256: `7b9463448b4fcb160c8d0b3d291b53425c34be9cd29df553f0a1d80d2512a25c`.
- Download ZIP SHA-256: `b818382da1fbb7c77f84b7382bd5b131a43af8af4b9ba6823ff4f7740e6b39b6`.
- Certificate SHA-256: `3a7ab565951c7a62fd785c60df3a1f3975ec8447d1307b46e77293a69017c72d`.
- APK v2/v3 verification passes. ZIP APK bytes equal the standalone APK.
- The signing identity is unchanged from 0.1.0; its private backup remains private and is excluded from source and downloads.

Immersive hosting and snap apply only to supported launcher-owned views. External Android apps remain ordinary Android tasks. Wide layouts are required for usable snapped pairs. The 8.1 Update controls are optional; Aero remains an independent desktop finish.

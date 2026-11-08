# Nth Desktop Eight 0.7.3 — settings sidebar colors

Desktop settings now uses the previous light grey property-sheet color (`#f0f0f0`) for its navigation sidebar and white for its main content. The sidebar background is painted on the full-height scroll container, reaching the bottom on both short and long pages. All Taskbar and Navigation tabs use white, and traditional Control Panel uses the same grey navigation surface when its sidebar is visible.

The change applies through the shared desktop settings layout, including Personalization, Desktop Icon Settings, Desktop Background, Color and Appearance, Sound, Display, Taskbar and Navigation, and the other supported desktop settings routes. Selection highlights and independent scrolling remain. Immersive PC settings already provides full-height profile-specific rails and white detail areas; those original Eight/8.1 presentations remain intact. Settings actions and Apply/OK/Cancel behavior are unchanged.

## Validation

- [PR #9](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/9); tested source `014efb4b7b323523b945d4846e6df61426043c5f`, based on main `7755b5ea6c46c486091cb05f50d2f8622a7abdcf`. Merged as `024a3d8159124b60e8564b4633d6c2165e33437f`, with the same tested tree.
- [Successful run 42468507194](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42468507194): 33 unit tests, zero failures/errors/skips, and all fourteen native suites on Android 11/API 30 and Android 16/API 36 against the non-debuggable release.
- Existing route/control checks cover all 21 desktop settings routes, both immersive settings profiles and Taskbar Apply. Additional captures cover short Display/Sound pages and traditional Control Panel. Personalization, Display, Sound, Taskbar properties and landscape Colors/Control Panel were visually reviewed on both Android versions.
- API 30 produced 104 captures; API 36 produced 105. View-rendered fallbacks are used on API 36. Physical Pixel 10/Android 17 testing is outside this run.
- 77,325 tile placements and 308,182 snap combinations passed. Navigation links, selection and headings maintain at least 6.27:1 contrast on their new grey/white backgrounds.

## Delivery

- `Nth-Desktop-Eight-0.7.3.apk`, version code 13, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36, non-debuggable.
- APK SHA-256: `8e9c61b84291a8d527d62e710095495b16b4f937f6b35181467c7372f79944cc`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained identity as delivered 0.6.1 through 0.7.2; install over 0.7.2 without uninstalling to retain layouts and local data.
- All 526 non-signature APK ZIP entries match the tested API 36 artifact byte for byte. Source artifact ZIP SHA-256: `5f55936aaba3e7c480db11a0247db35c82c4960ddd841a953947dbeb88c214f9`. Signature Schemes v2/v3 and 16 KB alignment verified; no native libraries.
- Download ZIP SHA-256: `7bcb2e9cf7db013345a2c711f08030bf66a809c4f30bbb3a66575a1ad7bad8cb`. CRC and contained-file byte comparisons passed. It includes only the signed APK, install notes and public metadata; no private key or test/fixture APK.

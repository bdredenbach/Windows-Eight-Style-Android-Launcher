# Eight 0.3.0 validation and delivery

Validated 6 November 2026. **All seven native scenarios passed on both Android 11 and Android 16.**

Native-tested source: `cfdb0a9563b2526e9d39d1a983e6ffa2dbb7921e`.

[Android 11 / Android 16 regression](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42299745904).

| Native scenario | Main coverage | API 30 | API 36 |
|---|---|---|---|
| EightSmoke | Start/Desktop continuity, isolated profile layouts, tiles, actual search, Charms, settings, backup, rotation, recreation and Home | Pass | Pass |
| EightAuditSmoke | All 21 legacy desktop settings routes, live Eight guide/name/icon, period desktop controls, Task Manager, Command Prompt/management, same calculator result, News, Calendar, Photos/Weather/Media presentations, both PC settings hierarchies, Computer/This PC refresh, opaque cutout padding, landscape search layout/focus, Update header controls and non-overlapping command handle | Pass | Pass |
| EightImmersiveSmoke | Same live WebView/draft, actual committed HTTP history/Back, Nota draft/assistant continuity, app switcher, compact/flexible snap, narrow fallback, device controls and independent closing/recreation | Pass | Pass |
| BrowserSmoke | Real pages, storage, history/tabs, TLS rejection, downloads, upload chooser, video rotation/fullscreen, private isolation and recreation | Pass | Pass |
| ExplorerSmoke | Actual providers, file operations/cancellation/verification, menus, navigation, linked folders and access across UIDs | Pass | Pass |
| PhotoSmoke | Actual decoded pixels/EXIF, zoom, large/corrupt images, slideshow, preserved sources, recreated state and revoked grants | Pass | Pass |
| MediaSmoke | Real multi-format audio/video, playback/seek/queues, rendered video, shared views and transfer of the same playing engine/view between Desktop and immersive | Pass | Pass |

## Build and policy checks

- 13 local unit tests pass: shared calculator/AI/accent tests and new Eight settings/command routing assertions.
- Packaged identity, isolated providers, Home intent and all seven native runner registrations pass.
- The new surface-construction check confirms Eight does not construct Ten Settings, Task View, Action Center sidebar, News flyout or calculator. It confirms the live guide and removal of the Seven screenshot asset.
- 77,325 tile placements and 76,944 supported snap combinations pass bounds/profile/minimum-width checks. News is included in the owned-app policy set.

## Native visual evidence

The audit captures actual portrait/landscape Control Panel, taskbar properties, Calculator, News, Media, Task Manager, PC settings in both profiles, Start, Usage Access guide, colors, right-hand Search and 8.1 Update controls. Native screenshots are retained with the workflow evidence.

API 30 provides compositor captures. API 36 AOSP ATD may return black compositor screenshots; the Eight runners detect that and save explicitly named `-view.png` renders of the laid-out native View tree. The fallback is not a generated mockup or a capture of Android system settings. Blank browser/photo compositor captures are not claimed as visual validation of those renderers; their behavioral/pixel checks are separate.

The initial broad audit found a real stale Explorer label after a shell change. The fix refreshes profile-dependent navigation/captions. Landscape search was subsequently corrected to keep focus when reparented and to scroll all scopes. Screenshot review also moved the command handle into reserved bottom space so it cannot cover app header controls. The final regression includes these changes.

## Delivered package

Locally packaged application source matches the native-tested source. Final documentation does not change application code.

- Nth Desktop Eight 0.3.0, versionCode 3.
- Package `com.nthexperience.desktop.eight`; minimum SDK 26; compile/target SDK 36.
- APK SHA-256: `089c820357b17cbc4d1400f70aa60dcb00632d80f7f277acba15927502b743cd`.
- Download ZIP SHA-256: `55503ae7fbc7642f8803d7ae8aca4655457ef2a195fec6228f9b62d942e6c71f`.
- Signing certificate SHA-256: `3a7ab565951c7a62fd785c60df3a1f3975ec8447d1307b46e77293a69017c72d`.
- Same signing identity as 0.1.0 and 0.2.0. APK v2/v3 verification passes, and the ZIP contains byte-identical APK content.
- Private signing materials remain outside source and downloads.

The audit documents Android adaptations and unsupported Windows services in EIGHT_0_3_AUDIT.md. These results establish tested launcher behavior, not an implementation of Windows itself or arbitrary external-app embedding.

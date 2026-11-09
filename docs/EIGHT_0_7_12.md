# Nth Desktop Eight 0.7.12 — Bottom-row taskbar controls

The expanded taskbar centered its fixed 48 dp controls across the full 96 dp bar. Start, the notification area, clock and Show desktop therefore floated between the two app rows.

The taskbar now aligns those controls to its bottom edge. Each occupies the normal 48 dp bottom row, including the clock, whose text remains centered in that row. In collapsed mode the clock text keeps its previous visual center. App buttons retain their existing two-row packing, order and scrolling. Desktop icon positions and saved layouts do not need a migration or reset.

The existing native taskbar stability suite now observes fixed-control screen positions, visible tray-icon positions, control height and alignment on every watched pre-draw frame. It covers both Eight shells, portrait/landscape and repeated expansion/collapse through the hold menu and Control Panel Apply, alongside the existing desktop icon and app-order regressions.

## Validation and delivery

- [PR #18](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/18), tested source `fbac19d86225a845d75c779fa0ad73a0c919f2a4`, tree `ea0c57e561b71f11468d0dc5501e194a19a13fcb`; merge `db0cb445585138de4e2b1f7e411c92c4407a5e16` retains the tested source tree.
- [Run 42636413468](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42636413468): 41 unit tests and all eighteen native suites passed on Android 11/API 30 and Android 16/API 36, with no unit failures, errors or skips.
- Seven taskbar scenarios per OS watched 158 API 30 and 147 API 36 pre-draw frames. Start, the notification area, clock and Show desktop remained in the bottom 48 dp row and retained their screen positions; visible tray icons stayed fixed too. Existing desktop icon geometry, saved coordinates, session app order, manual drag order and changed pin checks passed.
- Both shells and portrait/landscape, hold-menu and Control Panel Apply toggles, automatic/free placement and scrolling were covered. Existing Start tiles, backup/Undo, Task Manager, shell/Nota, location/inset, connected-app, gesture, browser, Explorer, photo and media checks passed. 77,325 tile placements and 308,182 snap combinations passed.
- Native captures: 138 on API 30, 139 on API 36. Taskbar captures were visually reviewed. Physical Pixel 10 / Android 17 testing was not performed.
- Delivered `Nth-Desktop-Eight-0.7.12.apk`, version code 22, package `com.nthexperience.desktop.eight`, minimum SDK 26, target SDK 36; non-debuggable. Install over the existing Eight app.
- APK SHA-256: `145890d42586eec9b0b75f3fa8e372a47895b6ddf920889790244716bd82fc72`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. The retained signing identity, v2/v3 signatures and 16 KB ZIP alignment are verified. All 526 payload entries excluding signature records match the passing API 36 artifact. Artifact archive SHA-256: `e9b45c24dd5ecb6ae3185604100b6dcba8a8af5ac2f932d344c5aca01b442b21`.

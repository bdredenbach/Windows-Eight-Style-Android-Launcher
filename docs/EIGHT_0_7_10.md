# Nth Desktop Eight 0.7.10 — Stable icons with an expanded taskbar

The supplied `224556.mp4` shows restored desktop folders moving when the taskbar expands and again when it collapses. In particular, a bottom-row folder can fall into an unrelated top slot.

The desktop calculated custom row heights from the current workspace height. A two-row taskbar shortened that workspace, changing cell dimensions. Saved coordinates were then snapped to the new cell sizes; resulting overlaps sent icons through the free-slot fallback. Automatic unsaved layouts could also repack into different columns.

Desktop layout now uses the normal one-row work area as its stable reference. Expansion reduces only the visible scroll area. Existing saved positions, grid choices and icon sizes are preserved without a data migration or coordinate rewrite. The desktop can scroll to reach content covered by the taller taskbar.

The native regression uses 21 synthetic desktop icons, the recording's 5×9 portrait grid and 80% icon size, both Eight shells, portrait/landscape, automatic unsaved layout, free placement and a scrolled sheet. It observes actual pre-draw screen positions, cell dimensions and icon dimensions throughout repeated menu and Control Panel Apply toggles, and verifies that saved coordinates are unchanged. Personal folders, app lists, screenshots and recording bytes were not committed.

A second transition in the recording affected taskbar buttons themselves: an unpinned Control Panel window was inserted before existing Android buttons. In a two-row bar, that shifted existing icons between rows, then reversed the shift on close. The launcher now keeps a session order and appends newly appearing tasks. Explicit saved drag order remains authoritative, and pin edits reset the normal pinned order. This session list stays in memory and does not persist app usage history. Native checks use five real installed fixture shortcuts with no pins and verify their rows stay fixed through Control Panel open/close, expansion/collapse, manual order and changed pins.

## Validation and delivery

- [PR #16](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/16), tested source `cb7c0b7e54254aad4a6a0ab486cb31580a390209`, tree `3d03b0d60349a73d3166f38b58ff5582c390f27a`. Merged as `9ee49663e9e8208f962bc09f6ea5cad8c14bf95c` with the same tested source tree.
- [Run 42620814243](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42620814243): 37 unit tests and all seventeen native suites passed on both Android 11/API 30 and Android 16/API 36. Final source passed both jobs on their first attempt with zero failed, errored or skipped tests.
- Seven desktop scenarios on each platform cover both shells, portrait/landscape, the supplied grid/size settings, automatic placement, free placement and scrolling. Across repeated menu and Control Panel Apply toggles, all 21 icons retained actual screen positions, cell dimensions and icon dimensions in 158 API 30 frames and 146 API 36 frames; saved coordinates were unchanged.
- The real installed Android shortcut fixture verifies stable unpinned button order and row positions through Control Panel open/close, expansion/collapse, and continued support for explicit manual order and changed pins.
- Existing shared backup/Undo, Task Manager, shell, Nota, location/insets, connected apps, gesture, browser, Explorer, photo and media suites passed. 77,325 tile placements and 308,182 supported snap combinations passed. API 30 produced 134 captures; API 36 produced 135. New stable desktop/taskbar captures were visually reviewed; the new suite renders settled native Views to avoid blank headless screenshot surfaces.
- Delivered `Nth-Desktop-Eight-0.7.10.apk`, version code 20, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36; non-debuggable. Install over the existing Eight app. No layout reset or backup reimport is required.
- APK SHA-256: `4a9be8e21009bb3bb537ea2cb019f0339f6c8feebb68c12f5547ea2a7e88b267`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. The existing Eight update identity is retained. Signatures v2/v3 and 16 KB ZIP alignment are verified. All 526 payload ZIP entries, excluding signature records, match the passing API 36 release artifact byte for byte. Artifact archive SHA-256: `048c3a54f986d2155a1a1bc4183a0e935ce8f75665228e264787573c06434efd`.

Physical Pixel 10 / Android 17 testing was not performed.

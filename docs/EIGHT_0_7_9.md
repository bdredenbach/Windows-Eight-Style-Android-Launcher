# Nth Desktop Eight 0.7.9 — Shared backups and desktop checklist

## Shared backups

The importer accepts version 1 `nth-desktop-backup` files and the earlier `nth-desktop-eight-backup` files. Exports now use the common format. Eight-specific profile data remains validated when present; partial Eight layouts are rejected. A shared backup from another edition supplies the common folders, shortcuts, notes, positions and preferences while retaining the destination's Eight / 8.1 profile, Start layouts and Eight app collections. Missing Eight layout data is prepared without touching live preferences.

The supplied November 2 backup was checked locally with the production parser and unchanged shared values: 20 app folders and 21 desktop shortcuts. Its contents were not added to the public repository. Native tests use synthetic fixture content with the same folder/icon counts. Preview remains read only, validation finishes before applying, the prior desktop is saved for atomic Undo, and invalid files leave live preferences unchanged. Initialization markers prevent restored display choices from being reset on the next launch.

## Expanded taskbar

The taskbar context menu has **Expand taskbar**. Control Panel's **Taskbar and Navigation → Taskbar** has **Expand taskbar to two rows**, with the existing Apply / OK / Cancel buffer. The embedded legacy Taskbar settings expose the same saved switch.

Expanded taskbars use two actual 48dp button rows, support scrolling and task reordering in both dimensions, and fit static buttons by their real column widths. Normal single-row behavior remains the default. Rotation, mouse peek, corner handles and the usable desktop area respect the selected height. The preference is included in backups.

## Windows 8 Task Manager

Microsoft's [Windows 8 Task Manager In-Depth](https://blogs.windows.com/windowsexperience/2013/06/06/windows-8-task-manager-in-depth/) describes compact/detailed views, the seven-tab arrangement and performance metric previews beside a selected detailed graph. Eight follows that structure with touch adaptations.

The compact view lists owned app sessions and recently opened Android entries. More details reveals Processes, Performance, App history, Startup, Users, Details and Services. Performance offers measured Nth CPU, device RAM, Java heap and device receive/send rates, with preview graphs, metric selection and a detailed graph. Wide views put the previews on the left; narrow views use a horizontal selector. Older detailed sessions retain their view after an update, and the layout responds to rotation.

Startup changes supported Eight startup preferences. Users identifies the current profile. Details retains real Nth-owned process information. Services explains Android's service boundary and provides the working Android settings route. No simulated Windows services, other users or invented per-app resource figures are added. Pausing, update speed, reduced motion and background/hidden sampling controls remain supported.

## Validation and delivery

- [PR #15](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/15), tested source `759784895c22da774a7e158e762e54dd4dc89abf`, tree `7df5c6714f8a152385c8239ab54170bc87d6f478`. Merged as `68d2194989983f626a321054e1f781bd63e6cb1a` with the exact tested tree.
- [Run 42561593455](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42561593455): 37 unit tests and all sixteen native suites passed on both Android 11/API 30 and Android 16/API 36, with zero failures/errors/skips. Final source passed both jobs on the first attempt. An earlier source run hit the same bundled emulator WebView / Chrome_IOThread abort previously recorded for 0.7.8; final Maps and browser suites passed.
- Native compatibility checks cover synthetic 20-folder / 21-shortcut restore and contents, preview without writes, malformed input and incomplete Eight rejection, shared reexport, legacy Eight format, retained 8.1 shell, atomic Undo, both taskbar controls, labeled static fitting, two real rows, mouse peek, portrait/landscape, compact mode and all seven Task Manager tabs, older detailed session migration, selectable real performance graphs and inactive sampling stop.
- All existing shell, Nota, touch ownership, Maps, inset, connected app, gesture, retained session, browser, Explorer, photo and media scenarios passed. 77,325 tile placements and 308,182 supported snap combinations passed. API 30 produced 131 captures; API 36 produced 132. New taskbar and Task Manager views were visually reviewed. API 36 uses native View rendering when its headless screenshot surface is blank.
- Delivered `Nth-Desktop-Eight-0.7.9.apk`, version code 19, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36, non-debuggable. Install over 0.7.8 to retain local data.
- APK SHA-256: `f7ec63349d1b7df1d2a4f68ab5a11e8ecb6bc869d627f269764a661a7742ab28`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. The retained identity matches previous Eight deliveries. Signature v2/v3 and 16 KB ZIP alignment verified. All 486 non-signature ZIP entries are byte-identical to the passing API 36 release artifact; its archive SHA-256 is `245eccf64fd37bd3a9dff6887725bf8ef6517eea6bcdaebd4c318fe22878c5c5`.

Physical Pixel 10 / Android 17 testing was not performed. Backups keep file references rather than document/media contents; Android permissions can require relinking in the target app.

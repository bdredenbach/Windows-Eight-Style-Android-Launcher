# Desktop consistency and portable restore

## Task Manager

Retains the existing Windows 8 compact/details Task Manager and its Processes, Performance, App history, Startup, Users, Details and Services navigation. The shared regression checks exercise its details toggle and tabs.

## Desktop caching

Each launcher keeps a rebuildable, atomic cache of app identifiers, labels and bounded icon images in its own Android cache directory. A worker displays the cached inventory before refreshing the package catalog. A failed or transiently empty scan retains the last working inventory instead of clearing the desktop. An in-memory catalog is reused for 60 seconds; package changes, locale changes and explicit Refresh invalidate it immediately. Configuration changes invalidate incompatible cached labels/icons. Android may evict the cache, which is rebuilt on the next successful scan. The cache is excluded from portable desktop backups; existing saved shortcut and layout preferences remain the source of truth.

## Welcome and restore

Every Welcome Center now says **Restore a desktop**, and the backup panel places that action first. Saving a backup remains available.

New backups retain the version 1 `nth-desktop-backup` format and add source-variant and wallpaper-name metadata. All five launchers also accept the original `nth-desktop-eight-backup` format. Imports from another variant or older backups without source metadata filter unsupported built-in shortcuts from lists, folders, aliases and icon positions; apply destination list limits; and translate stock backgrounds by name, including slideshows and saved themes. An unavailable background is skipped. Older backups without background names use only the common stock range to avoid treating another variant's stock image as the same picture.

Each destination still validates the compatible settings. Unsupported optional settings are omitted and named in the preview. Malformed JSON, unsupported schema versions, missing core data, invalid field types and the existing size/nesting limits still reject the import before preferences change. New same-variant backups retain strict validation. A partial restore preserves destination settings for skipped fields and keeps the existing undo transaction. On launchers with virtual desktops, importing an older primary-desktop layout updates its visible shortcut bank while preserving the other destination banks; incoming compatible banks retain their positions after unsupported shortcuts are filtered.

File and photo links still need Android access on the receiving app/device. A backup does not transfer file contents or grants. Task Manager shows actual Nth/device data exposed by Android; other apps' privileged process controls remain Android's responsibility.

## Validation

Run with Java 17 and Android SDK 36:

```sh
./gradlew :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest
```

`DesktopConsistencyTest` covers five source fixtures in each receiving launcher (25 source/destination combinations across the repositories), legacy Quick Launch/background handling, the historical Eight format, strict same-variant rejection, malformed/incomplete files, partial restore and undo, stock-background names inside themes, cold icon-cache reload, failed scans, damaged caches and generation-specific Task Manager navigation. Eight, Ten and Eleven also exercise older-backup membership migration and compatible imported desktop banks/positions.

The local unit tests and APK/instrumentation-APK builds are the validation for this change. Android device/emulator instrumentation suites are compiled but have not been executed in this workspace; existing CI runs the device suites. These are source changes for review, not a newly signed production release.

The corrected companion baselines are Vista 0.26.1 and Seven 0.7.1. Eight 0.7.12, Ten 0.26.0 and Eleven 0.8.2 match the user’s existing version numbers. First-scan catalog failures also skip external-task refresh instead of passing it an unavailable catalog.

## Extended taskbars and rendering caches

The desktop grid uses the actual work area above the taskbar. It measures each label before fitting the icon, keeps complete rows visible, and starts overflow rows outside the first viewport so they can be scrolled into view. Expanded taskbars temporarily fit saved positions without writing those coordinates back to preferences. The normal taskbar layout returns when the taskbar collapses. Larger system text participates in row sizing.

All five variants now share static built-in artwork in a bounded 4 MiB bitmap cache keyed by icon kind, pixel size, padding, density and UI mode. Existing drawing commands remain unchanged; live gadget faces are separate. Folder names, shortcut aliases and normalized preference lists reuse parsed snapshots until their raw saved data changes. Mutable folder/list return values remain independent so edits cannot accidentally corrupt the cache. Desktop placement plans are reused until dimensions, orientation, cutouts, shortcut membership or saved positions change. An unchanged in-memory app catalog no longer recreates the desktop and hidden Start contents on every Home/resume.

`LauncherRenderingTest` checks expanded taskbars with automatic/requested rows, two-line labels, larger system text, whole overflow rows, temporary saved-position fitting, geometry invalidation, mutable model safety, static icon reuse and bounded cache size. Phone/emulator timing and visual checks remain pending; the APKs are development-signed test builds.

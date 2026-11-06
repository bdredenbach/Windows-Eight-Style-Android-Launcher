# Nth Desktop Eight 0.1.0 — first shell slice

## Decisions

One APK and repository contain selectable Windows 8 and Windows 8.1 profiles. The default is original Eight. Aero is an independent desktop appearance option. Both profile layouts survive switching and are included in validated Eight backups. No other launcher data is imported automatically.

The new Android identity is `com.nthexperience.desktop.eight`; the Java namespace remains `com.nthexperience.desktop`. Signing material must remain outside this repository.

## Ownership and restoration

`EightStartScreen` owns START, ALL_APPS, SEARCH and OVERVIEW. The desktop window manager owns desktop tools. `EightPCSettings` is a separate full-screen surface. `EightCharms` owns a single transient overlay and restores its prior focus. Start and PC settings are siblings of the desktop shell, so Start covers the taskbar rather than inheriting Ten's menu bounds.

The Desktop tile hides Start without minimizing or recreating desktop windows. Home enters Start; explicitly requested Android text/tool intents retain their destination. Profile changes rebuild taskbar presentation without reconstructing windows. Activity restoration retains Start state, desktop contents and PC settings page. Ordinary pause preserves the current surface.

## New files

- `EightProfile`: profile capabilities, independent of appearance.
- `EightDefaults`: isolated defaults that bypass Ten's initial shell seeding.
- `EightTileLayout`: height-bounded horizontal packing in logical cells.
- `EightStartData`: separately validated profile layouts with stable item identities.
- `EightStartScreen`: tile groups, selection/app commands, Apps, overview and local search.
- `EightBackdrop`: original local pattern and optional wallpaper presentation.
- `EightCharms`: five charms, one overlay and supported contextual routes.
- `EightPCSettings`: functional first-preview settings surface.

`MainActivity` coordinates these surfaces and preserves existing engines. Nota now participates in ordinary taskbar buttons, including detached-window filtering and matching dimensions.

## Validation

Local Android app, fixture and instrumentation builds and the shared unit tests must pass. The standalone packer checks 77,325 tile placements. `CheckEightManifest.py` inspects installed package/provider identities and runner registration. `EightSmoke` exercises Start/Desktop continuity, native tile selection, profile isolation, search, Charms, contextual Settings, backup rejection, both orientations, recreation and Home. Browser/Explorer/Photo instrumentation checks protect reused engines on API 30 and 36.

The research handoff describes the desired finished app. Its historical Ten test results are not Eight results. CI results and any discovered limitations should be recorded separately after completion.

## Next implementation passes

1. Review actual native screenshots against the included gallery; complete Eight desktop assets and chrome.
2. Add explicit lifecycle-aware immersive presentations for selected Nth apps and the left immersive switcher.
3. Add supported compact immersive snap and edge app bars with usable phone fallbacks.
4. Complete coherent 8.1 PC settings/search/browser differences, then the optional 2014 Update behavior.
5. Broaden real-device and shared-engine coverage as these surfaces change.

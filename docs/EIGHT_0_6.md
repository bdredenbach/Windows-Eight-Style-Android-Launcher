# Nth Desktop Eight 0.6.0 - Windows 8.0 fidelity

Implements the owner's Windows 8.0 recording review against 0.5.0. No private recording, screenshot, signing material or downloaded Windows asset is included.

- Light original-Eight PC settings rail and heading, purple selection, gray pointer/focus states, Personalize tabs and grouped General destinations; 8.1 retains its separate layout.
- Pointer-only Charms preview before the committed black rail, responsive horizontal clock/date, original-style vector controls and contextual Start / Tiles / Help links.
- Tiles settings adds real maintenance destinations without removing a previously pinned tool; clearing content suppresses unchanged tile snapshots while retaining app data and layout. Fresh provider data can repopulate them.
- Dedicated black immersive Internet Explorer-style toolbar for 8.0, circular controls and anchored pin menu. Desktop and 8.1 presentations remain separate. Nth Nota stays in Tools. Rendering still uses the installed Android WebView.
- Explicit website Pin to Start with editable labels, retained HTTP(S) destinations, deduplication, drag/reorder support and validated backup. InPrivate pinning asks before saving a reference outside the private session.
- Coordinated cancellable perspective/scale/fade changes between Start, owned apps, settings and Desktop, plus tile hover and press feedback. Reduce motion and disabled Android animators are respected. Transient snapshots are bounded, stay in memory and are omitted when a private browser session exists.

## Verification

The workflow builds and runs unit checks plus native Eight, Gesture, Audit, Immersive, Research, Fidelity, Browser, Explorer, Photo and Media suites on Android 11 / API 30 and Android 16 / API 36. Fidelity covers profile styling, tabs, Charms, admin toggling, non-destructive tile clear, website backup/session reuse, rotation and rapid navigation. Results are recorded after the run; no completed device run is claimed by this initial commit.

## Icon sources for the owner

- [Windows 8 Original Icons - Cozzmy13](https://www.deviantart.com/cozzmy13/art/Windows-8-Original-Icons-Largest-Icon-Pack-407985804): titled original Windows 8 icons; login required. Actual pack contents/build provenance need checking after upload.
- [Windows UI Assets](https://github.com/bartekl1/windows-ui-assets): extracted system assets with an 8.1 folder, useful for desktop artwork; not labeled as an exact 8.0 set.
- [Windows Icon Archive](https://limehawk.github.io/windows-icon-archive/): browsable version/source-DLL views, including 8.1.
- [Microsoft Segoe UI Symbol](https://learn.microsoft.com/en-us/typography/font-list/segoe-ui-symbol): period symbol font information. The current code uses original Nth vector drawings while owner-provided assets are pending.

The inherited 17-icon owner pack remains intact. Additional icons will be matched to 8.0/8.1 references after the owner provides them.

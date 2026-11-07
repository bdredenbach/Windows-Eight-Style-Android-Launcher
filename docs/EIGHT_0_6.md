# Nth Desktop Eight 0.6.0 - Windows 8.0 fidelity

Implements the owner's Windows 8.0 recording review against 0.5.0. No private recording, screenshot, signing material or downloaded Windows asset is included.

- Light original-Eight PC settings rail and heading, purple selection, gray pointer/focus states, Personalize tabs and grouped General destinations; 8.1 retains its separate layout.
- Pointer-only Charms preview before the committed black rail, responsive horizontal clock/date, original-style vector controls and contextual Start / Tiles / Help links.
- Tiles settings adds real maintenance destinations without removing a previously pinned tool; clearing content suppresses unchanged tile snapshots while retaining app data and layout. Fresh provider data can repopulate them.
- Dedicated black immersive Internet Explorer-style toolbar for 8.0, circular controls and anchored pin menu. Desktop and 8.1 presentations remain separate. Nth Nota stays in Tools. Rendering still uses the installed Android WebView.
- Explicit website Pin to Start with editable labels, retained HTTP(S) destinations, deduplication, drag/reorder support and validated backup. InPrivate pinning asks before saving a reference outside the private session.
- Coordinated cancellable perspective/scale/fade changes between Start, owned apps, settings and Desktop, plus tile hover and press feedback. Reduce motion and disabled Android animators are respected. Transient snapshots are bounded, stay in memory and are omitted when a private browser session exists.

## Verification

Both Android 11 / API 30 and Android 16 / API 36 jobs passed for final source head `654822dc2605ed22aaa7c3d70d26e62011830d23` in [workflow run 42403224556](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42403224556). Both jobs compiled the app, unit checks, instrumentation APK, fixture and install probe. All ten native suites passed on both versions: Fidelity, Eight, Gesture, Audit, Immersive, Research, Browser, Explorer, Photo and Media.

Fidelity exercises profile styling, actual address submission and anchored website pinning, tabs, Charms, admin toggling, non-destructive tile clear, website backup/session reuse, rotation and rapid/reentrant navigation. Host checks covered 77,325 tile placements and 203,980 snap combinations. Native contact sheets were reviewed for portrait/landscape settings, browser chrome, Charms and Tiles. These emulator results do not replace an owner check on the Pixel 10.

### Delivery status

Source version 0.6.0 is merged in [PR #2](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/2); the icon follow-up is merged in PR #3. The owner-requested 0.6.1 APK is now delivered as a fresh-install package with a new, privately backed-up signing identity. It cannot update the original 0.5.0 installation. See [the package hashes, installation limits and future signing record](EIGHT_0_6_1_DELIVERY.md).

## Icon sources for the owner

- [Windows 8 Original Icons - Cozzmy13](https://www.deviantart.com/cozzmy13/art/Windows-8-Original-Icons-Largest-Icon-Pack-407985804): titled original Windows 8 icons; login required. Actual pack contents/build provenance need checking after upload.
- [Windows UI Assets](https://github.com/bartekl1/windows-ui-assets): extracted system assets with an 8.1 folder, useful for desktop artwork; not labeled as an exact 8.0 set.
- [Windows Icon Archive](https://limehawk.github.io/windows-icon-archive/): browsable version/source-DLL views, including 8.1.
- [Microsoft Segoe UI Symbol](https://learn.microsoft.com/en-us/typography/font-list/segoe-ui-symbol): period symbol font information. The current code uses original Nth vector drawings while owner-provided assets are pending.

The inherited 17-icon owner pack remains intact. Version 0.6.1 adds 20 selected native 256px icons from the owner-uploaded Cozzmy13 pack. Smaller-only, dithered, legacy and unrelated icons were excluded. The complete selection and source/output hashes are in `EIGHT_0_6_1_ICON_PROVENANCE.json`; the collection title does not establish a particular Windows build. Desktop and Start browser controls now use the supplied Internet Explorer silhouette; Paint and WordPad use their colored desktop artwork. Other monochrome controls retain the scalable Nth drawings.

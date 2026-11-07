# Nth Desktop Eight

A Windows 8-inspired Android launcher by **An Nth Experience**, with a selectable Windows 8.1 shell. Current preview: **0.6.0**. Android 8.0+; targets Android 16.

Package: `com.nthexperience.desktop.eight`. Installs alongside Nth Desktop Ten, Seven and Vista.

See [the 0.4 gesture and app audit](docs/EIGHT_0_4_AUDIT.md) and [device validation](docs/EIGHT_0_4_VALIDATION.md), plus [the earlier whole-surface audit and period references](docs/EIGHT_0_3_AUDIT.md).

## Eight and EightPoint One

- Hold and move a Start tile to drag it. Both screen edges scroll through columns/groups, with a stable destination marker; cancellation preserves the saved layout. Swipe down from Start or All Apps to return to Desktop.
- Weather exposes location setup on first open and visible Places, My location and Refresh actions. Real cached/fetched temperature and condition art appear in Weather and the live Start tile.
- Full-screen horizontal Start, independent saved layouts, All Apps, group overview and a selection command bar. Original Eight supports square/wide tiles; 8.1 adds small/large formats, wallpaper on Start, startup at Desktop and Apps sorting.
- The owner's 17-icon pack supplies Computer, Desktop folders, Documents, Downloads, Favorites, Music, Pictures, Videos, Network, Contacts, Links, UserFolder, Games, Searches and empty/full Recycle Bin artwork. Exact 256px ICO frames are converted losslessly to PNG. See `docs/EIGHT_ICON_PROVENANCE.json`. Other artwork uses original Nth fallbacks.
- Supported Nth tools open from Start in a true immersive host: Notepad, Nota, Calculator, Calendar, Weather, News, Browser, Photos and Media Player. Desktop shortcuts keep their desktop presentation. Open on Desktop transfers the same app body/session, without creating a second browser or player or closing the assistant.
- The left app switcher retains real owned sessions and treats Desktop as one surface. Use its left handle, Windows+Tab or Start's Switch apps command. Browser InPrivate content is excluded from switcher thumbnails. Other Android apps continue using real Android tasks.
- Reveal immersive commands with the bottom command-strip ellipsis, Windows+Z or a short top-edge pull. A deliberate longer pull to the bottom closes the focused app. Commands offer switching, supported sharing, docking to Desktop, snap and close.
- Original Eight uses a fixed 320dp compact companion when both apps remain usable. The 8.1 shell supports a movable divider within each app's minimum width. Narrow screens use full-screen switching; rotating back to a wide viewport restores the retained pair.
- Five Charms provide scoped local search, supported contextual sharing, Start toggle, Android device routes and contextual settings. The browser has bottom immersive address/tab controls, page search and its own settings route; 8.1 optionally keeps the address bar visible.
- PC settings uses separate original Eight and expanded 8.1 category hierarchies. Traditional Control Panel remains a desktop destination.
- Optional 8.1 Update behavior coordinates mouse-revealed close/minimize controls, immersive app buttons in the desktop taskbar, and a taskbar peek from the bottom with a mouse.
- Eight desktop chrome uses opaque accent frames, a red close control and a softly tinted taskbar. Aero is an independent optional desktop finish.
- Session restoration keeps immersive apps, their contents, the snapped pair and the preceding Start/Settings/Desktop surface. Eight-only backups validate both profile layouts and supported appearance settings.

Choose the shell in **Charms → Settings → Change PC settings → Personalize / PC and devices**. The 8.1 Update controls are optional on that page.

## Scope and adaptations

Snap applies to supported launcher-owned app views and requires usable widths. The Windows 8 companion uses density-independent sizing appropriate for Android. Arbitrary Android apps are launched normally and are not embedded into Nth panes. The 8.1 Everywhere view combines real local providers rather than simulating Bing Smart Search. Cloud login/sync, Windows services and Internet Explorer's engine are not implemented; the browser retains the installed Android WebView and its existing privacy/security behavior.

The supplied icons remain attributed to their rights holders. The archive's filenames and artwork are preserved; its file timestamps are not evidence of a particular Windows release. Research screenshots are references rather than application artwork.

## Windows 8.0 fidelity 0.6.0

See [the recording-driven fidelity changes and validation scope](docs/EIGHT_0_6.md) for the light original-Eight settings layout, Charms, immersive IE controls, website tiles and motion.

## Research release 0.5.0

The [research implementation notes](docs/EIGHT_0_5.md) describe the retained live tiles, nested PC settings, desktop property sheets, Explorer/Recycle refinements and working new utilities. The notes distinguish local functionality, Android Clock/device handoffs and the remaining roadmap.

## Build and validation

Use Java 17 and Android SDK 36:

```sh
./gradlew :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :fixture:assembleDebug
```

The main workflow builds for Android 11 and Android 16, runs the Eight surface/profile scenario plus the immersive session/snap scenario the whole-surface audit, and shared Browser, Explorer, Photo and Media scenarios, and uploads APKs and native screenshots. `checks/EightTileLayoutCheck.java` checks 77,325 placements for bounds, overlap, determinism and profile-specific formats. `checks/EightSnapCheck.java` checks 203,980 supported combinations for classic/flexible sizing, per-app minimum widths and narrow fallback. The 16 unit tests cover tile queue/expiry/badge and settings-route invariants alongside the shared calculator, AI lifecycle and accent checks. Historical Ten suites are preserved as source reference and are not asserted to pass against Eight's replacement shell.

The complete visual gallery and illustrated brief are in the owner-supplied handoff ZIP; research text and source attribution are retained here.

See [the passing 0.5.0 device validation and delivered package details](docs/EIGHT_0_5.md#validation).

See [the expanded Windows 8 / 8.1 investigation and proposed live-tile plan](docs/eight-research/README.md) (62 references, settings and file-management atlases, and sampled official Microsoft 8.1 footage), [the completion review and evidence limits](docs/eight-research/COMPLETENESS_REVIEW.md), [the immersive and icon implementation notes](docs/EIGHT_0_2.md), [first-preview notes](docs/EIGHT_0_1.md), [original research handoff](docs/eight-handoff/00-START-HERE.md).

## Source and notices

Forked from Nth Desktop Ten 0.15.0 at `89cd0dd3d22205bbb43b450b3b4a27d14a7fdfcf`. The original Ten README is retained in `docs/TEN_BASELINE_README.md`. Internal Ten/Vista class names identify shared engine lineage; visible product branding and Android identity belong to Eight.

Independent Nth project; not affiliated with Microsoft. Windows is a Microsoft trademark. Preserve [third-party notices](THIRD_PARTY_NOTICES.md) and the attribution accompanying research references.

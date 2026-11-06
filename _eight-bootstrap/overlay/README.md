# Nth Desktop Eight

A Windows 8-inspired Android launcher by **An Nth Experience**, with a selectable Windows 8.1 shell. First preview: **0.1.0**. Android 8.0+; targets Android 16.

Package: `com.nthexperience.desktop.eight`. Installs alongside Nth Desktop Ten, Seven and Vista.

## First preview

- Full-screen Start with horizontal named tile groups, a separate Apps view, and a group overview.
- Hold a tile to select it. The bottom command bar supports unpin, resize, move earlier/later and move between groups. Hold a selected tile again to drag it. Keyboard and accessibility reorder actions are available.
- Windows 8 supports square and wide tiles. The 8.1 profile adds small and large tiles, a visible desktop Start button, optional startup at Desktop and desktop wallpaper on Start.
- Each profile stores its own tile layout. Switching profiles preserves desktop windows, notes and files.
- Desktop tiles restore the existing desktop; ordinary Home intents open Start. Back dismisses Charms and selection before returning to Desktop.
- Five Charms with local Apps/Settings/Files search, supported contextual sharing, Start toggle, real device destinations and contextual Settings. Use the right-side handle or the Start command bar. Essential actions do not depend on Android's reserved edge gestures.
- Full-screen PC settings with a shell selector, Start colors, profile name, accessibility and real Android destinations. A separate desktop Control Panel retains the existing advanced controls.
- Optional Aero desktop finish, independent of the 8/8.1 shell profile.
- Existing Explorer, resizable windows, browser, photos, media tools and free, opt-in Nota integration from the finished Ten baseline.
- A scrollable desktop taskbar with ordinary app buttons and tray. The Ten Search, Task View, fixed Nota slot, News and Action Center buttons are omitted.
- Eight-only backup format validates both profile layouts and rejects other launcher backups before changing preferences.

Choose the shell in **Charms → Settings → Change PC settings → Personalize**. In original Eight, the desktop Start corner also works with touch; an optional visible Start button is under Ease of Access.

## Preview boundaries

This is the first working shell slice, not a completed recreation of either release. Apps currently open in the existing desktop windows. Dedicated immersive app hosts, the historical left immersive switcher, compact immersive snap, app edge command bars, a complete 8.1-specific PC settings/search presentation, later 8.1 Update behavior and final period artwork/browser polish remain follow-up work. The current shared desktop artwork includes inherited Ten assets; research images are references, not runtime assets.

The right-side handle and always-available Apps/Charms commands are Nth touch adaptations. File search covers linked/recent locations. The 8.1 Everywhere scope combines local providers; it does not simulate Bing Smart Search. Devices opens supported Android controls. Desktop Share supports an active webpage, photo, selected Explorer files or Notepad text; unsupported contexts explain the next action.

## Build and validation

Use Java 17 and Android SDK 36:

```sh
./gradlew :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :fixture:assembleDebug
```

The main workflow builds for Android 11 and Android 16, runs the Eight surface/profile scenario and shared Browser, Explorer and Photo scenarios, and uploads APKs and native screenshots. `checks/EightTileLayoutCheck.java` checks 77,325 placements for bounds, overlap, determinism and profile-specific formats. Unit tests retain the shared calculator, AI lifecycle and accent checks. Historical Ten suites are preserved as source reference and are not asserted to pass against Eight's replacement shell.

The complete visual gallery and illustrated brief are in the owner-supplied handoff ZIP; research text and source attribution are retained here.

See [the first-preview implementation notes](docs/EIGHT_0_1.md), [original research handoff](docs/eight-handoff/00-START-HERE.md).

## Source and notices

Forked from Nth Desktop Ten 0.15.0 at `89cd0dd3d22205bbb43b450b3b4a27d14a7fdfcf`. The original Ten README is retained in `docs/TEN_BASELINE_README.md`. Internal Ten/Vista class names identify shared engine lineage; visible product branding and Android identity belong to Eight.

Independent Nth project; not affiliated with Microsoft. Windows is a Microsoft trademark. Preserve [third-party notices](THIRD_PARTY_NOTICES.md) and the attribution accompanying research references.

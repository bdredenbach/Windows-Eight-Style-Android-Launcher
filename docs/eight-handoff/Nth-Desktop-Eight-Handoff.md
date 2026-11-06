# Nth Desktop Eight - Project Handoff

Prepared for Brad Redenbach, 6 November 2026.

## Start here

Build a separate Android launcher called **Nth Desktop Eight**, inspired by **the original Windows 8 release from 2012, before Windows 8.1**. This is the recommended interpretation of the owner's request, not a newly confirmed preference. Keep 8.1 improvements as a later, explicit option. Do not accidentally build Windows Phone 8, a Windows 10 full-screen Start mode, or an 8/8.1/10 hybrid.

The owner considers Nth Desktop Ten finished. Reuse its working engines and fixes. Create Eight in the new repository the owner will provide; that URL is **pending**. This handoff does not create a repository or start an Eight implementation.

Read this document, RESEARCH-WINDOWS-8.md, ENGINEERING-PLAN.md and ACCEPTANCE-CHECKLIST.md. Open references/index.html locally to inspect the original reference images. SOURCES.md resolves citation IDs such as [W01]. Statements marked **Proposal** are Android/Nth design choices rather than claims about historical Windows.

## 1. Frozen starting point

| Item | Verified starting point |
|---|---|
| Source repository | https://github.com/bdredenbach/Windows-Ten-Style-Android-Launcher |
| Completed release | Nth Desktop Ten 0.15.0, versionCode 15 |
| Handoff commit | 89cd0dd3d22205bbb43b450b3b4a27d14a7fdfcf |
| Tested implementation commit | 94959a673ebd26244c20783c493c9379fc418d9e |
| Full Android 11/16 regression | https://github.com/bdredenbach/Windows-Ten-Style-Android-Launcher/actions/runs/42267709882 - success |
| Focused taskbar/interaction regression | https://github.com/bdredenbach/Windows-Ten-Style-Android-Launcher/actions/runs/42267710260 - success |
| Existing Ten applicationId | com.nthexperience.desktop.ten |
| Existing Java namespace | com.nthexperience.desktop |
| Toolchain at handoff | Java 17; compile/target SDK 36; min SDK 26; Android Gradle Plugin 8.13.2 |
| Suggested Eight applicationId | com.nthexperience.desktop.eight - proposal, verify availability in the new project |
| Suggested first Eight version | 0.1.0 / versionCode 1 |
| Nota upstream reference | https://github.com/bdredenbach/Nth-Nota - use Ten's already integrated client first |
| Ten APK SHA-256 | 9ba3983329443840b2845d386791f47dc077523788c3d4549aa94ec0101e8860 |

The full Ten run includes twelve native Ten suites, ten shared-engine scenarios and ten unit tests per OS. The focused run verifies Nota state, scrolling, themes, Action Center, original artwork, virtual-desktop guidance, external app cards and shake behavior. These are baseline results, **not evidence that an unbuilt Eight app passes**.

Fork the pinned Ten source into the owner's new repository. Keep Ten unchanged. Do not restart from an older Seven/Vista revision and lose the fixes accumulated in Ten. Preserve attribution and dependency notices. Do not copy signing keys, credentials, personal screenshots, private recordings or user data into the new repository or reference archive.

## 2. What changes most

The reuse opportunity is large at the engine level. The shell needs a substantial redesign:

| Keep as working capability | Replace its Ten presentation |
|---|---|
| Installed-app catalog, real icons and launch intents | Full-screen Start and All Apps |
| File operations, Explorer, grants and recovery | Windows 8 desktop colors, navigation and ribbon defaults |
| Resizable built-in desktop windows | Flat, opaque desktop window chrome |
| Browser, photos, media, notes, weather and calendar | Optional immersive presentation for selected Nth apps |
| Safe command routing and free Nota assistance | A prominent Nota tile; context commands in the Eight shell |
| Search providers | Scoped Search charm and a results surface |
| Notifications and real device controls | Windows 8-style toast/Settings-charm destinations |
| Previews and task tracking | Desktop taskbar plus a separate left app switcher |
| Persistence, backup, setup and reduced motion | Eight-specific defaults, schemas and first-use guidance |

Remove from the **default Eight UI**: Ten's compact Start menu/rail, taskbar Search button/box, Task View button, exposed virtual desktops, News and interests ticker/dashboard, Windows 10 Action Center/quick-action grid, Focus assist branding, four-size Start tiles and Aero Glass window themes. Preserve useful underlying services where they can power Eight's own interface.

Windows 8 still has a desktop taskbar, preview thumbnails, Explorer, a traditional Control Panel and desktop window management. Do not remove all taskbar functionality or all desktop snap/peek behavior just because the glass finish changes. [W03, W15, W20]

## 3. Default experience

### Start

Use a whole-screen tile surface with a large light-weight Start heading, user identity at upper right, colored tiles, pronounced group spacing and **horizontal** travel between groups. The tile system should support square and wide formats. All Apps is a separate view, not Ten's permanent app-list column. Group overview is semantic zoom. [W01, W06, W07]

**Proposal:** Seed only working destinations: Desktop, Nth Nota, File Explorer, Nth Browser, Calendar, Weather, Photos, Media and PC settings. Populate other tiles from apps actually installed. Use the Desktop tile to restore the existing desktop workspace; do not instantiate a new desktop each time.

Start must have genuine selection, reordering, pin/unpin, supported resize and live-update controls. Contextual commands belong in an edge app bar rather than a Ten-style floating Resize submenu. Android long press can be a discoverable alternate selection method; label it as an adaptation.

### Desktop

Keep useful file/folder icons, taskbar running states, grouped previews, the clock/tray and Show desktop. Use the Windows 8-era Explorer vocabulary, including Computer/Favorites/Libraries rather than automatically copying Ten's later navigation labels.

Original Windows 8 has no persistent taskbar Start button; 8.1 restores one. [W01, W13] **Proposal:** Prefer an authentic hidden corner cue plus the Start charm and Android Home. If the owner wants a permanently visible N8 Start button for convenience, expose that as an Nth accessibility option.

Do not put the taskbar across the Start screen or immersive app surface in the default 8.0 profile. Do not copy the Store-app taskbar/caption controls introduced in the 2014 8.1 Update. [W12]

### Charms

Build the five right-side destinations: Search, Share, Start, Devices and Settings. Settings is contextual to the underlying app and also offers system controls and a PC settings route. The Start charm toggles Start and the previous in-launcher surface. [W02]

**Proposal:** Add a small, optional in-content Charms handle and keyboard/accessibility routes, so Android users are not forced to discover an edge gesture. Charms operate inside Eight by default. They do not become a global overlay over arbitrary Android apps.

### Switching and snap

Keep two models separate: a left switcher for immersive experiences, with Desktop treated as one surface; and desktop window/taskbar switching for individual desktop tools. Do not rename the Ten Task View grid and call it Windows 8. [W02]

Windows 8 immersive snap uses a narrow 320-pixel view in its original environment, not an arbitrary 50/50 Windows 10 layout. [W19] **Proposal:** Implement a compact companion pane only when both owned Nth apps remain useful; on a narrow phone, offer full-screen switching instead. Never apply the old Windows hardware pixel requirement literally to modern Android density-independent sizing.

### PC settings and Control Panel

PC settings should be a flat, full-screen settings experience with its own category list. Preserve a separate desktop Control Panel for the traditional views. Do not restyle Ten's About cards and category grid and claim that is the original PC settings screen. [W16]

Map Windows concepts to working Android/Nth controls honestly: a local Nth profile is not a Microsoft login, a launcher backup is not Windows Refresh, and an Android setting destination is not an implemented Windows service.

## 4. Nth Nota stays useful

Retain the current integrated Nota behavior, not the entire unrelated standalone Nota canvas:

- Clear local requests such as “open Audible” launch one unambiguous matching app directly.
- Ambiguous requests show large, bold action choices with real app icons.
- Opening apps/settings and editing text remain separate from model-generated prose.
- Imported/shared text and quoted examples never automatically execute desktop commands.
- Model preparation is explicit. Optional free cloud writing requires the existing consent.
- Do not add paid AI dependencies or expose provider secrets.
- Stop/closing/hiding must cancel or detach the active reply and suppress late output.

**Proposal:** Make Nota a prominent Start tile, available through contextual assistance and an optional desktop pin. A dedicated fixed assistant button is an Nth extension rather than historical Windows 8 chrome.

Carry the 0.15 fix into any pin/running representation: a cached window counts as running only while attached. Closing clears its active/running highlight, removes stale Close commands and lets the next click reopen it. Minimized and closed states are different. Use normal button bounds wherever Nota is a normal taskbar button.

## 5. Preserve the hard-won fixes

Keep these as regression requirements even when their UI is replaced:

1. Default Home setup, permission explanations and a useful return path from Android Settings.
2. Real SAF/direct-file operations, verified copies, cancellation, selected-file grants and revocation recovery.
3. Folder/address/breadcrumb navigation and persisted resizable window bounds.
4. Readable text across every accent, shell style and application mode. Do not tint arbitrary content or repeatedly transform already transformed colors.
5. Real installed-app icon cards; no promise of private Android task screenshots.
6. Correct close/minimize/restore state, detached-window filtering and singleton collision handling.
7. Scrollable desktop app buttons where needed; no clipped tray or inaccessible running apps.
8. Shared callbacks, list selection, context menus and keyboard behavior.
9. Gallery rotation/EXIF/slideshow/source preservation; browser privacy/video/file selection; real media playback.
10. Reduced motion, cutout/IME handling, landscape rotation and process/activity recreation.
11. Backup validation, font-choice persistence and preservation of user's content.
12. Free, opt-in Nota fallback and late-reply protection.

Do not leave hidden Ten route names active behind the new UI. Map or retire @news, @tenactions, @taskview, Windows+W, Windows+I and virtual-desktop shortcuts deliberately. Preserve access to useful underlying capabilities through Eight destinations.

## 6. Architecture direction

**Proposal:** Introduce an explicit surface controller with these states:

- START
- ALL_APPS
- DESKTOP
- IMMERSIVE_APP(appId)
- SEARCH(scope, query, returnTarget)

Charms, app bars, contextual selection and the left switcher are overlays with an explicit return target. One overlay should not leave another orphaned behind it. Android Back dismisses the top transient surface first. Android Home returns to Start; it must not unexpectedly relaunch an external app.

Desktop windows continue using the established manager. Immersive apps use a separate presentation host. A maximized DesktopWindow with the taskbar and caption merely hidden is not a complete immersive implementation.

Inspect lifecycle ownership before reparenting bodies: TenNotaPanel closes its assistant on detach; BrowserPanel pauses/resumes WebViews and destroys them on close; PhotoGallery stops slideshow work and checks access on return; MediaPanel has detach cleanup. Create an explicit presentation/lifecycle boundary before moving these Views between hosts.

Suggested new classes and adaptation points are in ENGINEERING-PLAN.md. Keep inherited internal names until a safe refactor is useful; user-facing Eight branding must be consistent.

## 7. Android adaptations that must be explicit

- Keep edge gestures inside the launcher's available area; respect system Back, navigation bars, cutouts and keyboard insets. Provide alternate controls. [A01]
- Owned Nth content can supply genuine thumbnails. External apps use their actual icon/title unless a supported, user-consented capture feature is separately designed. [A02, A03]
- Do not embed arbitrary Android apps in Nth windows or force them into a simulated Windows 8 snap pane. Real Android multi-window depends on the OS, device and target app. [A04]
- Do not present a decorative Nth lock screen as a replacement for Android security.
- Device settings, printing, sharing and app uninstall must call real supported Android/Nth destinations.
- Tile updates should be real and bounded. A static Android app icon is preferable to an invented live preview.

These are engineering constraints. Product copy should explain the user's next action in plain language, only when the constraint affects that action.

## 8. First implementation sequence

1. **Fork and isolate:** new applicationId, app label, N8 branding, provider authorities, fixtures/scripts and release identity. Verify Eight and Ten coexist.
2. **Build the shell spine:** Start <-> Desktop navigation; safe Back/Home; explicit surface/overlay state.
3. **Build Start properly:** horizontal groups, square/wide tiles, selection app bar, All Apps and semantic zoom.
4. **Add Charms and scoped search:** every destination works; Settings is contextual; keyboard routes match the chosen era.
5. **Add the left switcher and owned immersive hosts:** preserve desktop workspace and app state.
6. **Add compact snap where useful:** owned apps only by default; graceful phone fallback.
7. **Finish PC settings, desktop styling and browser presentation:** separate the modern and desktop settings worlds.
8. **Polish and validate:** use the acceptance checklist and reference gallery; build a signed APK and a ZIP alternative.

Ship a coherent vertical slice first, then broaden. Do not begin by deleting shared engines or adding many decorative applications.

## 9. Next chat starter

Paste NEXT-CHAT-PROMPT.md and attach the complete handoff ZIP, then provide the new Eight repository URL. The first task should be to inspect the pinned base and implement the isolated shell spine. The owner can then review an installable early build before the larger visual pass.

Unsettled choices are listed in DECISIONS.md. They have recommended defaults so work can proceed without repeatedly asking the owner for minor implementation choices.

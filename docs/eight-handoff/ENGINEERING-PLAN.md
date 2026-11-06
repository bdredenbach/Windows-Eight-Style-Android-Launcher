# Nth Desktop Eight - Engineering Plan

All new class names, policies and phase boundaries below are **proposals**. The source map describes actual files in the pinned Ten repository. No Eight code is implemented by this pack.

## 1. Source map and intended disposition

Java paths are relative to app/src/main/java/com/nthexperience/desktop/.

| Existing file or area | Reuse | Eight work |
|---|---|---|
| MainActivity.java | Catalog, windows, launch routing, system destinations | Extract surface/overlay ownership; replace Ten shell construction and shortcut routing |
| StartMenu.java | App resolution, some action/data concepts | New full-screen Start renderer; no permanent Ten rail or vertical tile scroll |
| TenStartData.java | Group/pin persistence concepts and validation | Eight tile-size/profile normalization and group ordering |
| TenTileLayout.java | Placement-test approach | New height-bounded, horizontally continuing packer with square/wide sizes |
| StartSearch.java | Actual catalog/settings/file providers | New scoped Search pane/results UI and provider context |
| TenSettings.java / TenSettingsCatalog.java | Real destinations and controls | Separate Eight PC settings catalog and contextual settings providers |
| SystemTray.java | Real volume/brightness/network/battery/calendar services | Desktop tray styling and Settings-charm control reuse |
| TenActionCenter.java / TenNotifications.java | Notification backend where useful | Retire Ten drawer/quick-action grid; add Eight transient presentation only if useful |
| TenTaskView.java / ExternalTasks.java | Owned preview generation and app identity | Replace grid with a left switcher; avoid false external snapshots |
| TenDesktops.java / TenDesktopItems.java | Persistence/isolation safeguards | One desktop by default; hide virtual-desktop UI unless a named extension is enabled |
| DesktopWindow.java / WindowArrangement.java / AeroSnap.java | Window geometry, resize, desktop snap, state | Eight desktop styling; keep immersive snap separate |
| AeroGlass.java / TenStyle.java / TenAppearance.java | Contrast and state lessons | Eight tokens/renderers; do not make glass the default desktop frame |
| AeroIcon.java / TenIconAssets.java / TenIcons.java | Rendering fallback and real Android icons | Audit era-specific asset mappings; Windows 10 build 10125 artwork is not a Windows 8 baseline |
| VistaExplorer.java / DocumentOps.java / DocumentFolders.java | Actual browsing, operations, capability states | Eight chrome, vocabulary, collapsed ribbon default and accessible commands |
| StorageAccess.java / StorageSetup.java / StartupExperience.java | Guided permission/Home setup and return paths | Eight labels, first-use guidance and Start entry |
| BrowserPanel.java / BrowserData.java / BrowserUrls.java | Engine, tabs, private data, downloads, chooser, video | Presentation adapter for desktop and immersive browser chrome |
| PhotoGallery.java / PhotoCanvas.java | Decoding, EXIF, slideshow, grants | Optional immersive host plus compact layout; preserve lifecycle |
| MediaPanel.java / MediaLibrary.java / MediaPlayback.java | Working playback and shared state | Appropriate desktop/immersive presentations |
| TenNotaPanel.java / TenAssistant.java / TenNotaActions.java | Safe assistance and local action resolution | Eight destinations, discoverability and presentation; retain safeguards |
| DesktopBackup.java / ThemeProfiles.java | Validated serialization and profile concepts | Explicit Eight flavor/schema rules and intentional cross-flavor import |
| TaskbarGrouping.java / TaskbarOrder.java / TaskbarReorder.java | Grouping, scrolling, ordering | Desktop-only placement by default; normal running state |
| TenRun.java / TenSystemTools.java | Supported command and device-tool dispatch | Eight vocabulary, Quick Links mappings, no fake Windows/admin execution |
| checks/ and androidTest/ | Native/shared-engine regression knowledge | Port shell assertions and package IDs; retain engine scenarios |

Do not globally replace every occurrence of “Ten”, “Vista” or “Seven”. Internal lineage names are less risky than an unnecessary engine rewrite. Product labels, package identity, icons, setup strings, exports and visible menus must nevertheless identify Eight correctly.

## 2. Identity and project isolation

1. Fork the exact pinned Ten commit into the new owner-provided repository.
2. Change root build.gradle's launcherApplicationId to the selected Eight identity. Keeping the current Java namespace initially is acceptable.
3. Update application labels, N8 resources, notification/service labels, exported file names and first release version.
4. Check applicationId-derived provider authorities, fixture manifest queries, install scripts, test APK targets and every hard-coded com.nthexperience.desktop.ten string.
5. Confirm Eight installs alongside Ten, Seven and Vista.
6. Generate an Eight release signing identity once and keep it outside git. Reuse it for later Eight upgrades. Never bundle the Ten private signing material.
7. Preserve third-party notices. Audit Selawik and media/browser dependencies before changing versions.
8. Start with clean Eight preferences. Do not run Ten initialization/migration code accidentally and seed the wrong shell defaults.

The handoff records the current SDK/dependency versions as a reproducible starting point, not a promise that future tooling will never need updates.

## 3. Proposed shell responsibilities

| Proposed component | Responsibility |
|---|---|
| EightSurfaceController | Own root state, previous surface, navigation and restoration |
| EightOverlayController | One active transient layer, focus restoration, Back/outside lifecycle |
| EightStartScreen | Full-screen tile groups, identity header, selection and entry to All Apps |
| EightTileLayout | Height-bounded square/wide packing and deterministic reorder |
| EightAppBar | Contextual bottom/top command regions and accessible invocation |
| EightCharms | Five charm entries and the narrow-strip interaction |
| EightSettingsPane | App-specific commands plus shared device controls |
| EightSearch | Scope, query, results and return target |
| EightAppSwitcher | Left immersive recent list and one Desktop surface |
| EightImmersiveHost | Owned app presentation, compact mode and lifecycle delivery |
| EightPCSettings | Historical category presentation over real Nth/Android destinations |
| EightThemeTokens | Distinct Start, tile, desktop-frame, taskbar and app colors |
| EightGestureRouter | Arbitration among scroll, selection, reorder, edge commands and close |

These need not all become large independent classes. The key is clear ownership and no duplicated mutable state.

### Essential transitions

| Trigger | Result | State to preserve |
|---|---|---|
| First normal Home entry | Start | Existing sessions/data; do not reset a desktop |
| Desktop tile | Desktop | Window geometry, ordering and minimized states |
| Owned immersive tile | Immersive app | App identity, content and back stack |
| External Android tile | Real Android activity | Last owned surface for later launcher return |
| Start charm / in-app Windows key | Start or prior owned surface | Return target; never guess an external task screenshot |
| Android Home | Start | Safe launcher entry; no automatic external relaunch |
| All Apps action / Ctrl+Tab on Start | All Apps or Start | Query/scroll/focus when appropriate |
| Scoped search | Search results and pane | Scope, query and prior owned surface |
| Back/Escape with overlay | Dismiss top layer | Focus and underlying surface |
| Back inside owned content | App-specific back, then surface back | No accidental data loss |

Represent return targets with stable IDs, not raw View references that cannot survive recreation.

## 4. Presentation and lifecycle

Before reparenting any existing app body, define what hiding, presenting, compacting, pausing and closing mean.

- TenNotaPanel closes on detach. A host change that detaches it is currently a close, not a harmless layout move.
- BrowserPanel controls WebView pause/resume, full-screen video and disposal. Two visual wrappers must not create two accidental browser sessions.
- PhotoGallery stops slideshow work when hidden, preserves the source image and checks revoked access on return.
- MediaPanel has detach cleanup. A presentation swap must not unexpectedly end playback or leak a player.
- DesktopWindow already coordinates geometry, active state, closing and callbacks; a separate immersive host must honor equivalent contracts.

Prefer a small app-presentation interface and host-specific adapters. Persist the data needed to reconstruct each app. Avoid saving a giant screenshot as a substitute for actual state.

A desktop application can remain a desktop window even when its launcher tile is full-screen. Not every tool must receive an immersive rewrite.

## 5. Tile model and packing

Proposed persisted fields: stable item ID, group ID/order, item order, square/wide size, provider capability, label override and optional color choice. Keep installed-app identity independent from its display label.

The packer should take available viewport height, square size, gutter and ordered items. Fill a group in vertical rows, continue into another column when full, then continue horizontally between groups. Both square and wide items must have deterministic placement without overlap. Do not apply Ten's four-size, primarily vertical layout unchanged.

Separate:
- pin membership from installed-app membership;
- group order from item order;
- live provider from tile identity;
- selection/drag state from persisted layout;
- semantic overview scale from normal tile size.

Reflow on rotation, density, font scale and safe-inset changes. Preserve logical order instead of committing transient pixel coordinates from one orientation. Migrate unsupported imported tile sizes explicitly.

## 6. Input and gesture arbitration

Use a recognizable input state machine. Horizontal Start scrolling must win over incidental movement. Selection, drag/reorder, pinch overview and an app-bar edge gesture are different actions.

A top-edge gesture in an immersive app can reveal commands; a deliberate longer drag can close. Provide visible progress and a cancellation path. A desktop title-bar drag should continue moving/resizing a desktop window rather than invoking immersive close.

Android's Back gestures occupy both outer side edges. Reserve only allowed, limited exclusion regions where justified and provide an inner handle or menu route. Never depend solely on a corner that lies under a cutout or system navigation region.

Mouse/keyboard/accessibility must reach every essential command. Distinguish focus from running state; a keyboard focus cue must not masquerade as an open task.

## 7. Search, Settings and device routing

Create registries for app-context search/settings/share capability. A registry entry should either expose a working action or provide a clear unavailable state.

Suggested search scopes:
- Apps: actual installed apps and launchable Nth tools.
- Settings: Eight settings entries with real routes.
- Files: granted/indexed locations only.
- Current app: only registered Nth providers.

Settings must remember the underlying app. A browser Settings charm and desktop Settings charm should not accidentally show the same generic Ten page.

Suggested PC settings mapping:

| Historical category | Useful Nth/Android mapping |
|---|---|
| Personalize | Start palette/pattern, desktop background, local profile picture, real Android lock-screen destination |
| Users | Local launcher profile; Android account-management destination |
| Notifications | Optional access, redaction and app-specific visibility settings |
| Search | Providers, allowed locations and search history controls |
| Share | Supported content/chooser preferences |
| General | Reduced motion, startup, language destination, recovery/help |
| Privacy | Permissions, location choices, Nota cloud consent |
| Devices | Actual printing/casting/connected-device destinations |
| Wireless | Network/Bluetooth/airplane-mode destinations |
| Ease of Access | Text scaling, contrast, reduced motion and Android accessibility |
| Sync your settings | Explicit Nth backup/export; do not claim Microsoft cloud sync |
| HomeGroup | Hide/disable with an honest reason unless a genuine sharing feature exists |
| Windows Update | Clearly identified Android device-update destination |

Desktop Control Panel remains a separate route. The Windows 8 reference is a visual/organization target, not permission to invent operating-system capabilities.

## 8. Taskbar and task state

Default desktop taskbar: app pins/running windows, grouped previews, tray, clock and Show desktop. No Ten Search, Task View, News ticker or Action Center button. No fixed Nota slot by default; the owner's explicit pin can be retained as an extension.

Use the same running predicate across painting, click, context menu and preview enumeration. A closed cached DesktopWindow is not running. Keep the latest attached-window fix. A minimized attached window remains restorable. Background desktop banks must not appear active in the current desktop.

Retain scroll access when there are more buttons than available room. All ordinary icon-only buttons, including Nota when pinned, should use consistent bounds unless a user-selected fit mode deliberately changes them.

## 9. Data and backup policy

ApplicationId isolation creates a separate Android data space; do not copy user data automatically from Ten.

The current DesktopBackup uses a shared format/version, and ThemeProfiles retains inherited key names. Decide an Eight flavor/version policy before accepting cross-project imports.

For the first build, support valid Eight backups first. Later Ten import should be an explicit conversion with a preview. Do not silently drop multi-desktop banks, unsupported tile sizes, theme choices or singleton app contents. Offer a documented advanced-workspace extension or an explicit rejection when faithful conversion is impossible.

Continue validating types, sizes, IDs and referenced items before committing restored settings. Failed import must leave live state intact.

## 10. Phased delivery and exit gates

### Phase A - Isolated spine
A separately installed Eight app opens Start, reaches Desktop, preserves a desktop window and returns through Start/Back/Home. Branding and provider identities are correct. Deliver an APK early.

### Phase B - Real Start
Horizontal groups, square/wide tiles, complete All Apps, stable reorder, bottom command bar and semantic zoom. Rotation and activity recreation preserve logical state.

### Phase C - Charms and routing
Search, Share, Start, Devices and Settings all work. Context changes correctly; overlay toggling and dismissal are tested. Original keyboard meanings are mapped intentionally.

### Phase D - Immersive presentation
At least one useful owned app can run full-screen, participate in the left switcher and return to a preserved Desktop. Lifecycle and reduced-motion behavior are correct.

### Phase E - Settings and desktop polish
PC settings, desktop Control Panel, Explorer, Task Manager and the browser have their intended presentations. Colors, fonts and folder artwork fit the period.

### Phase F - Compact snap and release
Supported owned apps have genuinely usable compact layouts, with graceful fallback elsewhere. Complete regression and visual comparison, sign a stable Eight APK, and supply a ZIP alternative.

Do not schedule calendar estimates until the new repository and first vertical slice exist.

## 11. Tests to preserve and port

Keep shared browser/gallery/media/storage/Explorer/file-opening scenarios. Port the shell-facing assumptions instead of declaring old Ten assertions valid for Eight.

Introduce Eight-specific checks for tile packing, overlay state, Start/Desktop return, app-bar selection, semantic zoom, scoped search and virtual-desktop-hidden defaults. Update fixture package IDs and launcher targets.

Carry the existing callback-index test fix: target a named list row's bounds rather than the entire ListView center, and wait for the actual callback. Keep the fixed Nota task state test when an Eight desktop pin is present.

Do not claim an external Android activity is an embedded or owned immersive Nth view merely because it has a tile.

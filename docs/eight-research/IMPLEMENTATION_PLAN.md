# Proposed implementation and acceptance plan

Status: research handoff only, based on 0.4.0 at `e04e1dec8c39ad9744175fe70f7ff2f414918815`. No runtime code or APK changes accompany this pack.

## Ordered work

| Stage | Scope | Why this order |
|---|---|---|
| 1 | Stable live-tile model, real providers, centered desktop captions | Most visible Eight identity; makes existing apps more useful |
| 2 | PC settings detail navigation, Taskbar/Navigation property sheet, Explorer contexts and Recycle details | Corrects remaining structural desktop/settings mismatches |
| 3 | Reading List and PDF Reader; Browser/News Share | Adds a coherent end-to-end workflow with manageable dependencies |
| 4 | Alarms and Sound Recorder | Useful 8.1 utilities after choosing reliable Android lifecycle backends |
| 5 | Photos/Camera editing, Music/Video collections, Paint/WordPad, optional People | Larger app experiences; avoid diluting shell quality |

Keep one application package with original Eight and EightPoint One profiles. A later optional 8.1 Update refinement set can control mouse-oriented affordances. App availability and content providers need not require separate APKs.

## Source map

Paths below are relative to `app/src/main/java/com/nthexperience/desktop/`.

| Existing code | Inspected responsibility | Proposed work |
|---|---|---|
| `EightStartScreen.java` | Tile/group layout, Weather/Calendar content, global live switch, periodic render | Separate data updates from layout; per-tile view animation and preferences |
| `EightTileLayout.java`, `EightDefaults.java`, `EightProfile.java` | Placement rules, defaults and profile differences | Preserve size validation and independent profiles; migrate added fields safely |
| `EightImmersive.java` | Owned app hosts, switching and snap sessions | Content-aware compact states, Share integration and new app routes |
| `EightWeatherIcon.java`, `WeatherClient.java`, `WeatherPanel.java` | Real weather data and presentation | Provider snapshots, timestamp/expiry, forecast layouts |
| `EightNews.java`, existing News data classes | Feed/article UI and reusable data | Stable headline identity, cached image and Reading List export |
| `EightCalendar.java`, `MonthCalendarView.java` | Current date/month view | Date provider first; event storage/provider only after separate scope |
| `PhotoGallery.java`, `PhotoCanvas.java` | Image selection/display | Opt-in photo source; bounded thumbnail pipeline; richer collection |
| `MediaPlayback.java`, `MediaPanel.java`, `MediaLibrary.java` | Playback state, controls and files | Single-source Now playing provider and separate Music/Video routes |
| `DesktopWindow.java`, `VistaShell.java` | Window geometry and Eight finish | Centered caption with collision bounds; inactive/active/accessibility checks |
| `EightPCSettings.java`, `EightDesktopOptions.java` | Settings categories and taskbar options | Nested page state and real property-sheet UI |
| `VistaControlPanel.java`, `ControlPanelCatalog.java` | Desktop categories/tasks and routing | Period label audit, meaningful applet layouts and Nth extension naming |
| `VistaExplorer.java`, `ExplorerClipboard.java` | Folder UI and file operations | Contextual ribbon and root presentation without changing verified operations |
| `RecyclePanel.java`, `RecycleBin.java`, `RecycleFolder.java` | Deleted-item UI and recovery | Type column, selection-aware commands, clear properties/recovery status |
| `BrowserPanel.java`, `BrowserData.java` | Web navigation and persistence | Immersive presentation and saved-reading handoff |
| `EightCalculator.java` | Current Eight calculator wrapper | Separate 8.1 modes and reliable converter |

These are current class names, including shared engine lineage. Renaming every Vista/Ten class is unnecessary for a visual/behavior correction and would enlarge regression risk. Visible branding and app destinations still require checking.

## Live-tile implementation slices

1. Define a stable snapshot, tile identity and per-profile/per-tile preferences; migrate existing layouts and backups.
2. Introduce provider registration without changing layout placement. Start with Weather, Calendar date and Now playing because they already have real state.
3. Add News and user-selected Photos with bounded caches and explicit empty/error/privacy states.
4. Implement size-specific static and vertical-peek layouts, then the small bounded queue. Stop animation when hidden, dragging, selecting, in semantic zoom or reduced motion.
5. Add content-specific secondary tiles only after stable deep-link persistence exists.
6. Keep optional notification badges a separate feature with clear opt-in and accurate count semantics.

The detailed provider contract, proposed timing and acceptance gates are in [LIVE_TILES.md](LIVE_TILES.md). No background refresh schedule or transition duration in that proposal is asserted to be a historical Windows constant.

## Workflow acceptance matrix

| Scenario | Pass condition |
|---|---|
| Fresh install | Useful Start, clear app actions, no fabricated weather/news/calendar data |
| Original Eight profile | Only permitted sizes; appropriate settings/search/Start behavior |
| EightPoint One profile | Four formats, chosen Start wallpaper, sorting and flexible snap retained |
| Long tile drag | Surface pans in both directions; insertion target stable; no flicker or duplicate placement |
| Drag during provider update | Active tile remains attached; layout and focus unchanged |
| Start / All Apps downswipe | Returns to Desktop; retained immersive state remains recoverable |
| Weather offline / denied location | Cached reading labeled appropriately or useful setup/retry state |
| News item expires | Old headline no longer presented as fresh; stable app launch still works |
| Photos source revoked | Safe fallback, no crash or repeated permission prompt |
| Media stopped elsewhere | Tile clears playing state and taskbar highlight follows actual playback/window state |
| Live off / reduced motion | Persistent static state; no off-screen animation loop |
| Caption geometry | Long/narrow titles do not overlap icon or controls; dragging/resizing works |
| Settings navigation | Correct category/subpage selected; back and profile switch recover safely |
| Explorer context change | Ribbon updates with real selection and location; no disabled action appears active |
| File operation | Real progress/errors; cancel/recovery and restore conflicts remain safe |
| PDF / reading workflow | Share -> save -> open -> back preserves item, document and list position |
| Alarm / recorder interruption | State reconciles with real backend; no false recording or alarm claim |
| Rotation/process recreation | App state, tile identity and valid document access restore coherently |
| Accessibility | Useful labels, focus order, contrast, text scaling and non-gesture routes |
| Android external app | Honest separate-task behavior; no unsupported promise of arbitrary app embedding |

## Verification strategy

For documentation-only work, validate source IDs, link targets, baseline references and the distinction between evidence, code findings and proposals. Do not rerun the Android build merely because a research file changed.

For runtime changes, retain the current meaningful checks: tile-placement/profile invariants, snap sizing, native shell/gesture/whole-surface/immersive smoke scenarios and shared Browser/Explorer/Photo/Media checks on the supported Android matrix. Add targeted assertions for provider expiry, migration and paused animation rather than tests that merely repeat view construction.

Use screenshots for the visual changes and short recordings for drag/peek/snap behavior. Compare portrait, landscape, both profiles, active/inactive windows, high/low contrast accents and reduced motion. A screenshot cannot establish lifecycle correctness; screen-off and process-recreation checks are required for alarms, recording and live data.

## Decisions before each new app

The remaining choices are ordinary implementation decisions, not reasons to block this research: Reader's supported formats; Reading List's offline scope; alarm backend/eligibility; recorder background behavior; Calendar's local versus device storage; and optional private content on Start. Choose and document the smallest complete behavior before adding its UI.

Network-heavy apps need a separate content/provider choice. Do not resurrect defunct Microsoft services through placeholder data or implied account integration.

<!-- reference links -->

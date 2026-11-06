# Windows 8 - Appearance and Interaction Research

Research date: 6 November 2026. Target: original Windows 8, before 8.1.
Citation IDs resolve in SOURCES.md. Reference IDs resolve in references/index.html and its JSON/CSV index.

**Evidence labels:** Historical = supported by the cited period material or specification. Observation = visible in an included reference. Proposal = an Nth/Android implementation choice, not an original Windows requirement.

## 1. The identity to preserve

Windows 8 makes content, typography, color and direct manipulation carry the experience. It is not merely a desktop theme with larger icons. Microsoft described a consistent design philosophy across its modern interfaces: deliberate craft, fluid response, digital rather than ornamental presentation, restraint and consistency. [W10]

**Observation:** The included references show two visual worlds. Start and immersive apps use broad color surfaces, expansive type, sparse chrome and large rectangular content regions. Desktop tools retain dense controls, detailed icons, a taskbar and traditional windows. The contrast is intentional; one universal skin for every surface would erase it.

**Proposal:** Represent that distinction in the architecture, not only in paint. Every application presentation should know whether it is an immersive surface or a desktop tool.

## 2. Avoid mixing releases

| Feature | Original Windows 8 target | Later feature to keep out of the default |
|---|---|---|
| Entry surface | Full-screen Start | Ten's compact Start menu |
| Desktop Start affordance | Corner cue and Start charm | Persistent taskbar Start button, restored in 8.1 |
| Start tile formats | Square and wide | Additional small/large formats from 8.1 |
| Start background | Period patterns and color sets | Desktop-wallpaper-as-Start-background option from 8.1 |
| App inventory | Separate All Apps surface | 8.1's revised Apps navigation/sorting defaults |
| Search | Distinct app/settings/file scopes | 8.1 Bing Smart Search presentation |
| Immersive snap | Narrow snapped companion plus filled app | 8.1's flexible proportions/more simultaneous apps |
| Browser | IE10-era reference | IE11-era UI from 8.1 |
| Store apps on desktop taskbar | Not the original default model | 8.1 Update taskbar integration |
| Immersive mouse caption buttons | Not the original model | 2014 Update's revealed minimize/close controls |

Sources: [W01, W07, W11, W12, W13, W19]. This table is a version boundary, not a demand to recreate Windows services on Android.

The 2011 Developer Preview and early 2012 Consumer Preview also differ from shipping Windows 8. Do not use a preview's glass desktop or preliminary app arrangements as the final reference. [W21] Modern documentation dates do not prove that a design is modern: some Learn pages preserve Windows 8-specific manifest schemas. Check their actual scope.

## 3. Start screen

**Historical:** Groups are a central unit of organization; columns and separation make the surface readable, and zoomed-out grouping provides navigation. [W01]

**Observation:** Start occupies the display rather than floating above the desktop. The heading sits toward the upper left, profile identity toward the upper right, and tiles travel horizontally beyond the visible area. Group labels have a different hierarchy from individual app captions. See references 01, 11, 12 and the launch Start/group figures.

**Proposal:** On Android, preserve horizontal group travel in portrait as well as landscape. A vertical Windows Phone-like tile feed should not become the default merely because the device is a phone. Reflow the number of rows and group width while preserving tile shape and usable text.

All Apps is a different surface with compact icon/name entries. It should remain complete even when a tile is unpinned. Search and installed-app inventory should share the actual catalog, so a visible result always opens a real destination.

## 4. Tile geometry and identity

Windows 8 desktop-tile guidance documents 150 x 150 pixels at 100% scale; the Windows 8 manifest documents a 310 x 150 wide image. These are historical Windows reference dimensions, not Android dp requirements. [W06, W07]

**Proposal:** Use a square unit s and gutter g, with wide width 2s + g and the same height s. At the historical values, s=150 and g=10. Compute row capacity from the available height; continue horizontally after the column fills. A wide tile must not silently become a square or span two rows.

Modern app tiles can use branded/live presentation. Desktop shortcuts use recognizable, colorful app icons and captions; Microsoft specifically distinguishes their design from the more graphic Store tiles. [W06] Keep Android apps' actual icons instead of replacing every app with a monochrome approximation.

**Proposal:** Desktop/Android shortcuts start square and static. Enable wide or live layouts only for providers designed to render them. A calendar, weather or battery tile can use real local/service data; unsupported apps should not show invented weather, unread counts or fake screenshots.

## 5. Selection, arranging and app bars

**Historical:** Edge commands are part of the content-first model: commands appear when needed rather than permanently occupying the reading surface. Microsoft's 2012 developer material describes app-specific commands reached through edge input, mouse interaction and Windows+Z. [W23]

**Observation:** Start selection is visible through its outline/check treatment and a bottom command region. Desktop and modern app tiles offer different commands. See launch tile-command references and reference 21.

**Proposal:** Do not preserve Ten's floating tile popup as the main Eight interaction. Build a contextual bottom app bar with only supported actions. Unpinning differs from uninstalling; Android uninstall still requires the real system flow. Offer accessible selection/reorder actions and a long-press alternate for users who cannot perform a short selection flick.

Do not let normal horizontal scrolling move tiles. Distinguish scroll, selection, long-press drag and group insertion explicitly. Keep the selected app identity stable while live content updates underneath it.

## 6. Semantic zoom

This is a change of organizational view, not simply shrinking text. [W27] It should expose groups as navigation/manipulation units. Naming and moving a group must preserve its member order. [W01]

**Proposal:** A pinch, minus affordance or keyboard command opens a group overview. Selecting a group returns to that part of Start. Include a non-pinch route for accessibility and mouse use. Avoid a permanently tiny Start screen on phones; zoomed-out content is a navigation mode, not the normal operating size.

## 7. Charms and status

The system's right-side command strip contains Search, Share, Start, Devices and Settings. It is available in different app contexts. [W02]

**Observation:** Charms and the broader Settings pane have different widths and roles. The narrow strip emphasizes large symbols; an opened destination becomes a proper panel. The clock/status presentation is also distinct from the desktop clock. References 03 and 13 show the hierarchy.

**Proposal:** Keep a single overlay owner and a remembered underlying surface. Repeated activation should close or focus the already-open surface, never dismiss then immediately recreate it. Back/outside/close must release listeners and restore focus, carrying forward the Action Center bug fix principle from Ten.

## 8. Search

Search in the original model distinguishes applications, settings and files, and can operate in an app-specific context. [W26] The contemporary Microsoft navigation explanation describes typing from Start and choosing a scope. [W21]

**Proposal:** Use the existing StartSearch providers under a new presentation. Keep the query when changing compatible scopes. Show real matching installed apps, actual Nth settings destinations and only files the launcher can legitimately enumerate. An app-scoped search should use a registered Nth provider; it must not imply access to private Android app databases.

Reference 22 shows settings results; the IT guide's file-search image shows a separate results surface. This is not Ten's tabs-and-top-apps landing page or an 8.1 web-rich result collage.

## 9. Share and Devices

**Historical:** Charms connect app context to capabilities such as sharing, printing and display destinations. [W02]

**Proposal:** Share the current supported object through Android's chooser with correct MIME and temporary URI grants. If nothing shareable exists, offer a clear unavailable state. Do not silently share desktop screenshots or Nota history.

Devices should expose real printing/casting/connected-device paths when available. A list of fictional printers is not functional fidelity. Network/volume/brightness controls should use the same real services already validated in Ten.

## 10. Contextual Settings

The Settings charm is not simply a shortcut to a global settings homepage. The current app determines some of its commands; common system controls and the route into PC settings remain accessible. [W25, W02]

**Proposal:** Register a small settings provider for each owned immersive app: browser privacy/options, weather place/units, media playback preferences and Nota assistance controls. Desktop context can link to the desktop Control Panel and personalization. Open the global PC settings surface explicitly.

## 11. Left switcher and Desktop grouping

The left switcher represents the Desktop as one experience; Alt+Tab can expose desktop applications separately. [W02] See the IT guide's left-switcher reference and the launch Alt+Tab image.

**Proposal:** Keep a recent list for owned immersive presentations, with one Desktop entry and preserved desktop window state. Android app entries may reopen a recorded app but should not claim that the launcher is the Android system Recents implementation. Dismissal from the launcher list must not imply force-stopping another package.

This is a substantial change from Ten's Task View grid and exposed virtual desktops.

## 12. Immersive snap is not desktop snap

The historical snapped width is 320 pixels; the launch material also specifies the old display-resolution requirement. [W19, W02] That is a compact application view paired with a larger one. Desktop Aero Snap is a separate window-management behavior.

**Proposal:** Implement an explicit compact layout for supported Nth apps. Do not scale an entire desktop window into an unreadable miniature. On phones, permit snap only above tested usable width or provide full-screen switching. Always allow returning to one full-size app.

The later ability to vary proportions freely and show more applications belongs to 8.1's design changes. [W13] Mark any expanded Nth snap mode as an extension.

## 13. Closing and lifecycle

Windows 8 apps can be removed from view with a top-to-bottom close gesture; a short edge gesture has a different purpose. [W21]

**Proposal:** Separate gesture recognition from destructive close. Start a drag only in the appropriate top region, preview the operation, commit only beyond a clear threshold and restore on cancellation. Save relevant app state. Do not use the same drag handler for app-bar reveal, desktop title-bar move and immersive close without a state distinction.

Retain Ten's close/minimize/restore correctness: detached cached objects are closed; minimized objects are still running; hidden desktop banks must not appear active on the current surface.

## 14. Desktop visual treatment

**Observation:** The 2012 Explorer and Task Manager references show flatter, opaque colored desktop frames and traditional dense content. The title, caption buttons and ribbon are not the same visual language as an immersive full-screen application.

DWM composition did not disappear in Windows 8; Microsoft documents it as always enabled. Thumbnail previews and related composition capabilities therefore should not be thrown away along with the old glass appearance. [W20]

**Proposal:** Use separate desktop-window, taskbar and immersive-surface tokens. Taskbar translucency is a distinct choice from glassy window borders; do not route every surface through Seven's wallpaper-sampled glass renderer. Keep detailed period-appropriate desktop icons. Ten's supplied build 10125 icons are Windows 10 references, not automatically a Windows 8 asset set.

## 15. Explorer

Windows 8 introduces the ribbon experience with contextual tools, a collapsible command area and a Quick Access Toolbar. The launch description notes that the ribbon is minimized by default. [W03]

**Proposal:** Retain VistaExplorer's real operations and safe file access. Restyle its header, set the chosen Eight ribbon default, preserve File/Home/Share/View and contextual management controls, and make every toolbar command reflect real capability. Do not confuse the Quick Access Toolbar with the later Quick access navigation destination.

Use Computer/Favorites/Libraries vocabulary where it maps usefully to Android storage. User folders still open the existing integrated file engine.

## 16. Task Manager

Windows 8's redesign includes a simple initial view and a more detailed interface with grouped process information and richer performance displays. [W04] The later in-depth Microsoft walkthrough explains the heat map, sidebar mini-graphs, detail graphs and expanded tabs. [W14]

**Proposal:** Keep Ten's working task/window management and real metrics, then align the presentation. State clearly what a metric measures. Android does not give a normal launcher arbitrary cross-app process-memory or service control. [A02] Avoid filling inaccessible measurements with plausible-looking numbers.

A narrower mobile layout can collapse the metric sidebar or switch categories; preserve access to the same useful information.

## 17. PC settings and desktop Control Panel

The original PC settings categories include Personalize, Users, Notifications, Search, Share, General, Privacy, Devices, Wireless, Ease of Access, Sync your settings, HomeGroup and Windows Update. Sony's official Windows 8 support walkthrough confirms that hierarchy. [W16]

**Proposal:** Treat these as presentation groups mapped to Nth/Android capabilities. Use a launcher profile instead of a fake Microsoft account; back up Nth settings instead of claiming operating-system recovery; keep unavailable network-sharing services out of active controls. Provide a separate desktop Control Panel route for legacy-style settings views.

## 18. IE10-era browser presentation

The reference material distinguishes edge-to-edge browsing from a desktop experience. Tabs and browsing commands can be revealed rather than permanently framing the content. [W05]

**Proposal:** Keep the existing Chromium/WebView engine, security handling, downloads, file chooser, video and private-data protections. Offer an immersive shell with an appropriate bottom address/command area and a desktop shell with traditional toolbar placement. Nth Browser must not claim to run Internet Explorer's engine or ActiveX.

Decide whether the two presentations share one session before implementing them. Hidden tabs must not become duplicated live WebViews accidentally.

## 19. Typography, color and controls

Segoe UI is the historic Windows UI type family. [W17] **Observation:** large light headings, compact labels, broad accent surfaces and strong alignment do more work than bevels, gradients or dense ornament on the immersive side.

**Proposal:** Reuse the project's licensed Selawik fonts where suitable; its upstream is Microsoft and carries the included OFL terms. [F01] Define a small type hierarchy and honor Android font scaling. Suggested Android roles, not historic Windows measurements: 36-42sp page heading, 20-24sp section title, 14-16sp body, 12-14sp captions. Reduce heading size before clipping essential content.

Start color, tile identity color, desktop frame color and foreground contrast should be separate tokens. Do not multiply accent transformations on every redraw. Keep selected/pressed/focused/disabled states legible in both light and dark app contexts.

## 20. Motion

Microsoft supplied a Windows 8 animation library and described motion as part of the interface's personality. [W09]

**Proposal:** Use restrained directional transitions: content enters from the relevant edge, tiles respond to press, groups reorganize without losing identity, and an app opens from its tile context. Suggested durations should be measured on Android; no exact millisecond value in this pack is asserted as an original Windows specification. Reduced motion must remove unnecessary movement without removing feedback.

## 21. Notifications and lock-screen references

The Windows 8 package schema distinguishes badge and detailed lock-screen content. [W18] Notification concepts are separate from the later Windows 10 Action Center surface.

**Proposal:** Remove Ten's Action Center drawer from the default Eight shell. Reuse the existing notification backend only after explicit user access, use suitable transient presentation while Eight is visible, and offer notification controls in the appropriate settings. A notification history, if retained, should be identified as an Nth extension.

The decorative lock-screen reference is useful for layout and style. Android's actual keyguard remains authoritative; the launcher should not collect a counterfeit Windows password or imply that its own cover screen secures the device.

## 22. Keyboard and alternate input

The official training brochure documents a broad keyboard path through the interface. [W05] A practical Eight mapping is:

| Input | Eight destination |
|---|---|
| Windows | Start / previous owned surface |
| Windows+C | Charms |
| Windows+Q | App search |
| Windows+W | Settings search |
| Windows+F | File search |
| Windows+I | Contextual Settings charm |
| Windows+H | Share |
| Windows+K | Devices |
| Windows+Z | Current app commands |
| Windows+Tab | Left switcher |
| Alt+Tab | Owned desktop/app switcher as supported |
| Windows+D | Desktop/show desktop |
| Windows+X | Quick Links |
| Ctrl+Tab on Start | All Apps toggle |
| Escape / Android Back | Dismiss top transient surface first |

**Proposal:** Support shortcuts only where Android delivers the keys to the launcher. Preserve Android-reserved keys and provide touch, mouse and accessibility alternatives. Do not leave Ten's Windows+W route opening News after the shell is renamed.

## 23. Discoverability

The original interface depends heavily on edges and corners. Its historical interaction model is a useful target; its learning friction is not a requirement.

**Proposal:** Use the owner's preferred small, dismissible, italic hints at first use. Show how to reach Start, return to Desktop, reveal Charms, select a tile and get back from immersive apps. Use actual app controls in guidance rather than an unrelated screenshot-only tutorial. Never make a critical action depend solely on a hidden gesture.

## 24. What the next developer should inspect first

Compare the included Start, Settings-charm, left-switcher, PC-settings and Explorer images side by side. Then inspect the existing Ten source. The biggest gaps are presentation ownership, tile packing direction, app-bar interaction and contextual routing. The file/media/permission engines already solve many difficult problems and should survive the redesign.

This research is sufficient to begin a coherent first implementation. The next project should still compare its actual native screenshots with these references; a written specification is not a substitute for visual review.

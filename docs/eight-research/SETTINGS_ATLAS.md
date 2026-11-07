# PC settings and Control Panel: detailed usage atlas

The key distinction is between three surfaces: the contextual Settings charm, immersive PC settings, and the desktop Control Panel. They should feel connected while retaining different navigation and presentation.

## Settings charm and return paths

The 2013 training reference identifies Start's settings as Personalize, Tiles and Help; the Desktop pane instead exposes Control Panel, Personalization, PC info and Help. General device controls occupy the lower part, followed by Change PC settings. The upper commands therefore depend on the foreground surface. [E02]

**Nth proposal:** Weather should offer its own location and units; News its sources; Photos its chosen folders. Keep a stable lower device-control area. Opening PC settings remembers the previous app, selected category and subpage. Back from a subpage returns to its category; leaving settings returns to the originating surface. Preserve the user's downswipe-to-Desktop extension and distinguish it from closing a session.

## Original Windows 8: 13 historical categories

Historical names below are taken from the OEM's original-Windows-8 instructions; the right column is a proposed launcher adaptation, not a claim about Windows internals. [E01]

| Historical category | Useful Nth adaptation |
|---|---|
| Personalize | Start background/accent, local profile picture; distinguish Android lock-screen settings |
| Users | Nth name/picture and a clearly labeled Android accounts destination |
| Notifications | Nth preview choices and actual Android notification/access state |
| Search | Local result scopes, history clearing and supported searchable content |
| Share | Available share destinations and remembered launcher choices |
| General | Nth startup, app-switching behavior, language routes and backup access |
| Privacy | Relevant Android permissions and live-content privacy |
| Devices | Connected-device, printing and casting destinations that actually work |
| Wireless | Wi-Fi, Bluetooth and airplane-mode destinations |
| Ease of Access | Text size, contrast, motion and accessible gesture alternatives |
| Sync your settings | Explicit Nth export/import; show synchronization only if implemented |
| HomeGroup | Historical omission in current code; defer or provide a distinctly named working sharing destination |
| Windows Update | Real launcher version information and a clearly named Android system-update route |

Avoid introducing 8.1's nested category arrangement into this profile simply to reuse a list. Android-specific routes can remain common underneath the two presentations.

## Windows 8.1: category and subpage map

This is a reference map for implementation, not a promise that every hardware-specific page is visible on every PC. The PC-and-devices figure was visually checked in the Microsoft Press sample, printed page 99. The publisher's public contents/excerpts and OEM guide cross-check the remaining families. [S15][E07][E08][E09]

| Category | Reference subpages to account for | Nth implementation boundary |
|---|---|---|
| PC and devices | Lock screen; Display; Devices; Mouse and touchpad; Typing; Corners and edges; Power and sleep; AutoPlay; PC info. Bluetooth is hardware-dependent. | Build real detail pages; retain accessible Android handoffs. Disk space is an Update-era addition. |
| Accounts | Your account; Sign-in options; Other accounts | Nth profile edits are local; Android owns sign-in security and device users. |
| SkyDrive / later OneDrive | File storage; Camera roll; Sync settings; Metered connections | Use historical labels in references. A product Storage and sync page needs real linked storage/backup behavior. |
| Search and apps | Search; Share; Notifications; App sizes; Defaults | Show accurate launcher options, supported storage figures and Android default-app controls. |
| Privacy | General; Location; Webcam; Microphone; Other devices | Show real grant state and request access at the action that needs it. |
| Network | Connections; Airplane mode; Proxy; HomeGroup; Workplace | Route system-owned configuration to Android. Do not suggest a Windows domain or HomeGroup backend exists. |
| Time and language | Date and time; Region and language | Apply Nth formatting separately from device-wide language/time changes. |
| Ease of Access | Narrator; Magnifier; High contrast; Keyboard; Mouse; Other options | Offer launcher contrast/text/motion choices and useful Android accessibility routes. |
| Update and recovery | Windows Update; File History; Recovery | Nth backup/restore and build status must retain their real names and scope. |

Network/Connections and OneDrive/Metered connections were additionally cross-checked against the tutorial author's instructions (their attachment images were unavailable in this review). [E11][E12] Ease of Access/Other options exposes the animation choice in the 8.1 reference; use it as the natural home for reduced motion. [E10]

### Presentation and behavior specification

The inspected PC-and-devices figure has a dark navigation region with a much lighter detail region. Its category heading/back affordance sits above the subpage list. Current `EightPCSettings` instead uses a pale header/sidebar and renders largely flat action lists. Those are separate visual and structural gaps.

**Nth proposal:** Keep the navigation background full height; retain both navigation and content in portrait as already requested; allow each region to scroll independently. Use a compact category heading and a selected subpage, not a growing stack of unrelated buttons. At large text sizes, wrap labels and preserve useful content width instead of clipping. Remember category/subpage scroll position and unsaved inputs through rotation. Profile changes should map semantically equivalent pages, with a safe category fallback.

A gray disabled switch needs a reason when the capability is unavailable. An Android handoff should say what will open. A setting reflected in two surfaces must share one stored value; do not make a desktop switch and an immersive switch disagree.

## Traditional Control Panel: working structure

The desktop catalog is a network of tasks and applets, rather than another copy of immersive settings. Microsoft's canonical-name inventory confirms period applet identities and warns that edition/hardware affect availability. [S16]

The category-view reference uses eight families. The proposed destinations below preserve those families and translate their work into actual launcher/device capabilities. These are implementation recommendations, not a claim that Android implements each Windows subsystem.

| Category family | Useful Nth destinations and organization |
|---|---|
| System and Security | Nth Action Center, real device/build System page, Nth backup/restore, storage status, labeled device-security and power routes |
| Network and Internet | Real network status, Android Wi-Fi/network routes, browser connection/preferences where supported |
| Hardware and Sound | Devices, printing/casting routes, volume, launcher sound scheme, display and input destinations |
| Programs | Installed Android apps, default handlers, launcher-owned app choices; distinguish disabling a shortcut from uninstalling an app |
| User Accounts and Family Safety | Nth profile and Android account/family-control routes where supported; no invented local Windows users |
| Appearance and Personalization | Theme gallery, Desktop Background, Color, Sounds, Desktop Icon Settings, Taskbar and Navigation, Folder Options |
| Clock, Language, and Region | Date/time and locale formatting; Android language/input destinations |
| Ease of Access | Launcher text/contrast/motion settings plus Android accessibility |

Within category view, give the category a clear heading and a few relevant task links. All-items view shows applet icons with a persistent view selector. Breadcrumbs, search and category links must all resolve to the same owner and destination. Search should find an individual task such as hiding desktop icons; it need not open an unrelated full category first.

**Naming findings:** Current `ControlPanelCatalog` says User Accounts, and still includes Welcome Center. Review the release-specific family label; call launcher onboarding Nth Setup or Help+Tips. Keep Nth backup visibly distinct from File History. Preserve Action Center as the Eight-era reference; do not import a Windows 10 notification drawer into it merely because shared code exists.

The decoded official Microsoft demonstrations now independently show category view, Hardware and Sound, the Add a device wizard and Taskbar/Navigation properties. Their category view uses two columns of green headings with blue task links and familiar icons. On a narrow phone, preserve that hierarchy and readable targets rather than copying the desktop column count rigidly. [V08]

## Property sheets: make the controls behave like settings

The inspected Taskbar and Navigation figure, printed page 136, contains Taskbar, Navigation, Jump Lists and Toolbars tabs with OK, Cancel and Apply. The Navigation tab separates corner behavior from Start choices. [S15]

| Proposed Nth sheet | Supported behavior to implement |
|---|---|
| Taskbar | Actual visibility/size/button grouping/notification-area controls, only where supported |
| Navigation | Boot destination, Start wallpaper and corner behavior; scope Update-specific controls separately |
| Jump Lists | Launcher-owned recent/pinned destinations, an accurate privacy/clear action |
| Toolbars | Only real launcher toolbar/folder integrations; avoid decorative checkboxes |
| Desktop Icon Settings | Icon visibility and change/reset commands; keep grid/spacing as clearly named Nth desktop options |
| Folder Options | Real open/click/view/search choices that alter Explorer behavior |
| Personalization | Useful theme previews linked to background, color and sound settings |

**Nth proposal for transaction semantics:** Edits stay pending until Apply or OK. Apply commits and leaves the sheet open; OK commits and closes; Cancel discards pending changes. If a live preview is offered, retain the original values so Cancel can restore them. A setting that immediately opens an Android-owned page is a handoff, not a pending checkbox. Do not imply Cancel can undo a device change already made outside Nth.

## Small workflows that expose hidden inconsistencies

| Workflow | Acceptance condition |
|---|---|
| Desktop -> Personalize -> Desktop Icon Settings | Toggle Recycle Bin; Apply affects the desktop; reopen shows the same value |
| Start -> Settings -> Personalize | Edits affect Start; Desktop wallpaper is affected only by an explicit shared-background choice |
| App -> Settings | The upper commands match that app; Back restores the app and its state |
| PC settings -> category -> detail -> Back | Returns one level; does not unexpectedly jump to Desktop |
| Control Panel search -> task | Opens the same page/value as category navigation |
| Open Android permissions and return | Refreshes grant state and restores the Nth page and selection |
| Reduced motion from either settings surface | One stored preference changes both shell and live-tile motion |
| Narrow portrait + large font | Navigation remains visible and scrollable; text and Apply/Cancel remain reachable |

The release distinction matters more than a pixel-perfect imitation of one screenshot. Preserve the current working phone navigation while correcting the content hierarchy and page ownership.

<!-- reference links -->
[E01]: https://www.sony.com.ph/electronics/support/articles/S500024748
[E02]: https://www.teachucomp.com/accessing-settings-in-windows-8-1/
[E07]: https://storage.bestbuy.com/pacsales/resources/exclusive_brands/unbranded/PDF/UB-15MS10_UB-15MS10SA_14-0962_Tablet%20User%20Guide_V1.pdf
[E08]: https://www.oreilly.com/library/view/windows-8-1-the/9781449371777/ch10.html
[E09]: https://www.oreilly.com/library/view/windows-8-1-the/9781449371777/ch05s05.html
[E10]: https://www.eightforums.com/threads/animations-turn-on-or-off-in-windows-8.39731/
[E11]: https://www.eightforums.com/threads/virtual-private-network-connection-add-in-windows-8-1.47160/
[E12]: https://www.eightforums.com/threads/metered-connections-for-onedrive-turn-on-or-off.31765/
[S15]: https://www.microsoftpressstore.com/content/images/9780735682658/samplepages/9780735682658.pdf
[S16]: https://learn.microsoft.com/en-us/windows/win32/shell/controlpanel-canonical-names

[V08]: https://www.microsoft.com/en-us/download/details.aspx?id=57337

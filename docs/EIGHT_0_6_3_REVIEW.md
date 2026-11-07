# Nth Desktop Eight - promotion review

Review date: 7 November 2026. Candidate: 0.6.3, version code 9. Baseline: 0.6.2 (`990fcca26437a88ac5f0e25b62eb63a2d0c76024`).

## Assessment and scope

**Recommendation:** promote 0.6.3 as a Windows 8 / 8.1 inspired Android launcher. The covered core workflows pass; no remaining blocker was found in this review.

Nth Desktop Eight is an Android launcher with separate Windows 8 and 8.1 inspired shells. It provides substantial shell behavior and working local applications. It does not reproduce every Windows feature or run Windows software. Promote it using that distinction.

This review combines current source inspection, fresh Microsoft primary-source research, the previously reviewed owner recordings, automated native workflows, and inspection of generated captures. It does not claim a new hands-on Windows virtual-machine session, exhaustive testing of every Control Panel combination, or a physical Pixel 10 / Android 17 run. Public network service availability, OEM settings screens, Bluetooth peripherals and physical camera/microphone quality are outside the emulator result.

The earlier 62-source investigation remains useful historical evidence. Its 0.4.0 implementation-gap lists are planning snapshots; this document and the current code supersede those lists when describing the shipped app.

## Corrections made in this pass

| Finding | Result in 0.6.3 |
|---|---|
| Printable keyboard input on Start/Apps did not begin search | Typing opens the correct profile scope and keeps the first character; native typing is routed through the activity while focus transfers on older Android |
| Search lacked a direct submit action | Enter or the keyboard Search action opens the first displayed result; an empty result stays open |
| Ctrl+Tab always opened Apps | It now toggles Start and Apps in both directions |
| 8.1 lacked the central upward Apps gesture | Swipe up on 8.1 Start opens Apps, without adding it to the original Eight profile |
| Win+D could leave immersive content covering the desktop | It clears shell overlays and performs the existing desktop hide/restore operation |
| Back from Start could lose a PC settings return destination | It now returns to PC settings when Start was opened there |
| Hidden scoped search could reset to the default scope | Files/Settings scope is retained when opening a hidden search surface |
| Desktop tools' All apps entry opened Start | It now opens the actual Apps list |
| Share/Devices Charms differed from the period symbols | Original Nth vectors now use the three-node Share ring and connected-device outline verified against Microsoft's Windows 8 brochure [R9] |
| Delivery was based on the debug variant | CI now tests the non-debuggable release APK; final delivery keeps the retained Eight signing identity |

The owner's downward swipe from Start/Apps to Desktop is intentionally retained. Historical 8.1 instead used a downward swipe from Apps to Start [R1]. Ctrl+Tab and the visible Start control provide the authentic return route without changing that established Nth shortcut.

## Shell coverage

Status means an implemented capability or an explicit adaptation, not a claim of pixel-for-pixel or operating-system equivalence.

| Facet | Current coverage | Boundary / evidence |
|---|---|---|
| Profiles | Original Eight and Eight Point One; independent saved Start layouts | Profile changes, rotation and layout persistence are exercised |
| Start | Horizontal tiles, group overview, rename/reorder, tile pin/unpin/resize, press/hover, selection commands | One selected tile at a time; long-press replaces short flick selection |
| New columns | Drop between groups or after the final group; live edge scrolling | Real pointer drops, cancel, backup and rotation checks |
| Tile sizes | Medium/Wide in 8; Small/Medium/Wide/Large in 8.1 | Profile-specific model and packing checks |
| Live tiles | Weather, date, selected photos, RSS headlines, playing media, Reading List and next device alarm | Owned providers only; cached/expiry states and per-tile off/clear; no arbitrary Android-app live content |
| Apps view | Installed apps and Nth tools; 8.1 name/install-date/usage/category sorting | No Update NEW-app indicator, dense Apps switch or Apps-heading zoom |
| Search | Keyboard initiation, submit, Apps/Settings/linked Files; local Everywhere in 8.1 | No Bing Smart Search federation or whole-device content index |
| Charms | Search, Share, Start, Devices and contextual Settings; mouse corners and clock | Android device/print/cast routes; sharing applies to supported content |
| Mobile access | White arrows on translucent bottom-corner handles, above taskbar/app controls | Adaptation avoids depending on Android-reserved edge gestures |
| Immersive apps | Same underlying app bodies retained through Start, switching and docking | Fourteen registered app routes checked in both profiles |
| Snap | Original 8 compact companion; 8.1 adjustable two-app divider | No three/four-app or independent multi-monitor workspace; narrow-screen fallback |
| App commands | Bottom command handle, supported app bars, close/dock/share/snap | Original OS process suspension/termination is not reproduced |
| 8.1 Update | Optional mouse caption controls, context menu and taskbar access | Kept separate from base 8.1; additional refinements remain below |
| Desktop | Movable/resizable windows, active/inactive captions, maximize/restore/minimize/close, window menu and snap | Android apps remain separate tasks; no Win32 embedding |
| Taskbar | Pinning, running windows, supported external tasks, grouping/previews, tray, clock and Show Desktop | External tracking depends on Android usage access; preview restrictions apply |
| Personalization | Wallpaper/pattern/color, fonts, icon size/spacing, taskbar and motion controls | English UI; locale-aware dates are not a complete language pack |
| PC settings | Light original 8 rail; dark 8.1 rail with nested pages; Back and profile controls | Accounts, HomeGroup and Storage/sync are useful local/Android adaptations |
| Control Panel | Category navigation, desktop options, property sheets and actual Android settings handoffs | No fabricated Windows registry, services, Defender or domain management |
| Files | Real local/linked storage; breadcrumbs/Up/history; views, selection, clipboard operations and picker | Android grants/provider semantics; no Windows drive-letter model |
| Recycle Bin | Real recoverable entries, metadata, restore/collision handling and permanent deletion | Recovery availability depends on the deletion destination/backend |
| Setup | Guided default-Home/file/usage access; permission recovery and optional tips | Android owns approvals and restrictions; permission denial must remain usable |
| Backup/recovery | Preview, apply/undo, settings, both layouts, website tiles and supported app state | Not a disk image: recordings/drawings/downloads require their own exports; grants may need re-linking |
| Lifecycle | Retained content, rotation, foreground/background cleanup, reduced motion and safe insets | Physical device/OEM behavior still requires normal field testing |

## Application coverage

| Application family | Working Nth implementation | Remaining historical capability |
|---|---|---|
| Internet Explorer style browser | Android WebView, desktop/immersive chrome, fine bottom controls, tabs, private tabs, favorites/history, find, pin website, downloads, file/media requests | Chromium/WebView rather than IE10/11 Trident; no Microsoft tab/account sync |
| Weather / News | Real MET forecast/cache/location and RSS panorama | No Bing backend or licensed editorial feed recreation |
| Calendar | Month navigation, selected date, Today and date tile | No appointment database, day/week agenda, invitations or calendar-provider sync |
| Photos | Real image browsing/viewer and selected-photo live tile | No full period Photos collection/editor or camera panorama workflow |
| Music / Video | Real shared playback, queues/library surfaces and media formats supported by Android backend | No Xbox subscription/catalog or complete separate modern Music/Video redesign |
| Help+Tips | Searchable help and dismissible guidance | Describes implemented Nth controls |
| Reading List | Local URL/title/read state, Browser/News saves and backup | URL bookmarks are not offline article copies or cloud sync |
| Reader | Local PDF rendering, navigation, zoom and last-page reopening | No XPS/TIFF, PDF text search, forms, annotations or text accessibility extraction |
| Alarms | Android Clock alarm/timer handoff, next real alarm, local stopwatch | Clock confirmation and OEM ringing belong to Android; no Nth alarm database |
| Sound Recorder | Permission, actual AAC/M4A capture, levels, saved playback/rename/share/delete | Foreground only, saves on leaving; 30-minute/100-MB cap; no editor/trimmer |
| Paint / WordPad | Drawing/undo/PNG export; rich local draft, text/HTML import/export | No full Windows accessory file-format or editing parity; no DOCX/RTF |
| Other utilities | Calculator, Notepad, Sticky Notes, Character Map, Mobility Center, Steps Recorder, Task Manager and Nota | System/process information and controls reflect Android, not Windows internals |
| Mail / People / Messaging / Skype | Android-installed apps can be launched | No integrated Windows-style mail, contacts or communication clients |
| Camera / Scan / Maps | Android apps can be launched independently | No dedicated in-shell Camera, Scan or Maps implementation |
| Store / SkyDrive / OneDrive | Android store/provider/settings routes where supported | No Windows Store, Microsoft-account or cloud synchronization client |
| Bing content apps / Xbox Games | Nth utilities and installed apps remain available | Finance, Sports, Travel, Food & Drink, Health & Fitness and Xbox service apps are not recreated |

Microsoft's original edition matrix includes the original app family [R2]; the Windows 8.1 product guide and inbox-app servicing list establish the later bundle [R3,R4]. Media Center was an optional Pro add-on, not an inbox feature on every Windows 8 device [R2].

## Additional research conclusions

[R1] confirms the 8.1 Apps gesture, Ctrl+Tab, desktop wallpaper on Start and boot-to-desktop. [R5] distinguishes the later Update mouse close/minimize controls, taskbar access, tile context menu and new-app discovery cue. The existing split between profiles and the optional Update switch is appropriate.

The 8.1 product guide describes a broader operating system: connected accounts, app discovery, cloud content, integrated services and larger-screen multitasking [R3]. Microsoft's release announcement explicitly describes up to four simultaneous apps on suitable displays [R6]. Nth currently supports two owned apps. Those differences should be documented rather than presented as already implemented.

Next fidelity priorities, if wanted after promotion, are appointment-capable Calendar; richer Photos/Camera/Music/Video libraries; integrated People/Mail/Maps or clearly labeled Android app assignments; and Update Apps-view discovery/density/heading navigation. Tile multi-selection, secondary content tiles and wider multitasking also remain useful extensions. These are planned features, not failed core workflows in this release.

Windows-only facilities such as Win32/WinRT executable support, Windows Update, domain join, Hyper-V, BitLocker, Storage Spaces, File History, driver management, picture-password login and OS lock-screen policies are not supplied by a normal Android launcher. Android remains responsible for system security, actual device locking, permissions, background tasks and connectivity.

## Validation record

The release CI builds and runs unit checks plus eleven native suites on Android 11 / API 30 and Android 16 / API 36. The application under test is the non-debuggable release variant. Emulator root is used only for collecting private screenshot evidence; application and fixture code continue to run under their ordinary Android UIDs.

| Layer | What is covered |
|---|---|
| Layout/policy checks | 77,325 tile placements and 203,980 snap combinations |
| Unit tests | Calculator/accents/local AI policy, tile snapshot queues, settings routes, website tiles and group insertion |
| EightReleaseSmoke | Native typing/Enter, both search defaults, Apps search, Ctrl+Tab, 8.1 upward gesture, hidden scope, empty results, settings return, fourteen app routes per profile, Win+D, five Charms/Devices, landscape focus |
| EightFidelitySmoke / EightGestureSmoke | Recorded 8.0 surfaces, browser outlines, bottom corners, motion, website backup, tile clear/admin, actual long-press drag/edge scroll/drop/cancel and Weather states |
| EightSmoke / EightAuditSmoke | Profiles, settings categories/routes, Eight branding, real Control Panel, Task Manager, Calculator, News, cutout area and landscape search |
| EightImmersiveSmoke / EightResearchSmoke | Retained sessions, snap/dock/switch/close, backup, PDF rendering/reopen, Reading List, WordPad, Paint, icons, sound capture/release and actual provider content |
| BrowserSmoke | Real local HTTPS fixtures, navigation/private tabs, permission denial, certificate/error handling, file selection, downloads and fullscreen paths |
| ExplorerSmoke | Separate-UID picker/provider calls, shared file operations, cancellation, revoked grants and recovery paths |
| PhotoSmoke / MediaSmoke | Real decoded images, grant recovery, actual audio/video playback, seeking, queue and lifecycle handling |

### Completed validation

[Successful Android 11 and Android 16 run](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42418225262). Tested source: `1136809cde98055ffa517925c99ced3d54619b64`. [Merged source changes](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/5).

| Result | Evidence |
|---|---|
| Unit tests | 24 on each platform; zero failures, errors or skipped tests |
| Native suites | All eleven passed on each platform, including rapid typing on Android 11 |
| Visual evidence | 81 captures per platform; selected Start/search, Charms, settings, desktop, browser and utility captures visually inspected |
| Release configuration | Non-debuggable application verified in its manifest and at runtime |
| Tested delivery contents | All 526 non-signature APK entries match the tested API 36 release build byte for byte |
| Signing and alignment | APK Signature Schemes v2/v3 and 16 KB ZIP alignment verified; no native libraries in the APK |
| Update identity | Same package and certificate as delivered 0.6.1/0.6.2; version code increased to 9 |
| Physical installation | The re-signed package was not installed on the owner's Pixel 10; Android 17/OEM hardware behavior is outside this run |

APK SHA-256: `4726b3321be9ba781d51037f778749899dd06ab8e5c8345e74b3350f88f5b073`.

Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`.

The first candidate exposed a rapid-input focus issue on API 30. The activity now routes native Start typing while editor focus transfers; the final unchanged rapid-input test passes on both platforms. A passing emulator matrix supports the covered workflows; it is not a promise that every hardware-dependent facet has been exercised.

## Primary sources checked in this review

- R1. Microsoft, 23 October 2013: [What to Look Forward to in Windows 8.1](https://blogs.windows.com/windowsexperience/2013/10/23/what-to-look-forward-to-in-windows-8-1/).
- R2. Microsoft, 16 April 2012: [Announcing the Windows 8 Editions](https://blogs.windows.com/windowsexperience/2012/04/16/announcing-the-windows-8-editions/).
- R3. Microsoft: [Windows 8.1 Product Guide](https://news.microsoft.com/download/presskits/windows/docs/Windows8_1ProductGuide.pdf). Selected interface/app/multitasking sections; not a claim to have read every page.
- R4. Microsoft Learn: [Microsoft Store app package updates available](https://learn.microsoft.com/en-us/troubleshoot/windows-client/application-management/windows-81-microsoft-store-app-package-updates). Used for historical inbox app inventory, not current service availability.
- R5. Microsoft, 2 April 2014: [Windows 8.1 Update - important refinements to the Windows experience](https://blogs.windows.com/windowsexperience/2014/04/02/windows-8-1-update-important-refinements-to-the-windows-experience/).
- R6. Microsoft, 17 October 2013: [Windows 8.1 is available now](https://news.microsoft.com/source/2013/10/17/windows-8-1-is-available-now-2/).
- R7. Microsoft Press: [Windows 8.1 Inside Out, sample chapters](https://download.microsoft.com/download/b/7/f/b7ff606c-c36d-479d-91ba-f3e6163215a6/microsoft_press_ebook_windows_8.1_inside_out_pdf.pdf). Selected touch/navigation passages.
- R8. Microsoft, 26 November 2012: [Supporting Windows 8 Mail in your organization](https://techcommunity.microsoft.com/blog/exchange/supporting-windows-8-mail-in-your-organization/597403).
- R9. Microsoft: [Windows 8 brochure](https://download.microsoft.com/download/6/F/5/6F512314-780B-42D7-9D56-BA501352B78A/W8_brochure_download.pdf). Visually inspected pages 9-10 for original Apps navigation and Charms symbols; the PDF is reference material and is not bundled with the app.

## Promotion wording

Suggested accurate positioning: "A Windows 8 / 8.1 inspired Android launcher with live tiles, Charms, resizable desktop windows, built-in tools and an Internet Explorer style browser." Describe it as an independent Nth project. Avoid advertising complete Windows emulation, Windows executable compatibility or a full set of Microsoft services.

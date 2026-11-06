# Eight 0.3.0 — whole-surface audit

The owner reported Windows 10 leftovers and a Seven-branded Usage Access illustration in 0.2.0. This pass audits visible routes and presentations, rather than treating the replacement Start screen as the whole shell.

## Findings and changes

| Facet | Finding in 0.2.0 | Result in 0.3.0 |
|---|---|---|
| Usage Access setup | Embedded bitmap showed Nth Desktop Seven and N⁷ | Bitmap removed from the package. A native instruction card reads the installed Eight name and launcher icon. Android permission/recovery flow is retained. |
| Full-screen safe area | Transparent padding above Start exposed desktop shortcuts around camera insets | The Eight layer paints an opaque background behind the protected content area. Controls remain below the cutout. |
| Desktop settings | Personalize, Colors, Fonts, System, taskbar and other deep links could construct TenSettings | EightDesktopOptions supplies traditional Control Panel pages. EightSettingsCatalog resolves old shortcut IDs, Run and Nota destinations to Eight pages or actual Android settings. |
| PC settings | Eight categories existed, but links leaked into Ten Settings | Separate original Eight / expanded 8.1 categories remain. The light navigation/page presentation and PC/device links are refined. Desktop controls stay separate. |
| Task View / virtual desktops | Desktop tools, keyboard and Nota exposed Ten concepts | Entry points use the Eight app switcher. New virtual desktop commands are not offered. Legacy IDs redirect without discarding saved data. |
| Action Center | Legacy routes could request the Ten notification/quick-action sidebar | The desktop security/maintenance Action Center remains a desktop window. Charms and Android notification settings handle their respective routes. |
| News | Inherited News and interests dashboard/flyout was incompatible with Eight | A separate flat panoramic News app uses the actual RSS/Atom cache and feed connection service. No taskbar interests chip or dashboard of games/markets/weather is constructed. |
| Start | Permanent footer crowded the tile surface | Normal commands are reduced to Apps, group controls and a compact overflow. Selection retains the explicit tile command bar. |
| 8.1 Update | Existing mouse controls lacked coordinated Start header controls / tile menu | Optional Update enables Start power/search controls and a contextual mouse tile menu, alongside immersive captions, app taskbar buttons and taskbar peek. Touch selection remains available. |
| Apps view | Name/category/recent sorting only | 8.1 offers name, date installed, locally counted use, and category. Legacy recent ordering remains readable. Counts are launcher-local; no fabricated Android-wide usage. |
| Search | Input and scopes always filled the top of the results page | Wide layouts dock the existing search field and scopes in a right-hand charm pane. Narrow layouts retain reachable controls. Desktop Control Panel pages use period names in real search results. |
| Charms | Settings was an undifferentiated dark link list | Five Charms remain; Settings/Devices use the shell color, a compact settings control grid, contextual desktop/app destinations, and a wide-screen clock/date panel. |
| Notification area | Device controls used a broad quick-action tile grid | Compact hidden notification icons and Customize destination replace the dashboard-like layout. Battery, network, volume and the calendar retain actual Android readings. |
| Calculator | Ten's colored keypad and hamburger-style menu | EightCalculator uses the retained arithmetic model with a pale desktop keypad and a flat 8.1 immersive presentation. Memory, clear-entry, square root, reciprocal, scientific functions and converter routes work. |
| Explorer | Ribbon was appropriate to Eight, but naming did not distinguish profiles | File/Home/Share/View ribbon and provider-backed operations are preserved. Computer becomes This PC in 8.1, including navigation/breadcrumb labels. Both names remain valid address aliases. |
| Browser | Both immersive profiles placed tabs at the bottom | Eight places the tab strip at the top and address controls at the bottom. 8.1 puts tabs above the bottom address bar, with optional persistent address controls. WebViews, tabs and privacy behavior are retained. |
| Photos | Desktop photo-viewer chrome remained visible in full-screen presentation | Immersive Photos hides desktop menus/transport until commands are requested; revealed controls are flat. Docking restores desktop chrome on the same viewer. |
| Weather | Full-screen app was a settings form | A large forecast surface uses real cached weather and hourly values. Commands reveal existing city/location/unit controls. No sample weather is presented as live data. |
| Calendar | Fixed-height compact desktop month grid stretched into full-screen host | A flat resizable month view retains locale, navigation, Today and selected-date state. It does not pretend to provide a cloud calendar account. |
| Media | Immersive player kept glass deck/navigation surfaces | Flat immersive surfaces and transport glyphs use the same playback engine/video view. Desktop Player/optional Media Center remain Nth extensions. |
| Task Manager | Applications/Processes/Performance/Networking followed the earlier Task Manager structure | Processes, Performance, App history and Details expose supported Nth/Android data. Network graphs join Performance. Unsupported system-wide process management is not simulated. |
| Themes / backgrounds | Ten theme names and a light-beam/window-style wallpaper | Eight presets and original floral illustrations replace Ten presets/art. The user's selected background, both Start layouts and optional Aero choice remain usable. |
| Help / commands / startup | Welcome instructions and Nota suggestions still described Ten features | Eight gestures/routes, N⁸ identity, circular startup dots, and actual available tools replace stale guidance. |
| Branding / assets | Launcher identity was Eight, but setup art was not | Runtime guide, Run, startup and system identity use the installed product. The owner's 17 supplied icons remain integrated. |

## Primary research

The implementation was checked against the following period sources and their visual examples. Current Windows 10/11 support pages are not used as evidence for Eight's layout.

- [Microsoft, Getting around in Windows 8 (2012)](https://blogs.windows.com/windowsexperience/2012/03/08/getting-around-in-windows-8/): contextual Search/Settings, full-screen Start and edge navigation.
- [Microsoft, Windows 8 end-user brochure](https://download.microsoft.com/download/6/F/5/6F512314-780B-42D7-9D56-BA501352B78A/W8_brochure_download.pdf): original PC settings category list, personalization, account/lock screen controls and Charms routes.
- [Microsoft, Windows 8 / Windows Server 2012 File Explorer](https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-file-explorer/375135): collapsible ribbon and contextual commands. The ribbon itself is not a Ten leftover.
- [Microsoft, Windows 8 Task Manager in depth (2013)](https://blogs.windows.com/windowsexperience/2013/06/06/windows-8-task-manager-in-depth/): redesigned Processes, combined Performance/network information, App history and Details. Nth adapts the structure to data Android actually exposes.
- [Microsoft, Continuing the Windows 8 vision with Windows 8.1 (2013)](https://blogs.windows.com/windowsexperience/2013/05/30/continuing-the-windows-8-vision-with-windows-8-1/): expanded personalization/PC settings, Start button, Apps view options and IE11 behavior.
- [Microsoft Press, Windows 8.1 Inside Out sample chapters](https://download.microsoft.com/download/b/7/f/b7ff606c-c36d-479d-91ba-f3e6163215a6/microsoft_press_ebook_windows_8.1_inside_out_pdf.pdf): PC/device hierarchy, search pane, Apps sorting and personalization examples.
- [Microsoft, Windows 8.1 Product Guide](https://news.microsoft.com/download/presskits/windows/docs/Windows8_1ProductGuide.pdf): Start, touch gestures, content-first apps, flexible side-by-side views and calculator roles.
- [Microsoft, Windows 8.1 Update refinements (2014)](https://blogs.windows.com/windowsexperience/2014/04/02/windows-8-1-update-important-refinements-to-the-windows-experience/): mouse captions, taskbar access, tile context menus and Start controls. These are gated behind the optional Update setting.

The supplied handoff's original Microsoft screenshots were also visually reviewed: desktop, Explorer, Task Manager, Charms/search and browser commands. Reference screenshots are not used as app artwork. The new floral backgrounds are original procedural illustrations, not copies of Microsoft's photographs.

## Profile boundaries and adaptations

Original Eight keeps the hidden Start corner, original tile sizes, scoped search and fixed compact snap companion. The 8.1 shell adds its Start button, extra tile sizes, Apps ordering, desktop wallpaper on Start, flexible snap and expanded settings. The 2014 Update controls are a separate coordinated option. Aero is an independent optional desktop finish.

Nth-specific assistants, backup, resizable windows, touch handles and overflow controls are retained for Android usability. Arbitrary Android apps run as Android tasks; they are not embedded in Nth snap panes. Cloud accounts, SkyDrive/HomeGroup services, Windows activation and arbitrary OS process/service management are not simulated. Calendar is a local month/date surface. App use counts and Task Manager measurements identify their actual local scope.

Internal Ten/Vista class and preference names remain where they identify shared tested engines or backup compatibility. Historical reference classes/tests remain in source, but Eight no longer constructs their Ten shell surfaces. The automated surface-construction check guards this boundary.

## Verification

The added EightAuditSmoke exercises all 21 desktop settings pages via legacy deep links, both PC settings profiles, actual Usage Access branding, calculator/session continuity, News, Photos/Weather/Media presentations, Task Manager tabs, Explorer identities, search-pane focus and opaque Start padding. It captures portrait/landscape native layouts. Existing Eight, immersive, Browser, Explorer, Photo and Media suites remain in the workflow.

Final device results and signed package details are recorded separately in EIGHT_0_3_VALIDATION.md after completion.

# UI, settings and file-management audit

Baseline: Nth Desktop Eight 0.4.0. Historical evidence and proposed changes are separated below. This document is a design audit, not a report that these changes have shipped.

See [the detailed settings atlas](SETTINGS_ATLAS.md) and [file-management workflows](FILE_MANAGEMENT_DETAIL.md) for the expanded review.

## Interaction model across the whole shell

Eight has two complementary environments: a desktop with overlapping windows and a content-led, full-screen app environment. The key controls are Start, the left app switcher, the right Charms strip, app command bars and the desktop taskbar. Search, Share, Start, Devices and Settings are the five Charms. Settings should reflect the foreground app before offering PC settings. [S06][S07]

For Nth, every gesture should retain a visible or keyboard-accessible equivalent. A phone has Android edge navigation, a small screen and external apps in separate tasks. The launcher should adapt reachability and minimum dimensions while preserving the relationship between surfaces. It should not copy Windows pixel measurements into Android dp.

| Surface | 0.4.0 audit finding | Proposed next behavior |
|---|---|---|
| Start | Horizontal groups, sizes and selection commands exist | Live provider views; stable tile identity; deliberate portrait/landscape layouts |
| Tile rearrangement | Edge scrolling and stable active drag were fixed | Freeze live animation and layout changes during selection/drag; retain regression coverage |
| All Apps | Profile-aware app presentation/sorting exists | Distinguish installed apps, pinned items and newly added items without duplicate destinations |
| Semantic zoom | Group overview exists | Verify named-group movement and restoration of the previous viewport |
| Charms | Shell routes exist | Contextual Share/Settings with meaningful app-specific actions; clear unavailable states |
| Search | App/settings/file routes exist | Preserve original Eight scopes versus 8.1 Everywhere; useful local results before optional web search |
| App switcher | Owned immersive app sessions are retained | Recognizable preview, title and activation target; no fake screenshot for inaccessible Android tasks |
| Snap | Classic versus flexible models exist | Responsive compact app content, divider focus and rotation restoration; no squashed desktop window |
| App bars | Several apps still depend on overflow for discovery | Visible primary empty-state actions; bottom commands with icon and label when expanded |
| Desktop | Shared engine has flat Eight finish | Center captions, audit selected/inactive states, contextual desktop menus and taskbar properties |
| Navigation | Downswipe-to-Desktop is an Nth extension | Preserve it and document it; distinguish minimizing from closing/forgetting app state |
| Setup | Usage-access guidance was rebranded in prior work | Recheck every permission illustration and return path; request optional permissions when useful |

The horizontal Start/group manipulation in original footage supports the first four interaction recommendations. Preview recording details are observations, not a substitute for release-specific documentation. [V01][V02]

## Desktop windows and taskbar

The Microsoft Computer figure shows a centered caption, icon/Quick Access Toolbar at the left and caption controls at the right. The Explorer ribbon can be minimized and has selection-sensitive contextual tabs. [S10]

**Verified code gap:** `DesktopWindow` still places its caption TextView in the remaining space with left padding and default text gravity. `VistaShell` supplies the flat Eight finish, but recoloring does not fix caption geometry. `EightDesktopOptions` still presents taskbar/navigation choices as a list.

**Proposed implementation:** Use a geometrically centered title with symmetric collision limits, preserve a useful drag region and retain larger touch targets on phones. Test long titles, inactive windows, maximized windows, narrow layouts and the optional Aero finish. Close should remain visually distinct; disabled controls must not look actionable. Avoid shrinking every caption control to historical desktop pixels.

For Taskbar and Navigation, build an actual property sheet with tabs and immediate, real preview where appropriate. The period 8.1 sheet uses Taskbar, Navigation, Jump Lists and Toolbars. Map supported settings to launcher behavior, especially boot destination, Start wallpaper and corner behavior. Unsupported Windows integrations should not become inert checkboxes. [S15]

Keep running-item activation, thumbnails, grouped folders, resize memory, desktop icon spacing and app highlighting in the regression list. DWM remained active in Eight; removing all composition, previews or snapping would reduce authenticity. [S12]

The 2014 Update added mouse-oriented immersive caption/taskbar access and tile context menus. Do not silently label all of those controls as original Windows 8. An optional Update refinement set within the 8.1 shell is sufficient; it does not justify a separate APK. [S09]

## PC settings and Control Panel

The separation introduced in 0.4.0 is correct: `@settings` opens immersive PC settings and `@control` opens the traditional desktop Control Panel. Both should keep their own navigation history and selection state.

### PC settings

The period 8.1 hierarchy has nine top-level categories: PC and devices; Accounts; SkyDrive; Search and apps; Privacy; Network; Time and language; Ease of Access; Update and recovery. [S15]

**Verified gaps:** `CATEGORIES_81` currently has eight entries and omits SkyDrive. PC and devices starts with launcher personalization and a series of Android links instead of a coherent subpage structure. Several preferences are useful Nth controls but are presented as if they were the whole Windows page.

**Proposed adaptation:** Keep historical organization while naming modern capabilities truthfully.

| Proposed category treatment | Real Nth/Android content |
|---|---|
| PC and devices | Subpages for lock-screen integration, display route, input, corners/edges, power route, storage and device information |
| Accounts | Nth profile name/picture; separate route to Android accounts |
| SkyDrive-era storage area | Explain the historical name in the reference; product UI can use Storage and sync for linked cloud folders and Nth backup, only after those capabilities exist |
| Search and apps | Local scopes, default handlers, tile behavior, notification-access controls and sharing defaults |
| Privacy | Permission state and direct routes; content-preview privacy for live tiles |
| Network | Actual device controls/status and Android network routes |
| Time and language | Device date/time, language and input routes; launcher-specific formats where supported |
| Ease of Access | Text scale, contrast, reduced motion, larger targets and gesture alternatives |
| Update and recovery | Nth backup/restore and build information; distinctly named Android update destination |

Original Eight should preserve its own organization rather than receiving the 8.1 list. The completion review corrects an earlier assumption: current code has 12 entries, but the historical list has 13 including HomeGroup. Record the omission; do not add an inert network placeholder. [E01] Build detail pages and back navigation before adding more top-level links. The table above is an Nth proposal; it is not a claim that historical Windows used those replacement labels.

### Traditional Control Panel

Audit category view, all-items view, breadcrumbs, search, applet headings, task links, related links and property sheets. The canonical-name reference is useful for checking names and version availability; it is not a screenshot of every Control Panel page. [S16]

**Verified gaps and proposed fixes:**

- The catalog still contains Welcome Center. Rename the launcher onboarding destination to Nth Setup and keep it clearly separate from a claimed Windows applet.
- Check category labels against the chosen release, including User Accounts and Family Safety. Preserve readable green category headings, blue task links and the Eight desktop frame.
- Implement Taskbar and Navigation as a property sheet; use the same approach for Desktop Icon Settings and simple Folder Options where the corresponding behavior exists.
- Personalization should show a useful theme selection and working Desktop Background, Color, Sounds and Screen Saver-related destinations. Android limitations may change which destinations are available.
- A System page should report the real device, launcher build and available memory information. Do not invent a Windows edition, processor or activation status.
- Nth backup is not Windows File History, and Android account management is not Windows domain membership. Label their real function.
- Test every applet route from both a category link and search. A destination should either work, open a clearly named Android setting, or explain the specific unavailable capability.

## Explorer, folders and Recycle Bin

The important historical distinction is **Computer in Eight versus This PC in 8.1**, with a ribbon, navigation pane, address/breadcrumb bar, search box, file area and status area. Microsoft's figure establishes File/Computer/View at the Computer root and contextual Drive Tools; it is not the same tab set in every location. [S10]

**Current code:** `VistaExplorer` shares the proven file engine and now has compact Eight ribbon styling. Its generic File/Home/Share/View row and Android storage/linked-folder landing screen remain less specific than the Windows reference. `RecyclePanel` has functional restore, properties and permanent-delete paths, but its details columns omit Type.

| Area | Proposed work | Acceptance evidence |
|---|---|---|
| Root landing | Profile-specific Computer/This PC heading; real storage groups, used/free capacity and useful linked folders | Values match Android storage; no decorative C: drive masquerading as real storage |
| Navigation pane | Period-style Favorites/Libraries/Computer or This PC organization adapted to permitted folders | Every node resolves; denied access has a recovery action |
| Folder contents | Home/Share/View groups, selection-aware commands, details/icon layouts and sort state | Back/forward/up and breadcrumbs preserve the intended location |
| Contextual ribbon | Drive, Picture, Music, Search and compressed-folder tools only when relevant and implemented | Selecting a different content type updates commands without resetting selection |
| Quick Access Toolbar | Small, stable commands separate from the navigation tree | Do not confuse this Eight toolbar with Ten's Quick access folder |
| Search | Show active scope and a cancellable search state | Results open the actual document/folder; back returns to the previous location |
| File operations | Preserve verified copy/move/recycle behavior; improve progress presentation | Byte counts/rates are real; pause appears only with a resumable backend |
| Recycle details | Add Type; align sortable Name, Original location, Date deleted, Size and Type columns | Narrow mode remains readable; details match the recovery record |
| Recycle commands | Contextual Manage grouping; restore selected/all and empty actions | Enablement follows selection; restore collision handling remains safe |
| Properties | Clear file/folder information and original location | Storage-provider limitations are visible; no invented per-drive Windows settings |
| Folder options | Useful view/default/sort controls in a familiar property sheet | Choices persist and affect actual lists |

The period Recycle Bin reference shows a dedicated deleted-item view with restore/empty actions and original-location/date information. Current recovery records can supply those concepts; Android document providers still control the underlying permissions. [P01]

Eight also redesigned file-copy progress with a graph and pause. That visual is a good later refinement only when the underlying operation exposes trustworthy progress and pause/resume. [S24]

Do not weaken 0.4.0's verified file operations to imitate a screenshot. For example, an unconfirmed original deletion should remain a recovery copy, and a restore conflict should preserve both files.

## Existing app interiors

| App | Current audit | Proposed Eight / 8.1 treatment |
|---|---|---|
| Browser | Square address area, Tools gear and useful empty state now exist | Explicit desktop/immersive presentation; immersive bottom navigation/tabs; readable history/favorites and Share to Reading List |
| Weather | Working empty-state actions and real cached condition/temperature | Large condition icon, location, current temperature and forecast; compact snap view; fresh/stale/error states; real live tile |
| News | Existing feed/content engine behind Eight presentation | Editorial image/headline layout, source attribution, article state, useful compact view and headline tile |
| Calendar | Date/month display, no event-management backend | Keep honest date tile now; add events only with a real local/device event model and permissions |
| Calculator | Standard UI plus scientific dialog | Distinct Standard, Scientific and Converter experiences for 8.1; validate keyboard and number formatting |
| Photos | Photo engine and viewer exist; hiding chrome is incomplete immersion | Collection/folder browsing, chosen albums, image fit, editing only where implemented, opt-in private photo tile |
| Media | Playback/library engines exist | Distinct Music and Video collection routes with compact transport; retain the useful desktop player |
| Notepad | Desktop utility exists | Keep a flat centered-caption desktop app; file/save state must be obvious |
| Nota | Deliberate Nth addition | Preserve useful integration; do not present it as a historical Microsoft inbox app |
| Task Manager | Shared functional system window exists | Audit the Eight simple/detailed relationship, tab layout and honest resource graphs |
| Setup/help | Existing launcher guidance | Short contextual tips for Charms, drag, live tiles and app switching; avoid replaying a long tour |

IE11 is the 8.1 browser reference, while the original Eight reference is IE10. We can reproduce navigation structure using the existing Android browser backend; we cannot claim it runs either historic browser engine. [S22]

Microsoft's Task Manager article is the reference for its overview, processes and performance presentation. Android cannot expose every Windows process/service control, so charts and actions must describe the data the launcher can actually obtain. [S11]

## Usage flows that need explicit treatment

| Flow | Reference relationship | Nth implementation consequence |
|---|---|---|
| Launch and return | Start leads to an app; the preceding session can remain available | Keep minimize/restore distinct from deleting an app's state |
| Close an immersive app | Windows had specific close gestures and lifecycle semantics [S26][S27] | Avoid assuming removal from a view is equivalent to force-stopping an Android app |
| Share an article | App content is handed to a compatible destination | Browser/News should expose a real title/URL payload; unsupported sources explain why |
| Devices | Context-dependent device actions sit in a common shell entry | Route to actual print/cast/device support only when available |
| Settings in an app | App configuration and general PC settings share a shell access point | Weather location/units and News sources should be reachable without a desktop detour |
| Lock/sign-in | A system-owned experience exists outside the app environment | Keep Android's actual lock/security controls; a decorative lock scene must not claim security |
| Desktop file work | Selection changes the available file commands | Preserve selection while opening a menu, resizing, searching or changing view |
| Jump Lists/recent items | Desktop shortcuts can expose recent destinations | Use real launcher-owned history and clear privacy/clear-history controls |

Historical lifecycle terminology describes Windows behavior. Our app should preserve useful state, but Android determines process survival and task behavior. Proposed Nth navigation must remain predictable after process recreation.

## Release and usability checks

Contemporary reviews provide useful checks against visual imitation that creates avoidable friction. The 2012 review emphasizes the unfamiliar split interaction model; the 2013 review assesses the refinements. Other period reviews disagree about how far those refinements solved the problem. Treat that as evidence to test discoverability, not as a vote to reproduce every inconvenience. [R01][R02][R03][R04]

Run each main workflow with touch, pointer and keyboard where available: launch, search, Share, configure, snap, switch, minimize, restore, close, rotate and reopen. Also test reduced motion, large text, dark/light accents, offline startup and denied permissions. An empty Weather page, inaccessible Save command or invisible back path is a functional failure even when its colors are accurate.

<!-- reference links -->
[P01]: https://ptgmedia.pearsoncmg.com/images/9780789752819/samplepages/0789752816.pdf
[R01]: https://arstechnica.com/information-technology/2012/10/windows-reimagined-a-review-of-windows-8/
[R02]: https://arstechnica.com/information-technology/2013/10/windows-8-1-what-a-difference-a-year-makes/
[R03]: https://www.pcworld.com/article/447608/windows-8-1-review-the-great-compromise.html
[R04]: https://www.infoworld.com/article/2192924/microsoft-windows-windows-8-1-review-new-version-same-mess.html
[S06]: https://blogs.windows.com/windowsexperience/2012/03/08/getting-around-in-windows-8/
[S07]: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-start-screen-and-user-interface-part-1/375111
[S09]: https://blogs.windows.com/windowsexperience/2014/04/02/windows-8-1-update-important-refinements-to-the-windows-experience/
[S10]: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-file-explorer/375135
[S11]: https://blogs.windows.com/windowsexperience/2013/06/06/windows-8-task-manager-in-depth/
[S12]: https://learn.microsoft.com/en-us/windows/compatibility/desktop-window-manager-is-always-on
[S15]: https://www.microsoftpressstore.com/content/images/9780735682658/samplepages/9780735682658.pdf
[S16]: https://learn.microsoft.com/en-us/windows/win32/shell/controlpanel-canonical-names
[S22]: https://blogs.windows.com/windowsexperience/2013/10/07/right-from-the-start-the-best-web-is-on-windows-with-internet-explorer-11/
[S24]: https://blogs.windows.com/windowsexperience/2012/12/06/eight-enthusiast-updates-in-windows-8/
[S26]: https://learn.microsoft.com/en-us/uwp/api/windows.applicationmodel.activation.applicationexecutionstate?view=winrt-28000
[S27]: https://devblogs.microsoft.com/oldnewthing/20170411-00/?p=95945%2F
[V01]: https://jensenharris.com/videos/surface-ui
[V02]: https://jensenharris.com/videos/ux-week-2012-story-of-windows-8

[E01]: https://www.sony.com.ph/electronics/support/articles/S500024748

# Windows 8 / 8.1 investigation for Nth Desktop Eight

Research date: 6 November 2026. Inspected application baseline: **0.4.0**, commit `e04e1dec8c39ad9744175fe70f7ff2f414918815`.

**Recommendation:** Keep one Eight app with the existing 8 and 8.1 shell options. Build a proper live-tile system next, finish the desktop/settings details that still distinguish Eight from Ten, and then add a small set of useful period apps. There is enough here for several meaningful releases without adding empty demonstrations.

This is research and a proposed implementation specification. It does not change the runtime application or issue a new APK.

## Read this investigation

| Document | Purpose |
|---|---|
| [Live tiles](LIVE_TILES.md) | Historical behavior, real providers we can implement, rendering and lifecycle design |
| [UI and settings audit](UI_AUDIT.md) | Release boundaries, desktop windows, Control Panel, PC settings, Explorer, Recycle Bin and app presentations |
| [App candidates](APP_CANDIDATES.md) | What belonged to which release, useful new apps, Android backends and limitations |
| [Video observations](VIDEO_OBSERVATIONS.md) | Exactly what footage was reviewed, timestamps, and unavailable sources |
| [Implementation and acceptance plan](IMPLEMENTATION_PLAN.md) | Ordered work, affected code, acceptance scenarios and release gates |
| [Source ledger](SOURCES.md) | 48 references with scope, publisher, evidence type and access notes; also JSON and CSV |

## What the deeper review found

1. **Yes, live tiles belong at the center of this launcher.** Windows tiles could present changing content while the app was not open. Original Eight and 8.1 used template-based content, independently updated badges, optional queued updates and content-specific secondary tiles. [S01][S03][S04][S05]
2. **Animation needs the correct vocabulary.** Desktop Windows peek templates slide vertically inside the tile. The archive explicitly distinguishes Phone's flip behavior. We should use desktop peek/slide treatments and size-specific layouts. [S02]
3. **0.4.0 has a foundation, not a complete live-tile system.** Weather shows real cached condition/temperature; Calendar changes its date. There is one global live toggle, minute-driven rendering, and no per-tile provider queue, photo cycling, News headline tile or badge model. This is verified in `EightStartScreen`.
4. **Desktop caption placement remains a visible mismatch.** Our caption label is left-aligned. The period Microsoft Explorer figure has a centered title, separate left icon/Quick Access Toolbar and right caption controls. Centering titles without collisions is a higher-value correction than repainting every working app. [S10]
5. **The desktop settings navigation improved in 0.4.0, but its interiors remain shallow.** Taskbar and Navigation is still a checkbox/list page rather than its property sheet. PC settings has the correct broad release split but lacks much of 8.1's deeper hierarchy. Its SkyDrive-era category is absent; a useful local/cloud-storage adaptation needs an explicit scope rather than a pretend Microsoft account page. [S15]
6. **Explorer's ribbon needs context, not removal.** Its current File/Home/Share/View row is reused everywhere. The actual Eight Computer view and contextual tools differ. Our generic storage/linked-file landing page also differs from the period drive/folder layout. [S10]
7. **The best new 8.1 utility candidates are Alarms, Sound Recorder, Reading List and Reader.** The app ledger distinguishes apps newly modernized in 8.1 from apps already available in Eight. Paint and WordPad are strong desktop additions. [S14][S17][S18]
8. **Several existing apps need purpose-built immersive layouts.** Hiding Photo Viewer or Media Player chrome does not provide a full Photos or Music/Video library experience. The existing engines are useful; the missing work is the collection, navigation and editing presentation.
9. **Authenticity should improve discoverability.** Contemporary reviews repeatedly discuss the friction of the desktop/immersive split and hidden commands. We should keep visible empty-state actions, optional guided tips and accessible alternatives. [R01][R02][R03][R04]

## Evidence and limits

The investigation uses Microsoft period announcements, archived developer guidance, Microsoft Press sections, actual release-era figures, contemporary reviews and the current source code. Modern Learn navigation and generic product headers are not treated as historical UI evidence. Preview footage is labeled; 8.1 Update controls are kept separate from 2013 8.1.

Two original/designer-hosted recordings were visually reviewed through playback and frame sampling. Selected sections of the longer recording were reviewed, not its full 66 minutes. The original 8.1 Preview upload is now private, and the public mirror and Microsoft Learn interview did not produce playable media here. Their metadata, posters or descriptions are not counted as watched footage. The 8.1 usage analysis therefore relies on accessible Microsoft documentation, period captures and the official demonstration transcript. See the video ledger for precise scope.

No Windows 8 VM was operated in this investigation. This is a source-and-footage investigation plus a source-code audit, not a claim of hands-on testing of every Windows applet. The inspected PDF samples are selected chapters/pages, not entire books. Complete publisher books, video files and article copies are not bundled or used as app assets.

## Version boundaries to preserve

| Area | Original Windows 8 | Windows 8.1 | 8.1 Update option |
|---|---|---|---|
| Start entry | Full-screen Start; desktop corner/charm | Desktop Start button; optional boot to Desktop | Input/device-dependent desktop refinements |
| Tile sizes | Medium square and Wide | Small and Large added | Same four sizes |
| Start customization | Pattern/color system | More backgrounds; desktop wallpaper option | Same base model |
| Apps access | All apps command | Upward access gesture/arrow; sort options | New-app notice and mouse refinements |
| Search | Apps/Settings/Files model | Everywhere/Smart Search model | Refined mouse access |
| Immersive layout | Fixed compact snapped companion | Flexible side-by-side views | Mouse caption/taskbar access |
| Tile mouse commands | Selection/app bar | Base 8.1 selection model | Context menu beside the tile |
| PC settings | Smaller category set | Expanded device/account/app/network hierarchy | Later behavior kept version-scoped |

This is a compact synthesis of [S06][S07][S08][S09][S15]. Android sizing is a separate engineering decision; historical Windows pixels must not be copied as phone dp.

## What should stay distinct

- The desktop Control Panel and immersive PC settings have different jobs and visual structures.
- Desktop File Explorer remains a ribbon-based file manager. It should not inherit Ten's Quick access landing page.
- Desktop Photo Viewer and Windows Media Player-style windows can remain alongside richer immersive Photos and Music/Video views. The 2014 desktop-oriented defaults are documented in Microsoft's Update tips. [S23]
- DWM composition and thumbnails continued in Eight. Optional Aero can remain an Nth finish without becoming the default Eight window frame. [S12]
- A live tile is a status/launch surface. Our first implementation should launch a stable app or pinned subview; inline media buttons, tile folders, unrelated widgets and Phone-specific behavior should be separately identified extensions.
- Android owns device security, permissions, external app tasks and system settings. A Windows-style destination may route to a real Android control, but should not claim to implement Windows Update, domain membership, BitLocker, HomeGroup or Windows services.

## Recommended sequence

**First release:** Live-tile infrastructure and real Weather/News/Photos/Calendar-date/Now-playing providers, per-tile live settings, reduced-motion behavior, centered desktop captions and a regression check against the 0.4.0 drag fixes.

**Second release:** Full PC settings subnavigation, Taskbar/Navigation property pages, Explorer context-sensitive ribbon and drive/folder views, and Recycle Bin presentation/details.

**Third release:** Reading List and a PDF Reader. These add a coherent Browser/News -> Share -> Reading List -> side-by-side reading workflow and make the existing file browser more useful.

**Fourth release:** Alarms and Sound Recorder with real persistence and Android lifecycle handling. The first working alarm backend must be chosen before promising reliable background ringing.

**Later:** Camera/Photos editing, distinct Music/Video collections, desktop Paint/WordPad, and People. Maps, Food & Drink and other network-heavy apps need a separate data/content decision.

The detailed plan includes implementation scope and acceptance checks. These are proposed releases, not commitments to ship every candidate or fabricated completion dates.

<!-- reference links -->
[R01]: https://arstechnica.com/information-technology/2012/10/windows-reimagined-a-review-of-windows-8/
[R02]: https://arstechnica.com/information-technology/2013/10/windows-8-1-what-a-difference-a-year-makes/
[R03]: https://www.pcworld.com/article/447608/windows-8-1-review-the-great-compromise.html
[R04]: https://www.infoworld.com/article/2192924/microsoft-windows-windows-8-1-review-new-version-same-mess.html
[S01]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh779724(v=win.10)
[S02]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh761491(v=win.10)
[S03]: https://learn.microsoft.com/en-us/uwp/api/windows.ui.notifications.tileupdater.enablenotificationqueue?view=winrt-28000
[S04]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh779719(v=win.10)
[S05]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh465372(v=win.10)
[S06]: https://blogs.windows.com/windowsexperience/2012/03/08/getting-around-in-windows-8/
[S07]: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-start-screen-and-user-interface-part-1/375111
[S08]: https://blogs.windows.com/windowsexperience/2013/05/30/continuing-the-windows-8-vision-with-windows-8-1/
[S09]: https://blogs.windows.com/windowsexperience/2014/04/02/windows-8-1-update-important-refinements-to-the-windows-experience/
[S10]: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-file-explorer/375135
[S12]: https://learn.microsoft.com/en-us/windows/compatibility/desktop-window-manager-is-always-on
[S14]: https://news.microsoft.com/download/presskits/windows/docs/Windows8_1ProductGuide.pdf
[S15]: https://www.microsoftpressstore.com/content/images/9780735682658/samplepages/9780735682658.pdf
[S17]: https://learn.microsoft.com/en-us/troubleshoot/windows-client/application-management/windows-81-microsoft-store-app-package-updates
[S18]: https://blogs.windows.com/windowsexperience/2013/09/27/right-from-the-start-reading-list/
[S23]: https://blogs.windows.com/windowsexperience/2014/04/10/some-tips-and-tricks-for-using-the-windows-8-1-update-updated/

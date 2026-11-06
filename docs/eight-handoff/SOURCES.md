# Sources and Evidence Notes

Accessed 6 November 2026. Primary technical sources are Microsoft, Android Developers, Microsoft GitHub and the owner repository; Sony is the manufacturer support source for its Windows 8 UI. No forum answer is used as the basis for the implementation plan.

## Release discipline

- W01-W08 are the central Windows 8 appearance references.
- W11-W13 describe later features for comparison, not the default design target.
- W09 is pre-release design context; W10 contains 8.1 Preview examples and is used for shared principles only.
- Living Learn API pages are cited for explicitly identified historical behavior or concepts; their current navigation chrome is not a Windows 8 screenshot.
- Observations and Android proposals are distinguished from original Windows behavior in the research.

## Primary-source bibliography

### W01 - Start Screen and User Interface - Part 1
Publisher: Microsoft AskPerf / TechNet. Date/scope: 2012-10-23; migrated 2019; Windows 8 launch.
URL: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-start-screen-and-user-interface-part-1/375111
Tiles, groups, Start and removal of the persistent desktop Start button.

### W02 - Start Screen and User Interface - Part 2
Publisher: Microsoft AskPerf / TechNet. Date/scope: 2012-10-24; migrated 2019; Windows 8 launch.
URL: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-start-screen-and-user-interface-part-2/375126
Switching, Charms, scoped actions and snap. Its approximate fraction description is qualified by W19 for the actual narrow width.

### W03 - Windows 8 / Windows Server 2012: File Explorer
Publisher: Microsoft AskPerf / TechNet. Date/scope: 2012-10-26; migrated 2019; Windows 8 launch.
URL: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-file-explorer/375135
Ribbon, contextual tools, Quick Access Toolbar and minimized ribbon default. HTML article body was readable even where the text extraction view was empty.

### W04 - Windows 8 / Windows Server 2012: The New Task Manager
Publisher: Microsoft AskPerf / TechNet. Date/scope: 2012-10-27; migrated 2019; Windows 8 launch.
URL: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-the-new-task-manager/375149
Simple/detailed presentation and client/server distinctions. Selected images are the Windows 8 client examples.

### W05 - Windows 8 End User Training Brochure
Publisher: Microsoft. Date/scope: PDF metadata: 2013-05-09; Windows 8, before 8.1.
URL: https://www.microsoft.com/en-us/download/details.aspx?id=39055
Official 36-page brochure. The current Download Center date is a later republication date, not the OS release date. Only selected UI images/regions are bundled.

### W06 - Desktop App Tiles on the Start Screen
Publisher: Microsoft Learn. Date/scope: Living archive; checked 2026-11-06; Explicit Windows 8 desktop tile guidance.
URL: https://learn.microsoft.com/en-us/windows/win32/shell/desktop-app-tiles-on-the-start-screen
Reference square sizes, icon identity and desktop tile design.

### W07 - DefaultTile - package schema for Windows 8
Publisher: Microsoft Learn. Date/scope: Living archive; checked 2026-11-06; Windows 8 manifest schema.
URL: https://learn.microsoft.com/en-us/uwp/schemas/appxpackage/appxmanifestschema/element-defaulttile
Square/wide behavior and original wide-image dimensions; do not treat the modern UWP page chrome as the historical UI.

### W08 - Introducing Windows 8: An Overview for IT Professionals
Publisher: Microsoft Press. Date/scope: PDF metadata: November 2012; Windows 8.
URL: https://download.microsoft.com/download/b/1/e/b1e7f4c9-304d-456c-bd96-a2287fa7871d/microsoft_press_ebook_introducing_windows_8_pdf.pdf
Original UI figures for Quick Links, Files search, switching, Weather, Explorer, Task Manager and properties. PDF page indices are recorded in the image manifest.

### W09 - Bring apps to life with Metro style animations in HTML5
Publisher: Microsoft BUILD. Date/scope: 2011; Pre-release design concepts only.
URL: https://learn.microsoft.com/en-us/shows/build-build2011/app-206t
Animation personality. Not used as the shipping RTM screenshot baseline.

### W10 - Designing and building user interfaces for Windows
Publisher: Microsoft Windows Developer Blog. Date/scope: 2013-08-13; Shared 8/8.1 design principles; contains 8.1 Preview examples.
URL: https://blogs.windows.com/windowsdeveloper/2013/08/13/designing-and-building-user-interfaces-for-windows/
Shared design principles only. Its 8.1 Search examples are not imported into the Eight 8.0 visual target.

### W11 - Continuing the Windows 8 vision with Windows 8.1
Publisher: Microsoft Windows Experience Blog. Date/scope: 2013-05-30; 8.1 comparison only.
URL: https://blogs.windows.com/windowsexperience/2013/05/30/continuing-the-windows-8-vision-with-windows-8-1/
Personalization, new tile formats, Search, PC settings and IE11 changes.

### W12 - What is New in the Windows 8.1 Update
Publisher: Microsoft AskPerf. Date/scope: 2014 Update; migrated 2019; 8.1 Update comparison only.
URL: https://techcommunity.microsoft.com/blog/askperf/whats-new-in-the-windows-8-1-update/375462
Store-app taskbar integration and mouse caption controls are later features.

### W13 - Windows 8.1 now available
Publisher: Microsoft Windows Experience Blog. Date/scope: 2013-10-17; 8.1 comparison only.
URL: https://blogs.windows.com/windowsexperience/2013/10/17/windows-8-1-now-available/
Returned Start button, alternate startup and flexible multi-app snap.

### W14 - Windows 8 Task Manager In-Depth
Publisher: Microsoft Windows Experience Blog. Date/scope: 2013-06-06; Windows 8, before 8.1 release.
URL: https://blogs.windows.com/windowsexperience/2013/06/06/windows-8-task-manager-in-depth/
Grouped heat map, performance graph hierarchy and expanded tabs.

### W15 - Eight Enthusiast Updates in Windows 8
Publisher: Microsoft Windows Experience Blog. Date/scope: 2012-12-06; Windows 8.
URL: https://blogs.windows.com/windowsexperience/2012/12/06/eight-enthusiast-updates-in-windows-8/
Desktop tools, Quick Links, copy improvements and multi-monitor context.

### W16 - How do I access PC Settings and what options are available in Windows 8?
Publisher: Sony Support. Date/scope: Content for Windows 8; support revision 2018; OEM primary support.
URL: https://www.sony.com.ph/electronics/support/articles/S500024748
Confirms the original PC settings category hierarchy. Its old image links were unavailable; Microsoft PDF figures are bundled instead.

### W17 - Segoe UI font family
Publisher: Microsoft Typography. Date/scope: Living reference; checked 2026-11-06; Font reference.
URL: https://learn.microsoft.com/en-us/typography/font-list/segoe-ui
Historical font family and redistribution information. No proprietary font files are bundled.

### W18 - LockScreen - Windows 8 package schema
Publisher: Microsoft Learn. Date/scope: Living archive; checked 2026-11-06; Windows 8 manifest schema.
URL: https://learn.microsoft.com/en-us/uwp/schemas/appxpackage/appxmanifestschema/element-lockscreen
Badge/detailed lock-screen content concepts. Not an Android lock-screen implementation.

### W19 - Best practices for designing authentication web pages
Publisher: Microsoft Learn. Date/scope: Living archive; checked 2026-11-06; Includes explicit Windows 8 snap guidance.
URL: https://learn.microsoft.com/en-us/windows/win32/secauthn/best-practices-for-designing-authentication-web-pages
Explicit 320-pixel Windows 8 snapped width; do not substitute an approximate one-third ratio.

### W20 - Desktop Window Manager is always on
Publisher: Microsoft Compatibility Cookbook. Date/scope: Windows 8 / Server 2012; Desktop composition.
URL: https://learn.microsoft.com/en-us/windows/compatibility/desktop-window-manager-is-always-on
Composition persists despite the visual change away from the old glass desktop frame.

### W21 - Getting around in Windows 8
Publisher: Microsoft Windows Experience Blog. Date/scope: 2012-03-08; Consumer Preview navigation; cross-checked against later material.
URL: https://blogs.windows.com/windowsexperience/2012/03/08/getting-around-in-windows-8/
Search scopes, app commands and close gestures. Preview screenshots are not the final desktop-style reference.

### W22 - Windows 8 Arrives
Publisher: Microsoft News. Date/scope: 2012-10-25; Release context.
URL: https://news.microsoft.com/source/2012/10/25/windows-8-arrives/
Historical release context, not a current recommendation to install Windows 8.

### W23 - Programming Windows 8 Apps with HTML, CSS, and JavaScript
Publisher: Microsoft Press. Date/scope: 2012 edition; Windows 8 developer material.
URL: https://download.microsoft.com/download/8/E/2/8E27933F-9B1E-409E-A0A3-508A39D75E9F/Microsoft_Press_eBook_Programming_Windows_8_Apps_with_HTML_CSS_and_JavaScript_PDF.pdf
Content-before-chrome and app-command interaction. The complete book is not redistributed.

### W24 - Designing Windows Store News Apps
Publisher: Microsoft MSDN. Date/scope: November 2012; Windows 8.
URL: https://download.microsoft.com/download/6/3/e/63eee0ea-d7a8-4be6-b71c-5e3dfdce18f9/mdn_1112dg.pdf
Content-focused design, tiles, notifications and share/device contracts.

### W25 - SettingsPane class
Publisher: Microsoft Learn. Date/scope: Living API archive; Historical charm API; deprecated for later Windows.
URL: https://learn.microsoft.com/en-us/uwp/api/windows.ui.applicationsettings.settingspane
App commands in contextual Settings. Not proposed as an Android API.

### W26 - SearchPane class
Publisher: Microsoft Learn. Date/scope: Living API archive; Historical charm API; deprecated for later Windows.
URL: https://learn.microsoft.com/en-us/uwp/api/windows.applicationmodel.search.searchpane
Search-charm context and query model. Not a recommendation for modern Windows application development.

### W27 - SemanticZoom class
Publisher: Microsoft Learn. Date/scope: Living API reference; Concept reference; modern API chrome.
URL: https://learn.microsoft.com/en-us/uwp/api/windows.ui.xaml.controls.semanticzoom
Different semantic views of grouped content, rather than arbitrary text scaling.

### A01 - Ensure compatibility with gesture navigation
Publisher: Android Developers. Date/scope: Checked 2026-11-06; Current Android constraint.
URL: https://developer.android.com/develop/ui/views/touch-and-input/gestures/gesturenav
System edge Back conflicts, limited exclusions and alternate input paths.

### A02 - ActivityManager API reference
Publisher: Android Developers. Date/scope: Checked 2026-11-06; Current Android constraint.
URL: https://developer.android.com/reference/android/app/ActivityManager
Third-party recent-task/process/service limitations; supports honest external-app previews and metric scope.

### A03 - Media projection
Publisher: Android Developers. Date/scope: Checked 2026-11-06; Current Android constraint.
URL: https://developer.android.com/media/grow/media-projection
User-consented capture sessions; not silent access to other app thumbnails.

### A04 - Support multi-window mode
Publisher: Android Developers. Date/scope: Checked 2026-11-06; Current Android constraint.
URL: https://developer.android.com/develop/ui/views/layout/support-multi-window-mode
OS/device-dependent split screen and windowing behavior.

### F01 - Selawik
Publisher: Microsoft GitHub. Date/scope: Checked 2026-11-06; Open font upstream.
URL: https://github.com/microsoft/Selawik
Keep the actual included OFL notices; do not rename proprietary Segoe files as a substitute.

### C01 - Nth Desktop Ten pinned source
Publisher: Owner repository. Date/scope: 0.15.0 / 89cd0dd3d22205bbb43b450b3b4a27d14a7fdfcf; Implementation evidence.
URL: https://github.com/bdredenbach/Windows-Ten-Style-Android-Launcher/tree/89cd0dd3d22205bbb43b450b3b4a27d14a7fdfcf
Local source inspected for the architecture map; source is linked rather than copied into this pack.

### C02 - Ten 0.15.0 full Android regression
Publisher: Owner GitHub Actions. Date/scope: Successful result checked 2026-11-06; Validation baseline.
URL: https://github.com/bdredenbach/Windows-Ten-Style-Android-Launcher/actions/runs/42267709882
Both OS jobs successful at tested implementation source 94959a673ebd26244c20783c493c9379fc418d9e.

## Reference retrieval and reuse

The official brochure was obtained from https://download.microsoft.com/download/6/f/5/6f512314-780b-42d7-9d56-ba501352b78a/W8_brochure_download.pdf . Its metadata dates it to May 2013, before the public 8.1 preview. The complete brochure and Microsoft Press books are not included.

Several legacy Tech Community image links no longer resolve. The included figures use current Microsoft-hosted versions of the original article images. Original and current URLs are recorded where applicable.

Some images are extracted original figures. Others are tightly bounded captures of a PDF UI region so layered diagrams remain intact. Gesture illustrations and layout diagrams are labeled. These are publisher references, not captures of a running Eight prototype or a personally operated Windows 8 VM.

The reference manifest records the exact page/figure, retrieval type, dimensions and image hash. Page numbers for PDF extracts are 1-based PDF pages, which can differ from printed chapter page numbers.

Microsoft UI imagery remains attributed to Microsoft. Research references are not a claim that all artwork may be bundled as runtime assets, and are not labeled original Nth artwork. Preserve actual notices and provenance when selecting runtime assets. The owner-supplied Windows 10 build 10125 icon pack is not reclassified as Windows 8.

The pack includes original commentary and implementation proposals rather than complete copies of web articles or books.

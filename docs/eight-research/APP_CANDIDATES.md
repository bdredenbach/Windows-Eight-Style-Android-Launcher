# Native-era app candidates and implementation scope

These are proposed additions. No app in this document is claimed to have been implemented by this investigation. Priority reflects useful Android behavior and fit with the existing launcher.

## Historical catalog

Microsoft's 8.1 package inventory includes Alarms, Calculator, Camera, Photos, Reader, Reading List, Sound Recorder, Scan and the Bing app family, among others. A package name alone is not evidence that every SKU, region or clean installation exposed every app identically. [S17]

| Candidate | Historical distinction | Nth fit |
|---|---|---|
| Alarms | Modern utility in 8.1; alarm/timer/stopwatch presentation | Strong, provided ringing has a real backend |
| Sound Recorder | Modern 8.1 app; desktop recording utility predates it | Strong, small useful workflow |
| Reading List | Added in 8.1 for saved shared reading items | Very strong connection to Browser and News |
| Reader | Present in the Eight era; distinct from Reading List | Very strong missing document destination |
| Calculator | Modern app added in 8.1; desktop calculator already existed | Improve current implementation |
| Scan | 8.1 utility for compatible scanners | Lower priority; phone document capture would be an Nth adaptation |
| Camera / Photos | Core modern-era media experiences, revised in 8.1 | Useful next layer around real capture and existing image engine |
| Music / Video | Xbox-branded period apps alongside desktop playback | Improve existing collection/transport UI |
| Mail / Calendar / People | Connected communication suite | Calendar useful; Mail/account synchronization substantially larger |
| News / Weather / Finance / Sports / Travel | Bing information family | News/Weather already useful; other feeds need providers |
| Food & Drink / Health & Fitness | Added to Bing's 8.1 family | Interesting later apps with content/data decisions |
| Paint / WordPad | Traditional desktop utilities in both versions | Strong offline additions after core shell work |
| Fresh Paint / Movie Moments | Microsoft app ecosystem additions | Optional inspiration; not universal inbox requirements |

The Product Guide documents Reader's PDF/XPS/TIFF role, Alarms' three modes and Sound Recorder's record/edit/share use. Those historical formats are a reference, not a promise that our first Android Reader supports all of them. [S14]

The 8.1 Bing announcements establish the Food & Drink and Health & Fitness additions and the broader family. Their discontinued services should not be treated as live APIs. [S20][S21]

## First additions

### Reading List

**Historical reference:** Shared articles/pages enter a categorized, visual reading collection, with a route back to the source and useful side-by-side reading. [S18]

**Proposed Nth scope:** Accept URL, title, source, optional thumbnail and optional user note from Browser/News and Android Share. Store a local database with categories, read/unread, date and duplicate handling. Open the source in our Browser, retaining the reading-list position; support removal, export and a pinned category or article tile. Use one clear save action.

Saving a URL does not make its web page available offline. Offer explicit saved text or a captured document only if the corresponding extraction/storage path is implemented, and preserve attribution. Test sites that require sign-in, missing titles, network failures and malformed links. This is a smaller, more coherent first app than a complete mail client.

### Reader

**Proposed Nth scope:** A focused PDF viewer reached from Explorer, downloads and Android Open with. Include page position, thumbnails/page jump, fit modes, rotation, recent documents and a compact snapped view. Start with PDF; add XPS/TIFF only after choosing and testing actual decoders. Clearly distinguish this lightweight Eight-era Reader destination from the broader Nth Reader books/comics product.

`PdfRenderer` is a practical initial Android backend with a seekable-file requirement. Some document-provider streams need a bounded local copy. Newer text/search APIs require platform gating; broad old-device search/annotation support is a separate implementation decision. [A01]

Inspect the current Nth Reader repository before choosing shared code or a handoff contract. This research did not inspect that repository, so no plug-and-play integration is assumed. Test encrypted, malformed, large and externally revoked documents; show recoverable errors rather than a blank canvas. Remember document access across process death only when permission persists.

### Alarms

**Proposed Nth scope:** Three modes: alarms, timer and stopwatch. Persist alarms and running timer deadlines; derive elapsed time from a monotonic source where appropriate. Provide accessible start/pause/reset, sound choice, recurrence and clear next-trigger information. A tile may show the next configured alarm or a running timer, with truthful stale/permission status.

Android distinguishes exact and inexact alarms, and exact scheduling has access and eligibility constraints. A launcher is not automatically eligible for every dedicated-clock-app permission. Decide the backend and distribution rules before promising reliable background ringing. [A02]

Two valid implementation paths are an integrated Android clock handoff with our own timer/stopwatch presentation, or a fully supported native alarm backend. A UI Handler alone is insufficient. Test screen-off, process death, reboot, time-zone/daylight-saving changes, denied access, sound modes and notification actions. Ringing must be dismissible and must not duplicate after restoration.

### Sound Recorder

**Proposed Nth scope:** Record, pause where supported, stop, name, list, play, rename, delete and share audio. Show elapsed time and a level meter based on real samples. Store files through an intentional private/export model; verify file finalization after interruption. Add trimming only after implementing safe, non-destructive output handling.

Android's MediaRecorder provides the core recording path and microphone permission model. Ask on Record, handle denial, and release the microphone correctly. Background recording requires a deliberate lifecycle/service design rather than keeping a view alive. [A04]

Use a distinct recording color and obvious active state. Initial live-tile content can show the latest saved recording or active recording state when trustworthy. It must not imply recording continued after the backend stopped.

## Improvements to existing apps

| Area | Proposed useful work | Data/lifecycle dependency |
|---|---|---|
| Calendar | Real appointments, day/week/month views, reminders and next-event tile | Choose local-only events or Android Calendar provider; explicit read/write permissions for device calendars |
| Photos | Albums/folders, browsing, crop/rotate and a chosen-photo tile | Current image engine; durable file access; bounded image decoding |
| Music | Album/artist collection, queue and Now playing tile | Existing playback state must be the single source of truth |
| Video | Library, resume position and compact transport | Existing player; full-screen/orientation and interruption handling |
| Calculator | Proper 8.1 mode selection and unit conversion | Existing arithmetic engine plus tested conversion definitions |
| Browser | Immersive tab/address presentation and Reading List Share | Existing WebView/history; URL validation and error states |
| Weather | Forecast, refresh/location management and more live layouts | Existing weather cache, timestamps and provider; no invented default reading |
| News | Categories, story persistence, headline tile and Share | Existing feed engine, article identity, source attribution and image cache |

Android Calendar provider access is permission-controlled; the current date-only Calendar does not already possess an appointment database. [A05]

## Later desktop and creative apps

**Paint:** A small but real bitmap editor fits the desktop well: pencil/brush/eraser, color palette, shapes, fill, selection, crop, resize, undo/redo and PNG/JPEG save. Begin with a bounded canvas and correct export. Ribbon styling can follow the chosen Eight desktop reference without promising every classic Paint feature.

**WordPad:** A basic rich-text editor is more demanding than Notepad. Start with headings, font emphasis, alignment, lists and a defined document format. Do not label a plain-text file as RTF or DOCX. Choose and test import/export before showing a format in the Save dialog.

**Camera:** CameraX can provide genuine preview and image capture. Add device switching, permission recovery, rotation and a gallery handoff. A scanner mode would be a separate Nth feature, not a claim that Windows Scan was a phone-camera scanner. [A06]

**Photos editing:** Microsoft describes the 8.1 creative app family and photo changes. Use that as presentation inspiration while limiting tools to transformations we actually implement. Fresh Paint-style pressure media and Movie Moments-style editing are separate projects. [S19]

**People:** Local contacts plus useful launch actions could work well. Choose opt-in contacts access, merge rules and a private tile policy before presenting contact photos on Start. Historical social-network aggregation cannot be restored by drawing its old UI.

## Candidates to defer

- **Mail:** Real account authentication, provider differences, background sync, MIME, attachment handling and deletion semantics make this much larger than a themed inbox. Prefer an external mail handoff until a complete backend is scoped.
- **Maps and Travel:** Maps require a provider, attribution, tile/download policy and a usable search/offline story. Travel adds curated content and potentially changing availability. Consider the existing Nth FootPrints ecosystem only after reviewing its current interfaces.
- **Finance and Sports:** Good tile concepts, but trustworthy real-time data, coverage, licenses and market/time-zone labeling must be selected first.
- **Food & Drink:** Could become a local recipe collection, meal planner and grocery list without requiring an old Bing feed. Label original Nth content and imported recipes appropriately.
- **Health & Fitness:** A tracker requires explicit data ownership and Android health permission planning. A static dashboard with fictional steps is not useful.
- **Store, Windows Update, HomeGroup, BitLocker and Windows services:** Do not create counterfeit system capabilities. Offer clearly named Android app-store/settings routes where useful.
- **Media Center:** Optional Windows-era feature, not a universal Eight inbox requirement. The existing desktop media player is a more practical first target.

## App quality gate

Every addition needs a useful first-launch state, visible primary action, save/error/permission behavior, compact layout, back path, process-recreation behavior and a clear scope. Only apps with real state should feed live tiles. Prefer four complete utilities over a large catalog of decorative launch screens.

<!-- reference links -->
[A01]: https://developer.android.com/reference/android/graphics/pdf/PdfRenderer
[A02]: https://developer.android.com/develop/background-work/services/alarms
[A04]: https://developer.android.com/media/platform/mediarecorder
[A05]: https://developer.android.com/identity/providers/calendar-provider
[A06]: https://developer.android.com/media/camera/camerax
[S14]: https://news.microsoft.com/download/presskits/windows/docs/Windows8_1ProductGuide.pdf
[S17]: https://learn.microsoft.com/en-us/troubleshoot/windows-client/application-management/windows-81-microsoft-store-app-package-updates
[S18]: https://blogs.windows.com/windowsexperience/2013/09/27/right-from-the-start-reading-list/
[S19]: https://blogs.windows.com/windowsexperience/2013/10/15/right-from-the-start-apps-for-creative-expression/
[S20]: https://blogs.bing.com/search/2013/6/New-Bing-Apps-Arrive-with-Windows-8-1/
[S21]: https://blogs.windows.com/windowsexperience/2013/09/20/right-from-the-start-bing-apps/

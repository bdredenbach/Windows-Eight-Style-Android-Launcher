# Completion review of the original research request

Reviewed 6 November 2026. This expanded edition revisits the saved investigation, adds missing detail, and corrects a settings-model assumption. Application baseline remains Eight 0.4.0 at `e04e1dec8c39ad9744175fe70f7ff2f414918815`; the first research commit is `42b573417e21b4041089d2384e1386a9d6e5cf3a`. This is research and implementation planning; it does not ship an APK.

## Coverage against the user's prompt

| Requested investigation | Earlier coverage | Work completed in this review |
|---|---|---|
| Live tiles we can implement | Strong historical model and provider specification | Rechecked Microsoft's desktop tile overview; retained real built-in providers and explicit third-party limitations |
| Microsoft websites | Period announcements, archived developer guidance and product guide | Added Help+Tips, layout guidance, naming chronology and Update-specific storage behavior |
| Articles across the web | Contemporary reviews and publisher samples | Added original OEM instructions, training material, publisher excerpts and first-hand tutorial captures |
| Videos about functionality, looks and UI | Two visually reviewed Windows 8-era recordings; 8.1 playback gaps disclosed | Visually sampled more of the designer's talk and four decoded official Microsoft 8.1 demos; separately recorded the failed third-party lesson attempt |
| Inner PC settings | Top-level comparison | Added both category maps, nested 8.1 pages, entry/back paths and profile-specific corrections |
| Inner Control Panel | Category/app routing audit | Added a destination matrix, applet versus property-sheet behavior, and practical Android mappings |
| Folders, windows, Recycle Bin | Major structural differences and current code gaps | Added navigation/selection rules, folder/root/ribbon contexts, dialog behavior and a recovery-state matrix |
| More native-era apps | Strong modern-app shortlist | Added Help+Tips and desktop accessories; verified that Steps Recorder is already present |

## Corrections and consequential additions

**Original Eight's settings list is incomplete in our code.** `EightPCSettings.CATEGORIES` has 12 entries. The original Windows 8 list documented by Sony has 13, including HomeGroup between Sync your settings and Windows Update. The earlier instruction to keep the existing 12-category model was too broad: preserve the original Eight organization, but record the HomeGroup omission. A network placeholder should not be added without a useful implemented destination. [E01]

**The 8.1 omission is separate.** Its nine-category historical list includes SkyDrive. Current `CATEGORIES_81` has eight entries. A real Storage and sync adaptation remains a product decision; a historical name alone does not provide cloud authentication or synchronization. [E02][S15]

**Later additions need their own boundary.** Disk space under PC and devices is documented for 8.1 Update. SkyDrive became OneDrive in February 2014; a guide using OneDrive throughout is not automatically a 2013 screenshot reference. The Update power button also depended on device characteristics. [S23][E04]

**Help+Tips deserves explicit app status.** Microsoft's 2013 description confirms both the included Help+Tips app and contextual first-run stickers. For Nth, a small searchable help hub plus dismissible tips directly fits the established preference for guidance on entering a new area. [E03]

**Mobility Center is another useful desktop candidate.** The official Microsoft demo shows a compact grid of device controls. A Nth version could reuse brightness, volume, battery and rotation controllers, displaying only supported controls. [V08]

**Reuse Steps Recorder.** `StepRecorder.java` and `ActionCenter.java` already implement opt-in launcher action logging, manual desktop captures, review and HTML export. It is an improvement/discovery task, not a new app from scratch. Its temporary export basename still says `nth-seven-steps.html`; this is a code-audit finding for a future cleanup, not a change made here.

## How to use this edition

Read the settings atlas and file-management detail alongside the original live-tile specification and UI audit. Historical labels describe the reference system. Columns headed Nth adaptation, proposal or acceptance describe future launcher work. Presence in the reference catalog does not establish availability on every edition, device or region.

The revised priority is: real live tiles and caption geometry; a consistent settings hierarchy and Help+Tips; Explorer/Recycle details; Reading List/PDF Reader; Alarms/Sound Recorder; then larger media and creative apps. This is an ordered proposal rather than authorization to silently start every implementation.

## Remaining evidence limits

No Windows 8/8.1 virtual machine was operated. These findings come from documentation, period screenshots, original usage material, selected video playback and source inspection. No complete audit of every hardware-dependent Control Panel applet or OEM variation is claimed. The TeachUcomp 8.1 video remained at 0:00, but four official Microsoft 8.1 demonstration files were subsequently decoded and visually sampled successfully. Their exact OS build remains unidentified. Source and video ledgers distinguish those outcomes. No new screen recordings, publisher screenshots or proprietary assets are bundled.

<!-- reference links -->
[E01]: https://www.sony.com.ph/electronics/support/articles/S500024748
[E02]: https://www.teachucomp.com/accessing-settings-in-windows-8-1/
[E03]: https://blogs.windows.com/windowsexperience/2013/11/21/getting-help-and-support-with-windows-8-1/
[E04]: https://news.microsoft.com/source/2014/02/19/microsoft-onedrive-launches-worldwide-free-cloud-storage-service-provides-one-place-for-everything-in-your-life/
[S15]: https://www.microsoftpressstore.com/content/images/9780735682658/samplepages/9780735682658.pdf
[S23]: https://blogs.windows.com/windowsexperience/2014/04/10/some-tips-and-tricks-for-using-the-windows-8-1-update-updated/

[V08]: https://www.microsoft.com/en-us/download/details.aspx?id=57337

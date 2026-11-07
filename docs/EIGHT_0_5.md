# Nth Desktop Eight 0.5.0 — research implementation

This release turns the Windows 8 / 8.1 investigation into working launcher features. It retains the Android package and independent original Eight / EightPoint One Start layouts.

## Working changes

- Retained live-tile content views: Calendar date, cached Weather, up to five fresh RSS headlines, explicitly selected Photos, actual player state, Reading List unread/recent items and Android's next device alarm. A data update does not repack the grid or replace drag targets. Queues are bounded, expired News is removed, Small tiles stay icon-based with supported badges, and motion stops while selected, dragging, hidden, backgrounded or reduced motion is enabled.
- Per-profile/per-tile live switches in the selection bar and 8.1 Update context menu. Photos require an explicit document-picker selection and retained grants; unavailable photos have a reconnect state. Clear tile photos is available in the mouse menu.
- Dark PC settings navigation and light detail pages, thirteen original Eight categories and nine 8.1 categories. The missing HomeGroup destination offers actual Android sharing/network routes. The historical SkyDrive slot is adapted as **Storage and sync**, using real file providers and Nth backup rather than Microsoft authentication. 8.1 has stable category/subpage routes, local Back and preserved scroll positions; Android settings return to the originating Nth settings surface. Disk space is exposed with the optional 8.1 Update setting.
- Taskbar and Navigation property tabs with buffered edits: Apply commits, OK commits and returns to Desktop, Cancel discards. External settings opened from Toolbars remain their own actions.
- Centered desktop captions when width permits, with reserved icon/control bounds and a constrained-width fallback.
- Explorer Computer/This PC context uses File / Computer / View; folders retain File / Home / Share / View. Computer has separate folders and devices/drives sections, an actual capacity meter, and real storage/system/control destinations. Existing copy/move/delete/recovery engines remain in place.
- Recycle Bin adds Item type, sortable details, and horizontal access to readable columns on a phone. Existing restore-conflict and recovery-status behavior remains authoritative.
- Help+Tips: searchable, contextual, replayable short guidance and an inline dismissible settings tip.
- Windows Mobility Center: real brightness, media volume, battery, orientation and connectivity/display routes. Brightness and volume use the same underlying controls as the tray.
- Reading List: saved web references, read/unread state, filters, share/remove and backup. Browser's menu and Share charm support saving, with an explicit confirmation before an InPrivate reference leaves private browsing. Hold a News story to save it.
- Reader: offline PDFs up to 100 MB, bounded off-thread rendering, previous/next, page jump, zoom/pan, error recovery and last-document/page reopening. Explorer routes PDFs to Reader; Open with remains external. This first reader renders pages as images and does not provide PDF text search, forms, annotations or screen-reader extraction.
- Alarms: scheduled alarms/timers use Android Clock with its confirmation and background ringing. Nth displays the actual next device alarm when Android supplies it. A local persisted stopwatch supports start/pause, laps and reset; a device restart ends its running interval. Nth does not invent its own scheduled-alarm database or claim an unconfirmed Clock request is active.
- Sound Recorder: explicit microphone permission, AAC/M4A recording, actual input level, save/list/playback, rename/share/delete, and microphone release/save when leaving the surface or app. Foreground recording only; no hidden background microphone service. Recordings have a 30-minute / 100 MB cap and can be exported through Share.
- Paint: bitmap canvas, colors, brush widths, eraser, undo/redo, recovery draft, saved drawings and PNG export/share.
- WordPad: local rich-text draft, selected bold/italic/underline/size formatting and clear formatting, text/HTML import and portable HTML export; plain-text Share. It does not claim DOCX/RTF support. Sources are kept separate from the local draft until Save as.
- Character Map: supported Latin, Greek, currency and symbol glyphs, selection, Unicode description and clipboard copy. Existing Sticky Notes and Steps Recorder are reused; Steps Recorder's exported name now identifies Eight.

## Data and limits

Settings backup includes live choices, photo references, Reading List and WordPad's draft. Android file grants do not transfer to another device; select photos/documents again there. Audio recordings, drawings and rendered PDF caches are not included in settings backup; export wanted recordings/pictures with Share. No publisher video, reference screenshot or proprietary Windows asset was added.

People/contact integration, cloud synchronization, Maps/content services, arbitrary Android-app live content and secondary content tiles remain separate follow-up work. The larger Photos/Camera and Music/Video library redesigns remain in the research roadmap; the existing viewers/players are reused here.

## Validation

The release adds provider queue/expiry/badge invariants and settings-route unit checks, plus a native `EightResearchSmoke` scenario for actual retained views, settings Back, Taskbar Apply, reading/backup, two-page PDF rendering/reopening, formatting, characters, accessories, Computer ribbon and Recycle Bin details. It joins the existing gesture, immersive and shared-engine device suites on Android 11 and Android 16.

Final build/device results are recorded after execution; physical-device microphone quality and OEM Clock handoffs need device-specific confirmation.

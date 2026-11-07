# Nth Desktop Eight 0.5.0 — research implementation

This release turns the Windows 8 / 8.1 investigation into working launcher features. It retains the Android package and independent original Eight / EightPoint One Start layouts.

## Working changes

- Retained live-tile content views: Calendar date, cached Weather, up to five fresh RSS headlines, explicitly selected Photos, actual player state, Reading List unread/recent items and Android's next device alarm. A data update does not repack the grid or replace drag targets. Queues are bounded, expired News is removed, Small tiles stay icon-based with supported badges, and motion stops while selected, dragging, hidden, backgrounded or reduced motion is enabled.
- Per-profile/per-tile live switches in the selection bar and 8.1 Update context menu. Photos require an explicit document-picker selection and retained grants; unavailable photos have a reconnect state. Clear tile photos is available in the mouse menu.
- Dark PC settings navigation and light detail pages, thirteen original Eight categories and nine 8.1 categories. The missing HomeGroup destination offers actual Android sharing/network routes. The historical SkyDrive slot is adapted as **Storage and sync**, using real file providers and Nth backup rather than Microsoft authentication. 8.1 has stable category/subpage routes, local Back and preserved scroll positions; Android settings return to the originating Nth settings surface. Disk space is exposed with the optional 8.1 Update setting.
- Taskbar and Navigation property tabs with buffered edits: Apply commits, OK commits and returns to Desktop, Cancel discards. External settings opened from Toolbars remain their own actions.
- Distinct flat utility icons are shared by Start, taskbar and desktop captions.
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

### Completed checks

Tested source: `c9a3dbc760b872ebf5834125564d93af496008a4` (runtime changes through `58e33fdb9a82686123b430e9d60a17b9ce04e591`). [Successful Android 11 and Android 16 run](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42326240628).

| Check | Result |
|---|---|
| Build, unit tests and instrumentation packaging | Passed on both API 30 and API 36; 16 unit tests, zero failures |
| Native suites | All nine runners passed on both versions: Eight, Gesture, Audit, Immersive, Research, Browser, Explorer, Photo and Media |
| Tile packing | 77,325 placements; bounds, overlap, determinism and profile formats |
| Snap policy | 203,980 combinations; profile limits, minimum widths and narrow fallback |
| New document/data workflows | Actual PDF render/page reopen, Reading List backup/read state, rich formatting through UI restoration, drawn PNG/undo/export and chosen-photo decoding |
| Recorder | Actual AAC start, encoded audio track, saved file and microphone release when Start opens on both emulators |
| Visual review | Native view captures in portrait/landscape; captions, photo label contrast, utility icons, settings, property sheets, Reader, Recorder, Paint, Help and Mobility Center |
| Signed delivery | Original Eight certificate; v2/v3 signatures and 16 KB ZIP alignment verified |
| Tested payload after signing | All 505 non-signature ZIP entries match the tested API 36 APK byte for byte |
| Download ZIP | CRC passes; contains the exact signed APK and install notes, with no fixture/test APK or signing material |

Package `com.nthexperience.desktop.eight`, version code **5**, version **0.5.0**. The certificate matches delivered 0.1.0–0.4.0, including the saved 0.4.0 APK checked directly, for an in-place update.

- Certificate SHA-256: `3a7ab565951c7a62fd785c60df3a1f3975ec8447d1307b46e77293a69017c72d`
- APK SHA-256: `a5b505a0b12aa822cf79029afa489d131536b8c113359c418ef991dfbcf9cc63`
- Download ZIP SHA-256: `3575fce720a9612c49a7cc231baffcbddb481c4aa32ff3555c0825a5210605f4`

Emulator validation establishes recording/encoding and lifecycle behavior. Physical-device microphone quality, acoustic routing and OEM Clock confirmation/ringing behavior still depend on the device. No claim of an Android 17 device run is made; the supported compile/target API remains 36.

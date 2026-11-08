# Nth Desktop Eight 0.7.0 delivery

[PR #6](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/6) implements the Calendar/collections/connected-app follow-up. See [features, usage and scope](EIGHT_0_7.md).

## Validated source

- Tested head: `106e70ca3805b0bf70628fc242389bf3053e2964`; baseline 0.6.3 main `4e2425052acb7170b9e8162a2d4ffe4fa03e5d3f`.
- [Successful release and regression run 42441978248](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42441978248).
- 33 release unit tests on each CI job; zero failures, errors or skipped tests.
- All twelve native suites passed on Android 11/API 30 and Android 16/API 36 against the non-debuggable release: EightConnectedSmoke, EightReleaseSmoke, EightFidelitySmoke, EightSmoke, EightGestureSmoke, EightAuditSmoke, EightImmersiveSmoke, EightResearchSmoke, BrowserSmoke, ExplorerSmoke, PhotoSmoke and MediaSmoke.
- 77,325 tile placements and 308,182 snap combinations passed.

The new native scenario exercises actual appointment/contact/place forms, validation, .ics file bytes, device Calendar intent, real Android Contacts and Calendar fixture rows, People-to-Mail/Maps actions, draft retention, read-only attachment sharing to a separate UID (no email sent), selected-folder albums, actual PNG thumbnails, embedded MP3 artist/album/duration tags, recursive music/video import, search/collection browsing, validated backup application and real Activity recreation. The final scenario asserts landscape Week appointments have real visible height and text, and that the selected media category tab is visible.

Selected captures were visually inspected. A first visual pass caught a zero-height landscape Week agenda and an off-screen selected media tab; both are corrected in this tested head. Automatic Android settings hierarchy restoration also exposed a real Activity-recreation crash during development; explicit state restoration now owns those views. No passing test result from an earlier candidate is substituted for this final head.

## Delivered package

- `Nth-Desktop-Eight-0.7.0.apk`, version 0.7.0/code 10, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36.
- APK SHA-256: `7b4e41c505c8088235a6eb1e28dc6253ce0dcf22c7498c034dd229d726113838`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`; same retained identity as delivered 0.6.1, 0.6.2 and 0.6.3. Install over those versions without uninstalling. The older 0.5.0 certificate differs.
- All 526 non-signature APK ZIP entries match the tested API 36 release artifact byte for byte. Release artifact ZIP SHA-256: `0aaed7a676756c8eba451580a7bfb53a724e137fe76009e45e3e142a87fd9116`.
- APK Signature Schemes v2/v3 and 16 KB ZIP alignment verified. No native libraries are packaged. Non-debuggable manifest and absence of Calendar/Contacts write permissions verified.
- Download ZIP SHA-256: `edbf192c788c33c8dc93210dd2a4fecf27ba89682216dd2cfa45732dc5760c06`. CRC and contained-file byte comparisons passed. It contains only the signed APK, install notes and public package metadata.

Private signing material and the emulator fixture/test APKs are excluded from the repository delivery and download archive.

## Testing limits

The new-app screenshots use view rendering after black AOSP compositor captures; this cannot prove every hardware-rendered WebView map pixel. The attributed OSM embed document and selected pin loaded, but complete online tile availability, live physical GPS, OEM Calendar/email handlers and sending a real email are not claimed as deterministic CI coverage. Mail inbox/authentication/final sending belongs to the user's Android email app; device Calendar owns reminders/recurrence. No physical Pixel 10/Android 17 installation was performed in this session. This is a Windows 8/8.1 inspired Android launcher, not a complete Windows OS or Microsoft-service implementation.

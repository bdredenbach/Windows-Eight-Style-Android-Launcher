# Nth Desktop Eight 0.7.5 — Control Panel and Back navigation

The owner recordings `224475.mp4` and `224476.mp4` show two problems: Control Panel links open a separate older category window, while Back from the Eight settings page minimizes it and exposes a previously opened Internet Explorer window. The intended destination is the Eight Personalization/settings layout at the recordings' end.

## Updated behavior

Control Panel aliases from Start, desktop shortcuts, Run, PC settings and Control Panel Home now reuse one `EightDesktopOptions` window. Existing pinned `@control`, `@legacycontrol` and `@desktopoptions` IDs remain usable. System, accounts, startup, clock, advanced taskbar and wallpaper controls render as content inside that same grey-sidebar/white-content layout. Their native control implementation is reused without attaching its old window chrome, category sidebar or address/search toolbar. Internal detail links route through the Eight host.

Control Panel maintains a bounded 32-entry page history and page scroll positions. Toolbar Back and Android Back return through visited settings pages. At the root, Android Back returns to Desktop and retains owned windows for explicit reopening; an older browser is not brought forward. The existing browser and its live state remain available through its taskbar button. Explicit browser Back and fullscreen-video behavior retain their existing handling.

Taskbar properties OK/Cancel returns inside Control Panel. Apply and appearance refreshes retain the selected tab. Page/history/tab state is included in the existing saved-view/session flow. Embedded forms retain their draft state during appearance/profile-picture refreshes. Advanced taskbar controls are available under Toolbars → More taskbar options; advanced wallpaper controls under Desktop Background → Picture position and slideshow options.

## Validation

- [PR #11](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/11); tested source `c110674277b6e9ece75c64e094f168850f8680fc`, based on main `9cb87efa4737a29887bebf0221af22a3c9aeaee0`. Merged as `f4de63ed201f96fcf6b0c8e7f87ec2a1205e2732`, with the same tested tree.
- [Successful run 42483068775](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42483068775): 33 unit tests, zero failures/errors/skips, and all fourteen native suites on Android 11/API 30 and Android 16/API 36 against the non-debuggable release.
- All 25 desktop settings routes were exercised. New checks drive alias, Run, PC settings and Home entry points; actual toolbar and Android Back handlers; no browser creation on root Back; no reveal of a retained browser; explicit reopening of the same browser; history/view-state roundtrip; Taskbar Cancel; and visible bounds for embedded content.
- The actual Activity-recreation scenario also checks restored Control Panel page, Navigation tab and Back history. An embedded-account check verifies that an unsaved name survives an appearance refresh.
- API 30 produced 116 captures and API 36 produced 117. Canonical Control Panel, Back, retained-window and System/account/startup/clock/advanced taskbar/wallpaper images were visually reviewed. The browser is deliberately open behind Control Panel in these fixtures; root-Back assertions separately verify that it stays hidden until explicitly selected again. API 36 uses View-rendered fallback captures. Physical Pixel 10/Android 17 testing is outside this run.
- 77,325 tile placements and 308,182 snap combinations passed. Existing shell-switch/defaults, gesture, connected-app, browser, Explorer, photo and media scenarios remain included.

The first compile attempt exposed a missing JSON import in the added native test; it was corrected before final validation. Additional visible-content captures and draft checks were included in the final source.

## Delivery

- `Nth-Desktop-Eight-0.7.5.apk`, version code 15, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36; non-debuggable.
- APK SHA-256: `43bb0fb127f35c6dc187b22b227f0add34743023628cad7cc58b288494ab3014`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained identity as delivered 0.6.1 through 0.7.4; install over 0.7.4 without uninstalling to keep layouts and local data.
- All 526 non-signature APK ZIP entries match the passing API 36 artifact byte for byte. Release artifact ZIP SHA-256: `40062be85617d1bc8ed5d8310e36c7cdacc899538b1bc7c0f63ff601cca6b6a7`. Signature Schemes v2/v3 and 16 KB alignment verified; no native libraries.
- Download ZIP SHA-256: `a67eb4860793d03e225baf356b473c2fb43de6331a41b3c10e3b35be546dbc2d`. CRC and contained-file byte comparisons passed. It contains only the signed APK, install notes and public metadata; no private key or test/fixture APK.

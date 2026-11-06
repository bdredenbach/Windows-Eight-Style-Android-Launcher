# Eight 0.4.0 validation

Application/test source: `858297f1f8e1b6c80ea7a4f063f59b24b88227c0`.

Native regression: [GitHub Actions run 42305236484](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42305236484). Both matrix jobs succeeded. Final documentation does not alter application code.

Evidence artifacts: `13545767804` (API 30) and `13545788143` (API 36).

## Native scenarios

| Suite | Coverage | Android 11 / API 30 | Android 16 / API 36 |
|---|---|---|---|
| EightSmoke | Start/Desktop continuity, separate profile layouts, selection/search/Charms, backup, orientation, recreation and Home | Pass | Pass |
| EightGestureSmoke | Actual pointer hold/drag; both edge scroll directions; attached source and same viewport during deferred refresh; saved cross-group drop, unique tile, cancellation; Start/Apps downward swipes and horizontal panning; traditional Control Panel; empty Weather actions; immersive/live-tile provider activity; cached condition and units | Pass | Pass |
| EightAuditSmoke | All 21 desktop deep links, both PC settings hierarchies, live Eight Usage Access branding, Calculator, News, Task Manager, Explorer identities, search focus, cutout padding and optional Update controls | Pass | Pass |
| EightImmersiveSmoke | Same WebView, draft and assistant session; actual HTTP history; original/flexible snap, narrow fallback, independent closing and recreation | Pass | Pass |
| BrowserSmoke | Actual pages/storage/history, tabs, TLS rejection, downloads/upload chooser, fullscreen video/rotation, private isolation and recreation | Pass | Pass |
| ExplorerSmoke | Real providers, navigation, verified file operations and cancellation, menus, linked folders and cross-UID access | Pass | Pass |
| PhotoSmoke | Decoded pixels/EXIF, large/corrupt pictures, zoom/slideshow, sources, recreated state and revoked grants | Pass | Pass |
| MediaSmoke | Real media formats, playback/seek/queue, rendered video, shared views and transfer of the same playing engine between Desktop and immersive | Pass | Pass |

## Build and static/property checks

- Local debug application and test APK builds pass.
- 13 JVM unit tests pass (calculator, AI, accent contrast, Eight destination and command routes).
- Packaged Eight identity, isolated providers, Home intent and eight native runner registrations pass.
- Surface-construction checks pass: no Ten Settings, Task View, notification sidebar, interests flyout or Ten calculator is constructed; the Seven setup screenshot is excluded.
- 77,325 tile placements and 76,944 supported snap combinations pass the existing geometry/profile/minimum-width checks.

## Captures and test method

The gesture runner sends actual finger events through UiAutomation. It observes retained native views during a drag without waiting for an active gesture to become idle. Edge scrolling stops redrawing once the scroll limit is reached. Cancellation may leave no recipient for a synthetic terminal touch-up; the test checks saved state rather than requiring that cancelled event to be handled.

Reviewed captures confirm visible first-run Weather actions, real parsed temperature/condition art, the live tile, Control Panel categories, Personalization theme previews and flat Explorer menus in the final source.

The new captures include dragging in both profiles, the traditional Control Panel, empty Weather, the live Weather tile and the actual Weather forecast view. The forecast fixture passes through the same cache parser, view and provider visibility logic, without depending on external weather availability. No fixture data is shipped in the application's preferences.

API 30 evidence uses compositor captures. API 36 AOSP ATD can return black compositor images; the native runners detect this and explicitly name their laid-out View-tree fallback renders `-view.png`. These are actual native views, not generated mockups. Browser/photo behavioral and decoded/rendered-pixel checks remain separate from compositor capture availability.

The [surface audit](EIGHT_0_4_AUDIT.md) records findings, changed presentations, retained Eight-era desktop behavior and primary references.

## Signed download

- Version 0.4.0, version code 4; `com.nthexperience.desktop.eight`.
- Minimum API 26; compile/target API 36.
- APK SHA-256: `62953380425dd5704f13db6bf4e0366b792770ba7b4c20826c55ae409880683c`.
- ZIP SHA-256: `d8f301020352c4f0f41204eeec187933ec553a2d3f73d8ab8ee3b72d9e569914`.
- Signing certificate SHA-256: `3a7ab565951c7a62fd785c60df3a1f3975ec8447d1307b46e77293a69017c72d`.
- APK v2/v3 signature and zip alignment verification pass. Same signing identity as the previous Eight previews.
- The ZIP contains only the byte-identical APK and install notes. Private signing material remains outside the repository and downloads.

Weather is a network-backed forecast with a real offline cache. City discovery and new forecasts require connectivity and can fail independently of the launcher; visible setup, retry/change-place controls and cache/error status remain available. The UI does not invent current conditions or cloud calendar events. Android app/system settings screens remain Android surfaces.

# Nth Desktop Eight 0.7.2 — precise Maps location

The previous Maps view requested only Coarse location, so Android could offer only Approximate access. It also accepted a cached network/fused result up to five minutes old and never directly subscribed to GPS. Those choices could leave the pin well away from the device's current position.

## Updated behavior

- My location requests Fine and Coarse together. Android 12+ offers Precise/Approximate on a first grant, or an upgrade dialog when Approximate was already allowed. Android 11 uses its normal location dialog. Only an explicit My location/accuracy action asks for permission.
- Existing approximate grants get an upgrade opportunity when Maps first requests its new accuracy choice. Choosing Approximate remains usable; repeated My location taps respect that choice. Location accuracy can ask to upgrade again or open Android app permissions.
- With Precise access, Maps requests GPS plus available fused/network providers. Recent cached fixes are only initial hints; stale fixes are rejected. A rough first result can improve for up to 30 seconds. A fresh fix with estimated accuracy of 50 m or better completes the request and zooms to street level.
- Map reloads are limited while a rough fix improves; small repeated updates do not continuously reload the page.
- Estimated accuracy in metres is displayed separately from map-page loading/error text. Precise permission is distinguished from the quality actually obtained. The timeout path keeps and labels the best available fresh fix.
- Approximate-only access uses available fused/network providers and is labeled Approximate. Denial leaves coordinate search and saved places usable. Disabled device location offers Android Location settings.
- Completion, timeout, hidden/background Maps and closing remove requests/timers. Late callbacks cannot move an inactive or completed map. Returning to Maps does not automatically restart tracking.

The full regression pass also exposed and corrected a People selection race: a queued search refresh could invalidate a device-contact details lookup. Selecting a contact now cancels that pending refresh.

No background-location permission or location service is added. Weather's existing approximate-location action remains unchanged. An estimated accuracy is not a guarantee of an exact physical position; GPS reception and the device's available providers still affect the result.

## How to use it

Update over 0.7.1, open Maps, then tap My location. Choose Precise or Change to precise location when Android offers it. If location remains Approximate, use Location accuracy → Android permissions → Permissions → Location → Use precise location, then return and tap My location again. A denied or restricted permission may require that Settings route.

## Validation and delivery

The release's new native scenario drives the actual Android permission dialogs and uses Android test-provider callbacks for deterministic positions. It covers denial, approximate access and precise upgrade, stale/rough fixes, estimated accuracy, GPS completion/zoom, timeout and lifecycle cleanup. Test providers are confined to emulator validation; physical Pixel 10 GPS performance is not claimed by those fixtures. Final validation and package details are recorded below. One emulator attempt aborted inside its bundled Chromium WebView native library during startup; that failed job was rerun against the unchanged source. A subsequent attempt passed the location, inset, connected-app, search/fidelity and shell scenarios, then exceeded the existing Start drag scenario’s five-minute harness limit; inspection found an unbounded idle wait while the test deliberately held the edge-scrolling drag. That test-only wait was removed; its bounded edge polling and all drag/drop assertions remain. Application code was unchanged by the harness correction.

Primary sources: Android [runtime Precise/Approximate permissions and upgrade](https://developer.android.com/develop/sensors-and-location/location/permissions/runtime), [LocationManager cache/update behavior](https://developer.android.com/reference/android/location/LocationManager), and [testing location workflows](https://developer.android.com/develop/sensors-and-location/location/testing).

## Validated delivery

- [PR #8](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/8); tested source `d10c6c89ce16581daafbdcdd67bd6fb153cd38eb`, based on 0.7.1 main `e47915acec467dd43a46946ae62172e1735bc691`.
- [Successful run 42447515071](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42447515071), attempt 1: 33 unit tests (zero failures/errors/skips) and all fourteen native suites on Android 11/API 30 and Android 16/API 36 against the non-debuggable release.
- Android 16 exercises the real initial Precise/Approximate dialog, an Approximate grant, and the real upgrade to Precise. Android 11 exercises its normal joint-permission dialog. Both use Android provider callbacks with deterministic mock positions and accuracy; a 4 m fixture verifies coordinates, zoom and visible accuracy. No physical GPS accuracy is inferred from that fixture.
- 77,325 tile placements and 308,182 snap combinations passed. Existing layout/gesture, connected-app, browser, Explorer, photo and playback scenarios remain included. The People selection regression passes.
- API 30 produced 101 captures and API 36 produced 102. The new location captures were visually checked. They use view rendering and do not prove all hardware-rendered online map tiles or service availability. Physical Pixel 10/Android 17 GPS performance is outside this test run.
- `Nth-Desktop-Eight-0.7.2.apk`, version code 12, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36.
- APK SHA-256: `7e97638d82930bbb24d6a7d8ef49c49941b40638f97970cc660c03272b251709`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained identity as delivered 0.6.1/0.6.2/0.6.3/0.7.0/0.7.1; update over 0.7.1 without uninstalling.
- All 526 non-signature APK ZIP entries match the tested API 36 release artifact byte for byte. Release artifact ZIP SHA-256: `36ca76f50234219a9561daaa95ab18b3776db4f7295c5e4c0fe399dbe130912b`. Signature Schemes v2/v3 and 16 KB alignment verified; no native libraries.
- Download ZIP SHA-256: `b20371e9bb7f2f983069b161f60364a1575875f9fe99e7255d7cc755d61f3cf0`. CRC and contained-file byte comparisons passed. It contains only the signed APK, install notes and public metadata. No private key or test/fixture APK is included.

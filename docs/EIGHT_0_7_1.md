# Nth Desktop Eight 0.7.1 — workspace spacing

The owner's portrait screenshots showed a wallpaper gap above the left Apps panel and the right Charms panel. The landscape screenshot showed a left edge arrow shifted inward by the camera inset, and desktop shortcuts scattered across columns because the camera blocked whole grid cells.

## Changes

- Apps and Charms panel backgrounds extend to the top of the workspace, including the area behind the camera/status region. Their controls keep safe internal padding.
- Start, PC settings and immersive app contents retain the previous camera-safe bounds through individual page margins.
- Bottom arrows align with the navigation-safe side edges instead of inheriting the camera's side margin. They stay above the taskbar or the current app's bottom controls.
- The desktop icon grid reserves the camera's actual horizontal inset. A left-side camera therefore keeps icons in straight columns beside it. A camera on the right preserves the left-side column alignment.
- Insets are recalculated from Android's current values when the phone turns. Saved icon coordinates and custom Start layouts are not rewritten.

## Validation and scope

The new EightInsetsSmoke scenario performs actual portrait, landscape and reverse-landscape rotations on the release app's Android views. Deterministic WindowInsets/DisplayCutout fixtures reproduce a top camera with the status bar shown/hidden and a side camera on either landscape edge. It checks panel background height, camera-safe controls, bottom arrow edge/taskbar geometry, straight left-camera icon columns, unchanged saved coordinates, and safe Start/settings/immersive content. These are controlled inset fixtures, not a physical Pixel camera test.

The completed validation and delivered package are recorded below. The existing shell, connected-app, browser, Explorer, photo and media scenarios remain enabled. All 0.7.0 features remain included; [their account/service boundaries still apply](EIGHT_0_7.md).

Primary implementation references: Android [display cutouts](https://developer.android.com/develop/ui/views/layout/display-cutout) and [edge-to-edge views](https://developer.android.com/develop/ui/views/layout/edge-to-edge). Backgrounds can reach the display edge while controls avoid system and camera regions.

## Validated delivery

- [PR #7](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/7); tested source `cfc8279d90ed43a805a55d2f0e958d446030ae7f`, based on 0.7.0 main `46fd30461509801a9b42b9143d350411796a8c1e`.
- [Successful run 42443634706](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42443634706): 33 unit tests (zero failures/errors/skips) and all thirteen native suites on Android 11/API 30 and Android 16/API 36 against the non-debuggable release.
- 77,325 tile placements and 308,182 snap combinations passed. Each platform produced 100 captures, including twelve new spacing captures. The twelve API 36 spacing captures were visually reviewed.
- `Nth-Desktop-Eight-0.7.1.apk`, version code 11, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36.
- APK SHA-256: `821bd06c103e3ac9c0f1cedfedf713f430129d9756633c3541ccc0f82c54807d`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained certificate as delivered 0.6.1/0.6.2/0.6.3/0.7.0; install over 0.7.0 without uninstalling.
- All 526 non-signature APK ZIP entries match the tested API 36 release payload byte for byte. APK Signature Schemes v2/v3 and 16 KB ZIP alignment verified; no native libraries.
- Download ZIP SHA-256: `e62ee9ef00086820c7032c2cc90862509946edf00b7ab0372aeae0a9fa02a0ec`. CRC and contained-file byte comparisons passed. It contains only the signed APK, install notes and public package metadata. No private signing material or test/fixture APK is included.

Physical Pixel 10/Android 17 behavior was not exercised here. The twelve spacing captures use Android view rendering and controlled camera inset fixtures; the camera bounds do not represent a newly observed physical camera.

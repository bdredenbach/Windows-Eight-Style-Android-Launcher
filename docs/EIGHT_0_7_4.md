# Nth Desktop Eight 0.7.4 — shell defaults and one refresh

The owner recording `224473.mp4` shows all three Taskbar and Navigation choices enabled in the 8.1 shell. Selecting a different shell now applies its defaults together and refreshes the launcher once.

| Navigation option | Choose Windows 8.1 | Choose Windows 8 |
| --- | --- | --- |
| Go to Desktop when Eight launches | On | Off |
| Show desktop background on Start | On | Off |
| 8.1 Update mouse controls and taskbar apps | On | Off |

The selection writes the profile and these three values in one preferences edit. A guarded Activity recreation then restores owned windows and supported app state. The 8.1 switch finishes on Desktop; returning to 8.0 finishes on the original Start surface. The saved switch marker is used only for that refresh. Normal recreation continues restoring the previous surface.

Tapping the already-selected shell preserves customized options and does not refresh. Individual options remain editable afterward; ordinary launches, returns and refreshes do not reapply their defaults. Existing installations already on 8.1 can switch to 8.0 and back to apply the new 8.1 defaults.

Each shell retains its separate tile layout. Saved local records and notes remain. Wallpaper selection, Aero finish, sounds and browser address-bar preference keep their current values. The [0.7.3 settings colors](EIGHT_0_7_3.md) and [0.7.2 Maps changes](EIGHT_0_7_2.md) remain included.

## Validation

- [PR #10](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/10); tested source `b6491cefacc06d99dd8ba7e18affb712ef6086d7`, based on main `6569d72ddee90fdba4a7d2991e6f9c29a9b063f3`. Merged as `296dd61c4a1cdd464090325926a43b26584abb14` with the same tested tree.
- [Run 42471640005](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42471640005): 33 unit tests, zero failures/errors/skips, and all fourteen native suites passed on Android 11/API 30 (attempt 1) and Android 16/API 36 (attempt 2).
- Real shell radio controls drive actual Activity recreation. The checks compare Activity identity, reject an additional distinct instance, and verify Desktop/Start landing, all three Navigation checkbox values, same-shell no-op, customization through normal recreation, separate Medium/Large tile layouts, notes and restored app sessions. Existing Snap checks wait for the newly visible immersive host to finish layout after recreation.
- API 30 produced 107 captures and API 36 produced 108. Final profile/default screens were visually reviewed on both. API 36 uses View-rendered capture fallbacks. Physical Pixel 10/Android 17 testing is outside this run.
- 77,325 tile placements and 308,182 snap combinations passed.

Earlier harness runs first counted create/resume callbacks for the same Activity as two refreshes, then tried Snap before the recreated hidden host had been measured. Those checks now distinguish Activity identity and await the visible viewport; all original behavior assertions remain. The application code was unchanged by those harness corrections. The Android 16 emulator also hit its bundled Chromium dangling-pointer startup abort during the initial Maps scenario in two attempts; the successful final retry used unchanged source. Android's [Instrumentation implementation](https://android.googlesource.com/platform/frameworks/base/+/HEAD/core/java/android/app/Instrumentation.java) provides the lifecycle monitor behavior used by the corrected check.

## Delivery

- `Nth-Desktop-Eight-0.7.4.apk`, version code 14, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36; non-debuggable.
- APK SHA-256: `be2bedd186285e16e112b51f929d018ba3f5ebb0809ef19a8bff14de18e699da`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained identity as delivered 0.6.1 through 0.7.3; install over 0.7.3 without uninstalling to retain layouts and local data.
- All 526 non-signature APK ZIP entries match the passing API 36 release artifact byte for byte. Release artifact ZIP SHA-256: `b04f1e956ae6fe06c789f3003efec62595f17915a5a10722b817a0abb6e036be`. Signature Schemes v2/v3 and 16 KB alignment verified; no native libraries.
- Download ZIP SHA-256: `56cbcebf64e8a0a9c5208b723c0dfcab75033e6cf670b4eae7290d6a6c301982`. CRC and contained-file byte comparisons passed. The archive contains only the signed APK, install notes and public metadata; no private key or test/fixture APK.

# Eight 0.6.2 — recorded interaction follow-up

Re-reviewed both owner recordings (223514.mp4, 5:54; 223525.mp4, 8:01) and the three 0.6.1 screenshots. The review sampled the recordings in order and examined the browser controls and Start drag sequence more closely. Recordings and personal screenshots are reference material only and are not included in source or app assets.

- In 223525.mp4 around 1:10–2:22, IE uses fine circular outlines with restrained pin/tool marks. The 8.0 controls now use thinner strokes and a slimmer outlined pin while retaining the existing touch areas and functional actions.
- Around 2:47–2:56, moving a Start tile beyond a group or into the gap reveals a vertical translucent insertion strip. Start now exposes those destinations, with temporary scrollable space beyond the final group. A successful drop commits one new named column/group; hovering/cancelling does not mutate the saved layout. Existing groups and tile sizes are retained, and moving a lone source tile removes its emptied source. Both profiles use their own existing layout and backup validation.
- The owner-requested left/right arrows now occupy the bottom corners above the taskbar or the active Start/browser command areas. Neutral translucent backgrounds, white arrows and larger hit areas replace the purple middle-of-screen controls. Insets, keyboard and rotation are respected; elevated drawing order no longer produces a large shadow.
- PC settings uses its white gear glyph on Start, distinct from the traditional Control Panel artwork. The browser home hint now follows the actual address-field position, and its footer uses Nth branding.

## Validation scope

Adds pure model coverage for insertion order, preserved tile identity/size, unique group names, missing/invalid requests, limits and singleton source removal. Native gesture coverage exercises new-column creation at the right edge and between groups, cancellation, backup and rotation in both profiles. Native fidelity coverage checks rendered browser-ring weight, translucent white-arrow controls, their actual actions and clearance above desktop/Start/browser commands, plus the PC settings glyph and address hint. The existing Android 11/16 workflow runs these alongside the shared native suites. [Workflow run 42415856900](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42415856900) passed for final source head `bf5b30d50a86c5e3ebc8fe77cecf2c50832ac50c`: 24 unit tests and all ten native suites passed on both Android 11 / API 30 and Android 16 / API 36. Host checks covered 77,325 tile placements and 203,980 snap combinations. The native browser/arrow captures and vertical new-column previews were visually reviewed. Edge scrolling stays active through trailing-space layout and stops before committing a drop.

## Packaging

Version 0.6.2 / code 8 is intended to be signed with the retained 0.6.1 preview key (certificate SHA-256 f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab) so it can update that installation. No private key or credential is included in the repository.

## Delivered APK

- `Nth-Desktop-Eight-0.6.2.apk`, version code 8. Install as an update over the delivered 0.6.1 APK; no uninstall is needed.
- `Nth-Desktop-Eight-0.6.2-Download.zip` contains the APK, installation notes and public package metadata.
- APK SHA-256: `040c4484b8cf9f9554a88a87c2217fa665d9002c7b47ab1e63ab68f9e8f878fa`
- Signature schemes v2/v3 and ZIP alignment verify; the certificate matches the delivered 0.6.1 APK and every APK ZIP entry matches the tested CI build.
- Uses the retained owner-private `Nth-Desktop-Eight-Private-Signing-Backup-20261107.zip`; private material is absent from the app download and repository.

These checks used emulators; the re-signed package has not been installed on the owner’s Pixel 10. The package remains a preview/debug build.

# Nth Desktop Eight 0.6.3 delivery

Promotion review fixes are merged in [PR #5](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/5). The [full review](EIGHT_0_6_3_REVIEW.md) records implemented features, Android adaptations, remaining historical capabilities, new Microsoft references and test limits.

## Validated source

- Source head: `1136809cde98055ffa517925c99ced3d54619b64`.
- [Successful CI run 42418225262](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42418225262).
- 24 unit tests and all eleven native suites passed on Android 11/API 30 and Android 16/API 36; zero unit failures/errors/skips.
- Non-debuggable release APK checked in the manifest and at runtime; final native captures confirm rapid typing and corrected Charms.
- 77,325 tile packing placements and 203,980 snap combinations passed.

## Delivered package

- `Nth-Desktop-Eight-0.6.3.apk`; package `com.nthexperience.desktop.eight`, version 0.6.3/code 9, min SDK 26, target SDK 36.
- Same retained certificate as the delivered 0.6.1/0.6.2 APKs; this is an update over those versions. No fresh installation is needed. The earlier 0.5.0 signing identity is different.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`.
- APK SHA-256: `4726b3321be9ba781d51037f778749899dd06ab8e5c8345e74b3350f88f5b073`.
- All 526 non-signature ZIP entries match the tested API 36 release build byte for byte.
- APK Signature Schemes v2/v3 and 16 KB ZIP alignment verified. No native libraries are packaged.
- Download ZIP SHA-256: `3683e477dbd9168d9f49728fe84643e2736c1d869f71af55a0fe0009b5ae9a20`.
- Review PDF SHA-256: `d31a128c54fa59ddcb0c09de22a3c4a54673406a021da9789a23125b19ab0b63`.
- Download ZIP CRC and contained-file byte comparisons passed. It contains only the signed APK, install notes, public package metadata and review PDF.

The private signing key stays outside the repository, CI and download archive. Continue using the separately retained Eight signing backup for later updates. The re-signed APK was not installed on the owner's Pixel 10; Android 17/OEM hardware behavior remains outside this emulator run. No complete Windows OS or Microsoft service parity is claimed.

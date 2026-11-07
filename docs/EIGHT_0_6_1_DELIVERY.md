# Nth Desktop Eight 0.6.1 delivered package

The owner-requested 0.6.1 APK was packaged from the validated source `5de30add7fd1d7473b219b5eea920a5897c653f4` and signed with a newly backed-up Eight preview identity. This package requires a fresh installation because the original Eight signing key was unavailable. It cannot update the earlier 0.5.0 installation.

Before uninstalling the old app, export its settings and separately export any wanted recordings, drawings, downloads and other saved content. A settings backup does not contain every app-created file. Regrant permissions and reselect linked resources after restoring. The package does not remove an installed app by itself.

- APK: `Nth-Desktop-Eight-0.6.1-Fresh-Install.apk`
- Download archive: `Nth-Desktop-Eight-0.6.1-Download.zip` (APK, installation notes and package metadata only)
- Package: `com.nthexperience.desktop.eight`; version 0.6.1, code 7; min SDK 26, target SDK 36
- APK SHA-256: `7a683db17fea81f297e0c83112b0986ae210a47d30ee4d983bec29103694447c`
- New certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`
- Earlier 0.5.0 certificate SHA-256: `3a7ab565951c7a62fd785c60df3a1f3975ec8447d1307b46e77293a69017c72d`

## Validation

[Workflow run 42405803426](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42405803426) passed unit tests and all ten native suites on Android 11 / API 30 and Android 16 / API 36. Packaging verified that every APK ZIP entry matches the validated CI build byte-for-byte, with only signing/alignment changed. APK Signature Scheme v2/v3 and ZIP alignment verify. No native libraries are included. The re-signed package has not been installed on the owner's Pixel 10. This is a preview/debug build.

## Future signing

The owner has a separate private `Nth-Desktop-Eight-Private-Signing-Backup-20261107.zip` containing the replacement PKCS12 key and credentials. Keep it private and outside the public repository and CI logs. Future APKs intended to update this 0.6.1 installation must use that key and match the new certificate above. It does not recover the original 0.5.0 identity. Do not silently regenerate a key when creating an update.

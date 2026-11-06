# Eight APK update identity

Delivered Eight APKs use package `com.nthexperience.desktop.eight` and a dedicated RSA signing identity.

Certificate SHA-256: `3a7ab565951c7a62fd785c60df3a1f3975ec8447d1307b46e77293a69017c72d`.

The owner has a private backup named `Nth-Desktop-Eight-Private-Signing-Backup.zip`. Recover that existing identity for every subsequent delivered update; do not generate a replacement key. Signing keys and passwords stay outside this repository. The backup contains the PKCS12 key and its private identity metadata.

GitHub Actions produces ordinary debug-signed test APKs. Before delivering an update, re-sign the validated APK with the dedicated Eight identity, verify it with apksigner, and record the delivered APK checksum. CI test APKs and the delivered update have distinct signing certificates.

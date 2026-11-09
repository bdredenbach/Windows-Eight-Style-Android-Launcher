# Eight APK update identity

Delivered Eight APKs use package `com.nthexperience.desktop.eight` and a dedicated RSA signing identity.

Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`.

The update-compatible private backup for 0.7.12 is named `Nth-Desktop-Eight-Private-Signing-Backup-20261107.zip`. Its certificate has been compared directly with the delivered 0.7.12 APK. Recover that existing identity for every subsequent delivered update; do not generate a replacement key. The earlier undated backup uses a different certificate and cannot update 0.7.12. Signing keys and passwords stay outside this repository. The backup contains the PKCS12 key and its private identity metadata.

GitHub Actions produces ordinary debug-signed test APKs. Before delivering an update, re-sign the validated APK with the dedicated Eight identity, verify it with apksigner, and record the delivered APK checksum. CI test APKs and the delivered update have distinct signing certificates.

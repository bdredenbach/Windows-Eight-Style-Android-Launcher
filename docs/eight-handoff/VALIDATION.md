# Package Validation

Prepared 6 November 2026. These results validate the handoff package, not an Eight application.

- 61 reference files decoded successfully; dimensions and SHA-256 agree with the manifest.
- 61 unique image IDs and 61 CSV records. Every image resolves to a listed publisher source.
- 34 source records; document citation IDs resolve.
- Offline gallery image and local document targets exist. No external asset dependency is required to view the included images.
- All Java filenames in the source adaptation map exist in the pinned Ten checkout.
- All JSON documents parse successfully.
- The 12-page PDF was rendered and visually reviewed. Page bounds, captions, tables and footer placement were checked.
- Ten source checkout remains clean; no Eight repository or implementation was created.
- ZIP CRC and its members' bytes were verified against the packaged files.

The historical research was cross-checked against Microsoft launch material, original guides and the separately labeled 8.1 material. Current Android documentation informs the proposed adaptations. See SOURCES.md for URLs and limitations.

CHECKSUMS.sha256 covers every deliverable in the extracted folder except the checksum file itself. It verifies file integrity, not provenance or a digital signature.

Excluded: complete source books/articles, private user screenshots and recordings, source checkout, user data, APKs, credentials and signing material. Original UI imagery is attributed to Microsoft and is supplied as design reference, not an asset license.

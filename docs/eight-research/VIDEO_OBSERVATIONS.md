# Video review and evidence limits

Research date: 6 November 2026. This ledger distinguishes actual decoded footage, transcript review and inaccessible media. No Windows VM was operated. Public videos were reviewed without signing in or bypassing access controls.

## Visually reviewed footage

### V01 - Microsoft Surface User Interface

Designer-hosted recording, approximately 1:16. Played in the browser and sampled across the timeline at four-second intervals. The hosting page identifies prototype-era work; this is not a definitive screenshot of every shipping Eight control. [V01]

Observed sequence: a horizontal Start surface; content-bearing tiles; a Weather view; left-edge app switching; video beside a compact news pane; pane swapping; Internet Explorer with lower navigation and pinned/frequent content; a split touch keyboard.

**Nth design inference:** Compact apps need deliberate layouts, not scaled full-screen pages. Shared navigation and content transitions matter as much as tile colors. A Browser compact view must retain a useful location/launch path. Do not infer final 8.1 controls from this prototype.

### V02 - The Story of Windows 8, UX Week 2012

Designer-hosted talk, approximately 66:57. Selected sections were played and frame-sampled: **25:27-31:03** and **45:22-47:22**. The relevant transcript was cross-checked; the full talk was not watched. [V02]

Observed: horizontal Start, tile movement while the surface pans, semantic zoom, image-led content, Charms/status, a compact companion and changing tiles.

**Nth design inference:** The viewport must be able to move during rearrangement while the dragged tile remains stable. This supports retaining and testing the 0.4.0 edge-scroll fix. Semantic zoom should operate on groups; snapping should expose a useful alternate composition. Animated tiles should pause during placement to avoid interfering with the target.

## Transcript-only reference

**V03 - 8 traits of great Metro style apps, Build 2011.** Relevant design chapters were read; footage was not reviewed. It is Developer Preview material. It helps frame edge commands, content emphasis and app contracts, but it is not release-specific evidence for 8.1 layout. [V03]

## Unavailable or unverified playback

| Source | Observed access result | Treatment |
|---|---|---|
| V04, original Microsoft First look at Windows 8.1 | Original YouTube upload is private | Not watched; no claims based on inaccessible frames |
| V05, period mirror of the same demonstration | Metadata/seek poster appeared, media did not decode | Not counted as watched; posters are insufficient to establish interaction |
| V06, Microsoft Learn interview with Antoine Leblond | Page accessible; player could not play media | Description only, not footage evidence |
| V07, rehosted Update navigation clip | Browser required sign-in; release attribution not verified | Excluded from release-specific conclusions |

Source links and access notes are in [the source ledger](SOURCES.md). Their presence in the ledger records attempted research, not endorsement or proof of playback.

## How 8.1 was cross-checked

Accessible period Microsoft announcements establish 8.1's Start, personalization, search and multitasking changes, while the 2014 announcement identifies later mouse refinements. [S08][S09] The official July 2013 keynote transcript provides a separate demonstration record. [S25] Microsoft Press and period usage samples were checked for settings and desktop navigation. These references support the release comparison without pretending the unavailable Preview video was watched.

## Visual reference policy

Only selected pages/captions from the accessible PDF samples were inspected, not entire books. The Microsoft Computer figure was visually inspected for caption placement, ribbon context, navigation and drive presentation. Frame samples were used internally to compare motion and surface relationships. Complete recordings, publisher books, article copies and publisher screenshots are not included in the research pack or runtime assets.

The eventual implementation should use our own assets and layouts. Historical reference images guide geometry and behavior; they do not supply an asset license.

<!-- reference links -->
[S08]: https://blogs.windows.com/windowsexperience/2013/05/30/continuing-the-windows-8-vision-with-windows-8-1/
[S09]: https://blogs.windows.com/windowsexperience/2014/04/02/windows-8-1-update-important-refinements-to-the-windows-experience/
[S25]: https://news.microsoft.com/speeches/tami-reller-worldwide-partner-conference-2013-keynote/
[V01]: https://jensenharris.com/videos/surface-ui
[V02]: https://jensenharris.com/videos/ux-week-2012-story-of-windows-8
[V03]: https://jensenharris.com/videos/build-2011-metro-style-apps

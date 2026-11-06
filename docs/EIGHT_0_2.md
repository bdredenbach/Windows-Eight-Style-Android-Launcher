# Eight 0.2.0 — immersive apps, snap and supplied icons

The owner requested the follow-up work and supplied `win8icons.zip`. This pass installs its 17 original icon frames, adds a lifecycle-aware immersive presentation layer, a real owned-app switcher, usable classic/flexible snap, separate 8.1 PC settings and the optional coordinated 2014 Update controls.

## Presentation ownership

`EightImmersive` owns a bounded map of launcher-owned sessions. Each retains its DesktopWindow identity, the original body parent/layout parameters, and one attached pane. Switching or resizing panes changes their visibility and bounds without detaching the body. Docking transfers the same body back. Browser tabs/WebViews and the MediaPlayback engine are shared; Nota presentation transfers suppress close and transient visibility cancellation. A true close still releases the app's ordinary resources.

Desktop wrappers of immersive sessions stay hidden. Default Eight taskbar enumeration omits them; the optional 8.1 Update path uses explicit immersive open/dock/close actions. Task Manager and the left switcher close actual sessions rather than orphaning their bodies. Desktop is one switcher item. Owned previews are bounded and memory-only; InPrivate browser content is excluded.

Activity state serializes stable app IDs, direct body UI state, primary/secondary IDs, divider ratio, recent ordering, focused pane and visibility. Existing desktop geometry and engine serialization remain authoritative. Separate return targets avoid overwriting the PC settings return route when Start is toggled above it. Android Back follows actual browser history before returning to Desktop. Global command controls do not change which snapped pane owns focus. Music volume and brightness remain in the right-side Charms flyout above the current immersive surface.

## Snap

`EightSnapPolicy` is Android-independent. Original Eight admits a 320dp compact companion with a sufficiently wide filled app. 8.1 clamps a movable divider to both app minimum widths. Unsupported/external apps are not eligible. Rotation to a narrow view shows one app while retaining the pair for a later wide view.

## References and artwork

`EightIconAssets` is the runtime mapping for the owner's 17-icon pack. `EIGHT_ICON_PROVENANCE.json` records source and converted-byte checksums. The conversion extracts the existing 256x256 frame without drawing new icon artwork. Nth branding and installed Android app icons remain their own. Original monochrome marks distinguish immersive tiles from desktop folder icons.

## Verification

The build checks horizontal tile packing and snap policies, all existing unit tests, packaged runner identities, the original Eight scenario, a new native immersive/transfer/snap scenario, and shared Browser/Explorer/Photo/Media scenarios on API 30 and 36. Media regression includes transferring a live player between presentations. Final run results and package identity are recorded separately after completion.

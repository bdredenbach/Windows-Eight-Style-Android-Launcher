# Nth Desktop Eight - Acceptance Checklist

This is a proposed test plan, not a claim that Eight has been implemented or tested.

## Identity and isolation

- [ ] Eight has its own applicationId, label, N8 icon, provider authorities and fixture targets.
- [ ] It installs alongside Ten, Seven and Vista without replacing or resetting them.
- [ ] Upgrade between two Eight builds retains settings and content.
- [ ] No N10 branding, Ten version text or private signing material leaks into the new product.
- [ ] Default Home setup and returning from Android permission screens remain understandable.

## Release fidelity

- [ ] Default profile is identified as original Windows 8, not a mixed 8.1/10 design.
- [ ] Start is a whole-screen surface with horizontal group navigation.
- [ ] Only square/wide formats appear in the original profile.
- [ ] All Apps is separate from Start; unpinning does not hide an installed app.
- [ ] The default desktop has no permanent Start button, with discoverable alternate navigation.
- [ ] No Ten taskbar Search, Task View, News ticker or Windows 10 Action Center remains by accident.
- [ ] Default immersive apps do not retain desktop caption/taskbar chrome.
- [ ] Any convenience extension is labeled and does not silently alter the reference profile.

## Start and tiles

- [ ] Mixed square/wide sets pack without overlap at every tested height.
- [ ] Overflow continues horizontally; a group does not become an endless vertical phone feed.
- [ ] Rotation changes geometry without changing logical order or losing items.
- [ ] Tap opens the named destination once.
- [ ] Scroll does not accidentally reorder or select a tile.
- [ ] Selection has a visible marker and exposes the appropriate app bar.
- [ ] Unpin, supported resize, group move and live-update toggle change the intended item.
- [ ] Uninstall opens the real Android confirmation and respects cancellation.
- [ ] Group creation, rename and move preserve members and reject invalid names safely.
- [ ] Semantic zoom works by touch and an alternate control; selecting a group returns to it.
- [ ] Live data never changes the identity under an active drag or selection.
- [ ] Missing/uninstalled apps have a clear recovery path rather than a dead tile.

## Navigation and overlays

- [ ] Desktop tile returns to the existing desktop with window state intact.
- [ ] Start charm toggles back to the prior owned surface.
- [ ] Android Home always produces a safe launcher entry rather than automatically relaunching another app.
- [ ] Back/Escape closes one topmost transient layer before leaving content.
- [ ] Repeated activation toggles or focuses one existing overlay; it never reopens immediately after dismissal.
- [ ] Close, Back, outside tap, rotation and host destruction release observers and callbacks.
- [ ] Search/Charms/selection/switcher do not stack orphaned intercepting layers.
- [ ] Focus returns to a usable control after dismissal.

## Charms, search and commands

- [ ] All five Charms entries reach real supported functions.
- [ ] Settings content changes with the current owned app.
- [ ] Common volume/brightness/network controls use actual Android/Nth state.
- [ ] Search scopes return the appropriate catalog and preserve a meaningful query.
- [ ] App results launch actual apps; settings results navigate to actual settings.
- [ ] File results stay within granted/indexed locations; revocation fails safely.
- [ ] Share sends only the selected content with correct type and temporary read grants.
- [ ] Devices never fabricates printers, monitors or connections.
- [ ] Windows+W means settings search in the Eight mapping, not Ten News.
- [ ] Windows+I opens contextual Settings, not an unrelated global page.
- [ ] Other supported shortcuts and accessibility actions have touch/mouse alternatives.

## Owned apps, switching and snap

- [ ] Left switcher groups Desktop as one surface and can return to it.
- [ ] Desktop taskbar still restores individual owned desktop windows.
- [ ] External Android entries use real icon/title and honest launch behavior.
- [ ] No private external screenshot or force-stop capability is claimed.
- [ ] Drag-to-close can be canceled and does not collide with app-bar reveal.
- [ ] Compact snap uses a readable app-specific layout rather than scaling a desktop screenshot.
- [ ] Unsupported/narrow layouts return gracefully to full-screen switching.
- [ ] Leaving and returning preserve useful content, geometry and selection.
- [ ] Browser sessions, gallery slideshow, media playback and Nota replies obey lifecycle rules.
- [ ] A presentation change does not accidentally close or duplicate an app engine.

## Desktop and Nota regressions

- [ ] Closed detached windows have no running indicator or stale Close command.
- [ ] Minimized attached windows remain restorable and distinct from closed windows.
- [ ] A pinned Nota button reopens after caption or taskbar-menu closure.
- [ ] Nota's normal button has the same bounds as other normal app buttons.
- [ ] App-strip scrolling exposes every running button without moving any deliberately fixed controls.
- [ ] Explorer breadcrumbs, Up, back/forward, safe copy/cut/paste/rename/delete and recycle restore work.
- [ ] Window resize, maximize/restore, desktop snap, previews and Show desktop retain state.
- [ ] Real media formats, browser video/file selection and gallery EXIF/source preservation remain covered.
- [ ] Task Manager labels the scope of actual metrics; unavailable cross-app data is not invented.

## Theme, accessibility and devices

- [ ] Start, desktop frames, taskbar and app controls have separate coherent tokens.
- [ ] Text remains readable across every available accent and app mode.
- [ ] Applying appearance repeatedly does not drift colors or repaint content.
- [ ] Focus, press, hover, selection, disabled and running states are distinguishable.
- [ ] Portrait, landscape, narrow windows, cutouts, IME, large text and reduced motion work.
- [ ] Essential targets remain touchable; navigation does not depend solely on hidden edges.
- [ ] Test gesture navigation and three-button navigation on a real phone.
- [ ] Edge handling respects Android Back and system insets.
- [ ] Dark/light and relevant high-contrast cases are visually reviewed.

## Persistence, privacy and delivery

- [ ] Activity/process recreation restores the correct surface and avoids stale View references.
- [ ] Backups validate before applying; malformed input leaves current state unchanged.
- [ ] Cross-flavor imports are explicit and do not silently discard unsupported data.
- [ ] Nota remains local-first with explicit model preparation, optional free cloud consent and late-reply suppression.
- [ ] Imported or model-generated text never executes commands.
- [ ] No account simulation, counterfeit lock screen or fake Windows update/reset flow is presented as functional.
- [ ] Native assertions and actual screenshots are compared with the reference gallery.
- [ ] Emulators and real-device results are reported separately; unsupported capture/OS features are stated accurately.
- [ ] Release APK identity, version, signing certificate, alignment and payload are verified.
- [ ] ZIP CRC and embedded APK equality are verified; no fixture APKs are included.

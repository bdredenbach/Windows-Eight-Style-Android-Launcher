# Additional apps and the details that make Eight feel cohesive

These proposals extend the earlier modern-app catalog. They distinguish useful additions from existing capabilities and optional Windows-era utilities.

## Additional candidates

The period app catalog already covers Reading List, Reader, Alarms, Sound Recorder, Calculator and the media/Bing families. Microsoft's Help+Tips announcement and the publisher's accessory inventory identify several additional opportunities. [E03][E08]

| Candidate | Historical role | Proposed Nth scope / disposition |
|---|---|---|
| Mobility Center | Period desktop utility shown in the Microsoft training | Strong reuse candidate: a compact grid for actual brightness, volume, battery and rotation controls; show only supported functions [V08] |
| Help+Tips | Included 8.1 guidance app, with contextual learning stickers elsewhere in the shell | High priority: searchable offline tips for Start, Charms, snap, windows, files, privacy and setup; replay a single tip on demand |
| Snipping Tool | Desktop screenshot accessory | Useful later: capture a Nth window/region, annotate, save and share; external-screen capture needs a separate Android projection flow |
| Sticky Notes | Small persistent desktop notes | Reuse existing Nota/note storage after checking its interfaces; a light desktop front end may be more useful than another notes database |
| Character Map | Desktop special-character utility | Small offline addition: searchable characters, font support and copy; report unsupported glyphs accurately |
| Steps Recorder | Desktop troubleshooting accessory | Already present in Nth Action Center; improve discoverability, naming and the review/export path |
| Windows Journal / Math Input Panel | Ink and handwriting-oriented desktop utilities | Lower priority: review Nota's actual ink/math support before duplicating engines; handwriting recognition is its own scoped backend |
| Magnifier / on-screen keyboard | Accessibility utilities | Expose useful Android accessibility/input routes; launcher-only magnification is an explicit adaptation |
| Run / remote-desktop utilities | System/connection tools | An Nth app/file/settings launcher can be useful; command execution or an RDP client needs a separate complete implementation |

These accessories were not all newly introduced in Eight. Some Windows components varied by edition, hardware or optional installation. Presence in a book's catalog is not evidence of identical availability on every device.

## Help+Tips: a small complete app

**Nth proposal:** Organize help by task, with short illustrated steps written for this launcher. A new-area tip is dismissible and does not auto-play a long tour. A user can search "move a tile", "allow usage access", "restore a file" or "resize a window" and jump to the relevant explanation. Keep history of dismissed tips per area/profile, provide Replay this tip, and make the hub available from Charms Help and the app catalog.

On a permission page, explain the concrete next action and restore the same guidance when the user returns. Never mistake a system Back action for a successful grant. Keep contextual help available beside an owned app when space permits; use a clear return route on phones. This adapts the historical help relationship to the already preferred guidance style.

## Snipping Tool: scope before appearance

**Nth proposal:** Start with owned launcher windows/desktop capture because there is already a manual capture path in Steps Recorder. A separate editing surface can offer crop, pen/highlight, undo, PNG save and Android Share. Keep the recorded troubleshooting report and the user's new image as separate objects.

For other apps/device-wide capture, Android's MediaProjection route has explicit session consent and platform lifecycle rules. It is a separate capability, not something usage access or the existing View drawing path already grants. [E13] The first implementation should say exactly which surface it captures and keep cancellation/save state clear.

## Mobility Center and the power-user route

**Nth proposal:** Reuse the existing device-control logic in a small, resizable desktop window. Synchronize it with the taskbar flyout; do not give the two surfaces independent slider values. A Start-button context/power-user menu can link to useful actual destinations such as System, Task Manager, Control Panel, Explorer and Mobility Center. The official footage shows this desktop access pattern. Preserve a visible alternative on touch devices and keep device-owned actions clearly labeled. [V08]

## A coherent visual language

Microsoft's layout guidance describes a shared silhouette and a 20-pixel unit with 5-pixel subdivisions, generous header margins and deliberate group spacing. Those are Windows design references, not Android dp instructions. [E06]

**Nth proposal:** Define shared tokens for large page headings, section headings, body text, command labels and spacing. Keep a clear difference between immersive app headers and desktop captions. Use a small hierarchy of weights and a legally distributable existing font; verify accents, symbols and fallback glyphs. Avoid introducing Windows 10-style settings cards or a mobile hamburger drawer as the default navigation for every Eight app.

Horizontal content should share a stable left alignment and group rhythm. Narrow snapped views need different content composition: a headline list instead of a compressed news wall, a readable date/agenda instead of a shrunken month, a useful transport instead of a tiny media library. Preserve phone text size and reachable commands. The developer archive explicitly warns that Update-era desktop bars can overlap immersive commands. [E06]

## Motion and input: findings from the additional video review

The additional designer-talk samples show the motion-design presentation, a News card layout and a reachability heat-map example; the synchronized transcript explains direct response to touch. These are Windows 8-era design examples, not proof of 2013 8.1 page layouts. [V02]

**Nth inference:** Content can enter/leave with purpose while the user's finger stays attached to the object being moved. Shared alignment and responsive input matter more than adding animation to every control. Do not rebuild the tile grid just to cycle a headline. Preserve the fixed drag source and viewport, stop ornamental animation during placement, and honor reduced motion across the whole shell.

## First useful release groups

| Order | Result worth shipping | Completion evidence |
|---|---|---|
| 1 | Real live Weather/date/News/Photos/Now playing and centered desktop captions | Truthful data, stable drag, per-tile live controls, all supported sizes, motion paused when hidden |
| 2 | Proper settings detail navigation and Help+Tips, then Explorer/Recycle detail | Consistent page ownership/back paths, readable portrait layout, safe file operations |
| 3 | Reading List and PDF Reader | Save a real article from Browser/News; open a real PDF from Explorer; retain reading state |
| 4 | Alarms and Sound Recorder | Real persisted timing/audio, screen-off and recreation tests, recoverable permissions/errors |
| 5 | Richer Photos/Music/Video, Paint/WordPad and selected accessories | Complete import/save/return behavior and no duplicate storage or decorative app shells |

This remains research planning. Large service-based apps such as Mail, Maps, Finance or social aggregation still need current providers and a separate implementation scope. The existence of an attractive historical tile is not a data backend.

<!-- reference links -->
[E03]: https://blogs.windows.com/windowsexperience/2013/11/21/getting-help-and-support-with-windows-8-1/
[E06]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh872191(v=win.10)
[E08]: https://www.oreilly.com/library/view/windows-8-1-the/9781449371777/ch10.html
[E13]: https://developer.android.com/media/grow/media-projection
[V02]: https://jensenharris.com/videos/ux-week-2012-story-of-windows-8

[V08]: https://www.microsoft.com/en-us/download/details.aspx?id=57337

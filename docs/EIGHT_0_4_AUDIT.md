# Eight 0.4.0 — gestures, actionable apps, and desktop audit

This follow-up addresses the owner's recording of Start tile moves, the Weather screenshots, and the remaining desktop/settings presentation issues in 0.3.0. Windows 8 and 8.1 remain options inside one Eight application; Aero remains an independent optional finish.

## Start interaction

Holding a tile selects it without replacing any tile views. Moving the held tile starts Android's native drag. Keeping the pointer in the 56 dp zone at either side pans the existing horizontal viewport, including across more than two columns or groups. A stable insertion marker and a Before/After destination label show where it will land. Empty groups accept a first tile. The destination is calculated against the target group with the dragged item excluded, so same-group forward/backward moves use the correct final index.

The source tile remains attached as a faint placeholder. Appearance, catalog, layout and minute updates defer rebuilding while a drag is in progress. Successful drops save once and then render; cancelled drops leave the saved layout intact. Edge scrolling stops when the pointer leaves or the drag ends. Keyboard, accessibility and command-bar ordering remain available.

A predominantly downward single-finger swipe from Start or All Apps returns to Desktop. Horizontal scrolling, multi-touch zoom, tile holds and active drags are excluded from that gesture. Search retains its own interaction.

## Surface review

| Surface | Finding | 0.4.0 result / retained behavior |
|---|---|---|
| Control Panel shortcut | `@control` still redirected into PC settings | Opens the traditional desktop Control Panel categories. Its title is Control Panel. `@settings` remains PC settings. |
| Personalization | Desktop options presented themes as a vertical list of names and a horizontal settings link strip | Desktop task pane, address breadcrumb, selected task and actual theme thumbnail gallery. Theme previews use the Eight border/taskbar accent; theme actions still apply the real settings. |
| Other desktop options | The same settings strip appeared across taskbar, colors and other pages | Traditional desktop task navigation surrounds the retained working controls. All 21 deep-link destinations remain supported. |
| Control Panel categories | Shared navigation retained Vista glass gradients | White task pane and flat navigation/address/search fields, with category/icon view, search, history and applet links retained. |
| File Explorer / folders | Shared shell chrome still carried glossy navigation; ribbon was expanded by default | Flat navigation, white folder pane, square address/search fields; compact ribbon by default on fresh preferences. An explicitly saved expansion preference is kept. Provider-backed file operations and breadcrumbs are retained. Computer / This PC follows the selected profile. |
| Recycle Bin | Glossy shared chrome and permanently expanded ribbon | Same flat desktop chrome; collapsible ribbon with a green Manage context, enabled restore/empty state retained. Recovery and permanent deletion use the existing verified storage implementation. |
| Browser | Rounded modern address pill and ellipsis Tools glyph; blank/home tab could hide its only address entry | Square address field, Tools gear, visible address entry on an empty/home tab. Original Eight top tabs / lower address and 8.1 lower tabs/address remain. Navigation, private profiles, uploads, downloads and WebView continuity are retained. |
| Weather empty/error state | Location controls hidden until ellipsis; short blue header left a blank white screen | Entire immersive surface is blue, including the camera safe area. Unconfigured Weather exposes city search and approximate-location setup immediately. Places, My location and Refresh are visible without opening the app command strip. Error and offline text remains truthful. |
| Weather provider | Visibility tracking only recognized the desktop wrapper; immersive Weather could deactivate fetching | Visible immersive Weather/snap panes and the enabled Start Weather tile activate the actual provider. The minute tick checks for an expired forecast; provider expiry/retry limits and foreground-only activity still apply. A native regression checks activity in immersive Weather, on Start, and after returning to Desktop. |
| Weather forecast | Text summary without condition art | Large real temperature and condition-based sun/moon/cloud/rain/snow/sleet/fog/thunder art, selected location, update/cache status, and cached hourly forecast values. Places offers search, follow location and units. |
| Weather live tile | Generic app glyph and location label only | Actual cached temperature, condition art and condition/location text when a forecast exists. No fabricated weather before setup. Small 8.1 tiles keep an app glyph. Live tiles can still be disabled. |
| Photos | Empty gallery already supplied Open a picture | Retained visible empty-state entry. Desktop Photo Viewer and immersive Photos share image/rotation/slideshow state; secondary commands use the reserved app bar. |
| Media Player | Existing library/transport commands already visible | Retained working Add files and playback controls, flat immersive mode and the desktop player presentation. Media Center remains an Nth extension. |
| Calendar | Flat calendar already had month navigation and Today | Retained local date/month surface. No fabricated cloud events. |
| Calculator | Eight keypad and functional arithmetic already replaced Ten UI | Retained classic desktop / flat immersive keypad and the same arithmetic and memory model. |
| News | Separate RSS panorama already had feed and refresh actions | Retained actual feed/cache cards and visible actions; no taskbar interests dashboard. |
| Notepad / Nota | Editor and assistant already expose working controls | Retained native editors, text state and Android-local routes. Nota is an Nth extension. |
| Window frames / Task Manager / tray / setup | Eight frames, security Action Center, flat Task Manager tabs and live Eight permission card already present | Reviewed and retained. No Ten Settings, Task View, notification sidebar, colored Ten calculator or Seven setup screenshot is constructed. |

Desktop Photo Viewer, Windows Media Player-style presentation, traditional Control Panel and the Explorer ribbon are appropriate members of the Eight-era desktop family. They are deliberately retained alongside immersive presentations. Historical Java class/preference names remain where they preserve shared engines and backup compatibility; this is not evidence of a visible Ten interface.

## Research basis

The prior complete [0.3 audit](EIGHT_0_3_AUDIT.md) records the wider primary-source review and profile boundaries. This pass revisited the supplied period screenshots and these Microsoft sources:

- [Start screen tiles, groups and live weather (original 2012 article)](https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-start-screen-and-user-interface-part-1/375111).
- [Getting around in Windows 8 (2012)](https://blogs.windows.com/windowsexperience/2012/03/08/getting-around-in-windows-8/): app-specific commands, Settings and Weather Places.
- [Windows 8 File Explorer (original 2012 article)](https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-file-explorer/375135): ribbon, contextual commands and minimized default.
- [Control Panel canonical names](https://learn.microsoft.com/windows/win32/shell/controlpanel-canonical-names): Eight/8.1 desktop applet identities.

The requested downward Start/Apps gesture, persistent Weather actions, touch handles and optional Aero are deliberate Nth adaptations for Android usability. They are not claims that Android reproduces every Windows OS behavior. Weather uses MET Norway and Photon/OpenStreetMap rather than defunct Microsoft Weather services.

## Regression evidence

The new native EightGestureSmoke sends real pointer events to hold, drag, edge-pan, drop and swipe in both profiles. It checks retained source/viewport identity during deferred refresh, persisted single-copy cross-group placement, cancellation, horizontal panning, the Control Panel route, visible first-run Weather actions, and the cache parser's actual live temperature and condition. Existing Eight, immersive, browser, file, photo, media and whole-surface tests remain enabled. Final run links, native capture method and package hashes are in [validation](EIGHT_0_4_VALIDATION.md).

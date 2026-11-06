# Live tiles: historical model and Nth implementation specification

Status: proposed next work; current baseline is Eight 0.4.0. Historical statements reference the source ledger. Data contracts, timing and class names below are Nth proposals.

## Historical behavior worth reproducing

A tile could show useful app content without opening the app. Windows provided fixed template families; static templates held one presentation, and desktop peek templates moved vertically between content areas. 8.1 added large-template variants. This differs from Phone's front/back flip treatment. [S01][S02]

A tile could optionally cycle several notifications. The queue held up to five, and tags let new content replace an older item of the same identity. 8.1 allowed size-specific queue configuration. Queue replacement behavior and visible display order should not be confused: the overview says Windows controlled display ordering, so we should not claim a particular fixed rotation order was universal. [S01][S03]

Badges were independent of the main tile payload. Windows placed them in the lower-right corner, with numeric values up to 99, a 99+ presentation above that, and zero clearing the badge. Status glyphs were another form. Badges were supported across tile sizes. [S04]

Secondary tiles were user-approved deep links to content within an app, not duplicate installations. A city, feed, person or album could have its own entry point. [S05]

## What 0.4.0 actually does

| Capability | Inspected state |
|---|---|
| Real weather data | Present: temperature/condition art from the weather cache; provider active for visible immersive Weather or its live tile |
| Calendar date | Present: local weekday/day label, with a calendar glyph |
| Live preference | One global `eight_live_tiles` preference |
| Animation/queue | No general peek renderer or multi-item queue |
| News tile | Static app representation even though the feed cache is available |
| Photos tile | Static app representation; no selected-photo/slideshow provider |
| Now playing | Playback engine exists; no dedicated live tile provider |
| Badge updates | No Start badge model |
| Per-tile controls | Unpin/resize/reorder/group controls exist; no individual live on/off action |
| Secondary content tiles | No general parent-plus-content model in Eight Start data |
| Update mechanism | A minute runnable can rebuild Start; drag rendering is deferred while moving a tile |

The main opportunity is to reuse real providers while replacing full-layout refreshes with updates to stable tile views. We must preserve the drag-source and viewport identity work from 0.4.0.

## First useful tile providers

| Tile | Proposed content | Existing data / work needed |
|---|---|---|
| Weather | Current forecast, condition art, selected city; wide/large can alternate upcoming periods | Reuse `WeatherClient`; provider expiry and cached status remain authoritative |
| Calendar | Large local day/date; future appointments only after an event backend exists | Current date is ready; `EightCalendar` is currently a month/date view |
| News | Cycle up to five actual cached headline titles | Reuse `FeedClient` cache; skip image presentation unless there is a valid image source |
| Photos | User-selected image or album rotation | Reuse safe image decoding/source grants; explicit selection and bounded thumbnail cache needed |
| Music / Now playing | Track, artist/art when available, paused/playing state | Reuse the actual media engine and metadata; stop showing a track once its session ends |
| Reading List | Recent saved article and locally unread count | New bookmark/read-state repository |
| Alarms | Next enabled alarm or running timer | New reliable timing backend and stored state |
| Sound Recorder | Last saved recording or current recording state | New recorder repository; recording must be real |
| People | Selected contact picture and local content | Optional later provider; import/access decisions needed |
| Android app badge | Notification-derived count or status, if explicitly enabled | Optional notification-listener integration; this is not an app inbox unread count [A03] |

Desktop shortcuts such as Notepad, Control Panel and Recycle Bin should keep a coherent static presentation unless the owner explicitly selects an Nth extension. Original desktop-tile guidance emphasizes app name and recognizable icon identity. [S13]

## Size behavior

| Profile/size | Proposed treatment |
|---|---|
| Eight Medium | One useful data face or image/text peek; keep app identity readable |
| Eight Wide | Headline, weather summary or image with a compact caption |
| 8.1 Small | App icon plus optional badge; no forced miniature headline/forecast text |
| 8.1 Medium | Same compact provider family, adapted to the square |
| 8.1 Wide | Two-area information hierarchy or peek |
| 8.1 Large | Multiple useful rows, richer weather periods or a photo composition when real content exists |

The historical desktop reference dimensions are Medium 150x150 and Wide 310x150; 8.1 Large is 310x310. These describe Windows templates at their reference scale, not phone dp. The archived 71x71 notification templates are explicitly Phone-only and must not be used as desktop Small live-content evidence. [S02][S13]

## Proposed architecture

Separate tile identity/layout from content. Suggested components:

| Component | Responsibility |
|---|---|
| `EightLiveTileRegistry` | Resolves supported built-in providers; unsupported shortcuts remain static |
| `EightTileSnapshot` | Immutable content state, including source time, expiry, stable item tags and destination |
| `EightLiveTileView` | Updates/animates inside a retained tile rectangle; never owns grid packing |
| `EightTileScheduler` | Coordinates visible-tile updates, staggered rotation and lifecycle pause/resume |
| `EightTilePreferences` | Per-tile live on/off, privacy mode and optional provider selection |
| `EightSecondaryTile` | Parent app ID plus validated content key, title, icon and activation destination |

A snapshot should distinguish content from freshness:

```json
{
  "tileId": "@weather",
  "provider": "weather",
  "contentKey": "selected-city",
  "updatedAt": 0,
  "expiresAt": 0,
  "status": "cached",
  "items": [
    {"tag": "current", "headline": "Example structure only", "destination": "@weather"}
  ],
  "badge": null
}
```

The example is a contract shape, not sample weather shipped to users. Providers must supply actual values or a meaningful setup/error/default state.

## Motion, freshness and interaction rules

- Animate only the content area of a tile. Position, size, focus, selected state, drag shadow and hit target remain stable.
- Pause tile animation during drag/selection, semantic zoom, reduced-motion mode and when Start is covered or the launcher is backgrounded.
- A proposed reading interval is 6-10 seconds, staggered across tiles. This is a tuning range for Nth, not a claimed historical Windows timing specification.
- Use a vertical peek transition where appropriate. Photo replacement may use a restrained slide or crossfade. Avoid rotating every tile simultaneously.
- A tap opens the stable tile destination. A secondary tile opens its pinned subview. Do not make the launch destination depend on a headline changing under the user's finger.
- Preserve cache timestamps and expiry. Drop expired headline/event items; retain a truthful cached-forecast indication. Windows supported explicit expiry, rather than requiring old information to remain indefinitely. [S01]
- Failed provider refreshes should not erase a usable cache, restart an animation continuously or trigger layout/scroll jumps.
- Honor global and per-tile live controls. Add "Turn live tile off/on" to the selection bar and, for the Update option, the mouse menu.
- Disabled live content returns to the app's normal icon/name. Notification previews require an independent privacy choice; existing permission to read recent task usage is unrelated.
- Observe provider state directly. A foreground-visible countdown can tick; a weather tile must not make a network request every animation frame.

## Secondary tiles worth adding later

A saved weather city, chosen News feed, photo album, music playlist, Reading List category and linked folder are useful candidates. The activation path should be validated and survive backup/recreation. A pinned city cannot silently change to a new city because the main Weather app's selected place changed.

Current `EightStartData` uses shortcut IDs, so this is a versioned data-model change. Introduce a parent/content key record and migration, preserve existing IDs, reject invalid references on import, and keep each profile's ordering independent. Removing an app's main tile must not delete the user's content. Revoked file access should lead to a reconnect state rather than a crash.

## Acceptance gates

1. Weather and date providers show real values and the selected units/locale.
2. News cycles only real cached entries, replaces items by stable tags, and expires stale entries.
3. Photo grants, corrupt images and empty collections have working recovery states.
4. Now-playing content follows play/pause/stop and queue changes without stale highlighting.
5. Live off is persistent per tile; the global switch remains available.
6. All permitted sizes have deliberate layouts; original Eight never receives Small/Large data.
7. Badges clear correctly and never claim notification count equals unread mail.
8. The same tile view and horizontal viewport remain attached throughout a drag.
9. No provider/animation changes hit targets, launches the wrong app or steals focus.
10. Rotation, process recreation, profile changes and backup/restore keep tile identities and choices.
11. No animations or network refresh loops run merely because an off-screen tile exists.
12. A cold, offline or unconfigured app remains useful and does not display invented content.

<!-- reference links -->
[A03]: https://developer.android.com/reference/android/service/notification/NotificationListenerService
[S01]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh779724(v=win.10)
[S02]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh761491(v=win.10)
[S03]: https://learn.microsoft.com/en-us/uwp/api/windows.ui.notifications.tileupdater.enablenotificationqueue?view=winrt-28000
[S04]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh779719(v=win.10)
[S05]: https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh465372(v=win.10)
[S13]: https://learn.microsoft.com/en-us/windows/win32/shell/desktop-app-tiles-on-the-start-screen

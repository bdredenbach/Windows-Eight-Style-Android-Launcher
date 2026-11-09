# Nth Desktop Eight 0.7.11 — Nth app tiles on Start

Every built-in Nth app and utility now has a discoverable Start tile. The default layout contains 54 tiles in seven groups. Colored immersive app tiles mix with lighter desktop-tool tiles and their native artwork. Tile labels use contrast-checked foregrounds. Windows 8 uses Medium/Wide defaults; Windows 8.1 adds Large Calendar and Photos tiles and a Small Dice Roller tile. Existing resize controls remain available.

| Group | Included apps and tools |
| --- | --- |
| Essentials | Desktop, Nth Nota, Nth Internet Explorer, File Explorer, Calendar, Weather |
| Connect | Mail, People, Maps, News, Reading List |
| Media | Photos, Nth Media Player, Nth Media Center, Reader, Sound Recorder |
| Create | Notepad, Sticky Notes, WordPad, Paint, Character Map, Calculator, Color Studio, Text Counter |
| Plan and play | Alarms, Clock, World Clock, Checklist, Countdown, Stopwatch, Days Until, Dice Roller, Tile Puzzle |
| Utilities | Unit Converter, Compass, Storage Meter, Network Meter, Feed Headlines, Windows Mobility Center |
| Your PC | Computer / This PC, PC settings, Control Panel, Task Manager, Recycle Bin, Backup and Restore, Desktop Organization, Quick Launch, Saved Workspaces, Recent Items, File Managers, Nth Tools, Help+Tips, Run, Nth Command Prompt |

Optional administrative tools still follow their existing visibility setting. Documents, Pictures and Music remain destinations inside File Explorer rather than duplicate app tiles. Installed Android apps remain available in Apps/search and can be pinned individually.

## Defaults, upgrades and restore

- First initialization saves the full default layout separately for both shells and enables live tiles.
- An existing installation adds missing Nth app tiles once, after existing groups. Existing tiles keep their group, order, size and live-off choices. Existing group/tile limits are respected.
- The one-time marker prevents subsequently unpinned tiles from returning on every launch.
- **PC settings > Personalize > Add missing Nth app tiles** appends missing tiles to the current shell on demand; it does not move existing tiles.
- Restoring an older backup preserves its saved Start layout. The restore marks defaults as initialized, preventing an automatic insertion pass on the next launch. The explicit add-missing action remains available afterward. Restore Undo retains the previous layout.

## Live content

Nineteen tiles support live content. New providers read local draft counts, contact/favorite counts, saved-place counts, note word counts, two selected world clocks, checklist progress, configured countdown/stopwatch state and a saved Days Until event. Feed Headlines shares the existing feed cache and expiry policy. Nth Media Center shares the existing playback snapshot with Nth Media Player.

Existing Calendar, Weather, News, Photos, Reading List and device alarm providers remain included. Providers needing a location, selected photos, feed data, a configured timer/event, device alarm or current playback show the normal app face until that data exists. They do not fetch from the animation loop or start playback, recording, location access or permission dialogs. Mail counts local drafts; it does not claim to show unread server mail. Contact, place, note and checklist tiles show counts without exposing their saved bodies.

Individual shell-specific live switches, the global switch and clear-personal-info behavior cover the new providers. Content refresh changes the retained live surface without rebuilding or moving the tile, changing its launch target or disturbing selection/drag behavior. Reduce Motion continues to suppress transitions. Live headings automatically fit the available tile height; detail lines and the fixed app-name caption have reserved space, including compact landscape layouts.

## Validation and delivery

- [PR #17](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/17), tested source `d3e70efe7fcc30632495e5c25830cef4d7ccf568`, tree `b4b7b9667964aa8a737377908e43887a91033605`. Merged as `3bda4a3f498a370542d68c80edb2ad5d834140ee` with the same tested source tree.
- [Run 42632826865](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42632826865): 41 unit tests and all eighteen native suites passed on both Android 11/API 30 and Android 16/API 36; zero failed, errored or skipped unit tests.
- New native coverage verifies all 54 tile click destinations and scrolling in both shells/orientations (216 dispatches per OS), fresh/upgrade/restore/Undo policies, later unpins, real live provider fixtures, individual/global live switches, clear and repopulation, retained geometry and content fitting above the fixed caption. Metadata coverage checks ownership, unique ids, profile sizes, packing, contrast and independent mutable layouts.
- Existing expanded-taskbar stability, shared backups, Task Manager, shell/Nota, location/insets, connected apps, drag/drop, browser, Explorer, photo and media suites passed. Full-layout drag traversal requires continuous progress rather than assuming the old short starter layout. 77,325 tile placements and 308,182 snap combinations passed.
- During development, an API 36 emulator attempt hit a native Chromium WebView crash in the Maps location-permission suite. The final validation run passed that suite and all other scenarios.
- Native captures: 138 on API 30 and 139 on API 36. Start captures in both shells/orientations were visually reviewed. New suite captures use settled native View drawing, avoiding blank headless emulator surfaces.
- Delivered `Nth-Desktop-Eight-0.7.11.apk`, version code 21, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36; non-debuggable. Install over the current Eight app.
- APK SHA-256: `7e654e5399b442ee5530711b40d04b1bbfc537622fbc9cda492148cc5707b0fa`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. The retained Eight update identity, v2/v3 signatures and 16 KB ZIP alignment are verified. All 526 payload ZIP entries, excluding signature records, match the passing API 36 release artifact. Artifact archive SHA-256: `96fb0d0c5f96acfe2aa0b6d146c27ac7a83f14c94167240d1fdd56e3643e7a5c`.

Physical Pixel 10 / Android 17 testing was not performed.

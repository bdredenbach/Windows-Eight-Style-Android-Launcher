# Nth Desktop Eight 0.7.0 — connected apps and collections

This release implements the Calendar, richer media library and Mail/People/Maps follow-up from the 0.6.3 promotion review. Both Eight profiles use real local data and existing retained desktop/immersive sessions. The earlier review remains a record of 0.6.3, rather than a description of this release.

## What you can do

| App | Working behavior | Android/service boundary |
|---|---|---|
| Calendar | Create, edit and delete appointments; timed/all-day dates; location/notes; Day, Week, Month and 90-day Agenda; local event markers and upcoming Calendar live-tile text; export .ics | Optional read-only device Calendar events. Add to device calendar opens a real editable insertion, where the user configures reminders/recurrence. Nth itself does not schedule reminders or sync accounts. |
| People | Local contacts, favorites and search; name/phone/email/address/notes; compose in Mail; find address in Maps; dial or SMS handoff | Device contacts require optional read access. Device editing opens Android Contacts. Explicit Copy to local copies selected fields. No social-network aggregation or cloud account service. |
| Mail | To/Cc/Bcc, subject/body, local drafts, up to ten selected attachments, draft deletion, preferred inbox app and external-browser webmail shortcuts | Android email app owns authentication, inbox and final send confirmation. Nth never labels a handed-off draft as sent or deletes it automatically. This is not an IMAP/Exchange inbox client. |
| Maps | Interactive attributed OpenStreetMap embed; coordinate or device-Geocoder address search; approximate My location; saved places with edit/delete/notes; zoom/retry; directions | Online tiles and address lookup depend on external service/device availability. Directions opens a compatible maps app or site. No offline tiles, turn-by-turn engine or background tracking. Panning does not move the saved pin. |
| Photos | Remember chosen folder albums; nested folders/Up; real bounded thumbnails; retained picture viewer; Back returns to the source album; forget link | Read access is limited to selected folders. Originals are preserved. Reconnect unavailable grants by choosing the folder again. |
| Media Player / Center | Add files or recursively import a folder; real title/artist/album/duration metadata; Music, Artists, Albums, Videos, Recent, Now Playing and Queue; search and Refresh tags | Existing playback engine and Android codecs remain. No streaming subscription/catalog or media-account sync. Missing tags fall back to file/unknown labels. |

Existing customized Start layouts remain intact. Open Apps/search for Mail, People or Maps, then hold to pin. Fresh defaults include the three tiles. Existing Media libraries migrate without losing the selected entries; Refresh tags enriches older metadata.

## Persistence and permissions

Appointments, local contacts, drafts, saved places, album links and media metadata participate in the validated Eight backup. Backups do not contain source media, attachment bytes, Android provider calendars/contacts, passwords, account credentials or transferable Android file grants. Restoring onto another installation can require selecting linked files/folders again.

All-day appointments preserve calendar dates across timezone changes; .ics uses date-only exclusive end dates and folded/escaped UTF-8 text. Timed entries are stored as instants. Mail drafts survive app switching, pausing, session restoration and Activity recreation. Automatic Android hierarchy restoration is disabled for views whose explicit app state owns reconstruction, including PC settings; this fixes a real recreation crash exposed by the new tests. The final visual pass also corrected zero-height landscape Calendar agendas and keeps the selected media collection tab visible.

Device Calendar/Contacts read permissions are requested only for those optional views. The release requests no write Calendar/Contacts permissions. Location is requested when My location is used, then stopped when Maps becomes inactive. Selected Mail attachments are handed to the chosen receiving app with temporary read access only. Webmail opens the installed browser so providers can manage their supported sign-in flow.

Collection bounds are 512 appointments, 500 local contacts, 50 drafts, 200 saved places, 32 album links and 512 media entries. Folder import is bounded to 64 scanned folders, seven levels and available library capacity, with cancellation and a visible limit message. Device contact searches and event queries are bounded; they are not bulk device backups.

## Validation

See [the delivery record](EIGHT_0_7_DELIVERY.md) for the exact passing source, emulator matrix and signed package. The new native suite exercises actual forms and files, separate-UID read-only attachment sharing, Android provider fixtures, thumbnails, tagged media, backup restoration and real Activity recreation. The existing shell/browser/file/photo/playback regression suites remain enabled. Test-only write permissions and provider records exist in the separate emulator fixture APK, never the delivered launcher.

The OSM embed document, pin and attribution loaded during native checks; complete online tile availability is not asserted. AOSP compositor screenshots were black, so the seven new-app captures use view rendering; that cannot validate all hardware-rendered WebView map pixels. Mail/calendar handler differences, physical Pixel 10 behavior and Android 17 are separate from deterministic local/provider fixture coverage. No email is sent by the tests. Full Windows OS/Microsoft service parity is not claimed.

## Primary implementation references

- [Android common intents: Calendar, email, maps and contact actions](https://developer.android.com/guide/components/intents-common)
- [Android Calendar provider](https://developer.android.com/guide/topics/providers/calendar-provider)
- [Android Geocoder](https://developer.android.com/reference/android/location/Geocoder)
- [Google Maps universal directions URLs](https://developers.google.com/maps/documentation/urls/guide#directions-action)
- [OpenStreetMap export/embed](https://wiki.openstreetmap.org/wiki/Export), [tile policy](https://operations.osmfoundation.org/policies/tiles/) and [attribution](https://osmfoundation.org/wiki/Attribution_Guidelines)

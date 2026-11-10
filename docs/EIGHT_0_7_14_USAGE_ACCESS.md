# Eight 0.7.14: Usage Access return recovery

Returning from Android Usage Access without a real grant keeps the current Usage Access step and reopens its guide. Returning from App info does the same. Only an actual permission grant or the user's explicit Later choice continues the ordered flow; Android activity result codes alone do not complete it. Existing focus/early-result guards and unavailable-Settings fallbacks remain.

The illustrated, scrollable guide uses Eight's installed name and icon and exposes Open App info on supported Android versions. It resizes for rotation and safe insets. Eight retains its own Start backdrop, 8.0/8.1 appearance and startup animation. The Welcome Center manual retry uses the same branded example and keeps recovery available on App info return. Its already-granted action opens Eight's taskbar settings.

Existing initialized installations retain their device-local progress. Pre-ordered-flow installations with recorded Home/file setup migrate without repeating those steps. Backup/restore, folder repair, app identity and the retained signing certificate are preserved.

The lifecycle unit tests cover ungranted/repeated return, actual grant versus result codes, App info recovery, recreation, explicit Later, migration and unrelated permission steps. `EightInitializationSmoke` exercises real Android Usage Access and App info screens, ordered/manual recovery, recreation, portrait/landscape guides and an actual granted app-op. Its isolated workflow uses full API 30/default and API 36/google_apis system images and bounded evidence collection. The normal eighteen-suite release regression continues separately.

Adapted from the confirmed Ten 0.28.1 handoff and source commit `0d6a89ec1d2213bddfa46e381b6d5cdaf4cae51a`.

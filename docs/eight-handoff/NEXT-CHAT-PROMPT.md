# Paste into the next project chat

We are starting **Nth Desktop Eight**, a Windows 8-inspired Android launcher.

I have attached the complete research/handoff ZIP. Read 00-START-HERE.md, Nth-Desktop-Eight-Handoff.md, RESEARCH-WINDOWS-8.md, ENGINEERING-PLAN.md and ACCEPTANCE-CHECKLIST.md. Inspect references/index.html and the full-resolution images. SOURCES.md records the primary sources and release boundaries.

The recommended default is **the original Windows 8 from 2012, before 8.1**, with deliberate Android/Nth adaptations. Do not silently mix in Windows 8.1 or Windows 10 features. The package distinguishes researched facts from design proposals.

My new Eight repository is: **[PASTE THE ACTUAL NEW REPOSITORY URL HERE]**.

Use the completed Nth Desktop Ten source:
https://github.com/bdredenbach/Windows-Ten-Style-Android-Launcher

Freeze the starting import at commit **89cd0dd3d22205bbb43b450b3b4a27d14a7fdfcf**, release 0.15.0. Tested implementation source is **94959a673ebd26244c20783c493c9379fc418d9e**. The handoff links the successful full Android 11/16 regression. Keep Ten itself unchanged.

Create a separate Eight identity so both apps coexist. Reuse the working file, media, browser, photo, setup, permission, backup, window-management and Nota engines. Do not restart from an old Seven/Vista baseline or globally rename/refactor working internals without a reason.

Begin with an installable vertical slice:
1. Separate package, N8 branding and correct test/provider identities.
2. Full-screen Start and a Desktop tile that restores the same desktop workspace.
3. Safe Back/Home navigation and explicit surface/overlay state.
4. Horizontal square/wide tile groups, then All Apps, selection app bar and semantic zoom.
5. Charms and scoped/contextual routing.

The larger plan then adds the left switcher, owned immersive hosts, useful compact snap, Eight PC settings and desktop/browser polish.

Preserve the latest fixes: closed cached windows are not running; Nota reopens after closure and uses ordinary button bounds when pinned; taskbar app buttons stay reachable; themes remain readable; real file grants and revocation work; and callbacks/lifecycle are reliable.

Keep Nota useful: unambiguous “open Audible” requests open the actual app, ambiguous results show large bold choices with app icons, paid AI is not added, cloud writing stays optional, and imported/model text never executes commands.

Use small guided hints, not an automatic tour. Do not fake external Android app embedding, screenshots, Windows accounts or operating-system administration. Explain Android destinations only when users need that information.

Proceed with the initial fork and shell spine after the actual repository URL is supplied. Make the first coherent build reviewable before expanding the feature set. Deliver the APK and a ZIP download alternative with accurate validation notes.

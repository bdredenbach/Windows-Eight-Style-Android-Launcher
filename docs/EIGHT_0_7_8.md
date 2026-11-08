# Nth Desktop Eight 0.7.8 — Category instructions in Nota

The owner screenshot `Screenshot_20261108-104051.png` requests a main instruction paragraph for every Nota category, matching the existing Apps & settings guidance.

## Updated behavior

The brief category descriptions remain above the main content. A dedicated guidance view now explains how to use the selected category; the composer hint follows the same selection. Guidance changes from the dropdown, writing chips, New chat, New command and restored task selection.

| Category | Main instructions |
| --- | --- |
| Ask or write | Type a question or describe what you want to write, then press Send. Include the topic, tone and any details you want in the reply. |
| Brainstorm | Type a topic or goal, then press Send for ideas. Include any limits or preferences to help shape the suggestions. |
| Proofread | Paste or type the text you want checked, then press Send. I’ll correct spelling, grammar and punctuation while keeping your wording. |
| Polish | Paste or type the text you want improved, then press Send. Describe the tone or style you’d like, and I’ll make it clearer and smoother. |
| Summarize | Paste or type a passage, then press Send for a shorter version. Mention any length or format you prefer, such as a paragraph or bullet points. |
| Apps & settings | Type open followed by an installed app name, then press Send. One match opens immediately; multiple matches show choices. |

Actual replies use a separate view. A new result focuses the reply; choosing another category reveals its guide while retaining the response below it. Instruction text never populates the reply cache or writing history. Copy reply and Save to Notepad are disabled without a real reply and never fall back to copying or saving guidance. New chat/New command clear the current reply cache, while saved history remains available. Public passage imports also clear the preceding reply cache; private imports retain the prior ordinary cache without persisting private text.

## Validation

- [PR #14](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/pull/14); tested source `2a293f253e3c8b7f24a09c1d13b6c61c78f39c40`, tree `8aeefc0c3102bb4b1f4a7229ec90f1a5b0e3fe97`, based on `91f277b72afbfe7967fa35feba9c5990ebd133d6`. Merged as `2bc310d9cfd4ee1ee25210b90fa058639d28fc32` with the exact tested tree.
- [Run 42497147014](https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/actions/runs/42497147014): 37 unit tests, zero failures/errors/skips, and all fifteen native suites passed on Android 11/API 30 and Android 16/API 36 against the non-debuggable release.
- API 30 passed on attempt 1. API 36 passed on an unchanged attempt-2 rerun after the bundled emulator WebView aborted in Maps (`partition_alloc_support.cc` / `Chrome_IOThread`). The complete final suite passed, including Maps; no checks were skipped or weakened.
- The native Nota scenario preserves a real fixture reply through all six category changes, verifies enabled reply actions alongside guidance, checks the reply cache remains actual data, then verifies instructions-only state hides the answer and disables reply actions. Attempts to save guidance leave Notepad and the reply cache unchanged. Existing app-launch/no-replay and imported/private/generated-text checks remain included.
- All six main instruction paragraphs and empty-composer hints, preserved response presentation and compact landscape layout were visually reviewed. API 30 produced 128 captures; API 36 produced 129. No cloud writing requests or model downloads were needed for these new checks.
- Existing shell/profile/defaults, Control Panel/Back, touch ownership, location/insets, connected-app, gesture, retained-session/snap, research utility, browser, Explorer, photo and media scenarios remain included. 77,325 tile placements and 308,182 supported snap combinations passed.

The added category captures use settled native View rendering with an empty composer so both the instructions and hints can be reviewed without sending writing requests. Physical Pixel 10/Android 17 testing was not performed.

## Delivery

- `Nth-Desktop-Eight-0.7.8.apk`, version code 18, package `com.nthexperience.desktop.eight`, min SDK 26, target SDK 36; non-debuggable.
- APK SHA-256: `3ee05121f0388967674650c410c32029029efdb3ccaba95ff257c5eaab9e5871`.
- Certificate SHA-256: `f47de95e726c9beec0c31f25bf09709bbc5961518f1bb178397e0479a9f136ab`. Same retained identity as delivered 0.6.1 through 0.7.7. Install over 0.7.7 without uninstalling to retain local data and layouts.
- All 526 non-signature APK ZIP entries match the passing API 36 artifact byte for byte. Release artifact archive SHA-256: `bf94124a24f51f22c311198ef9b112b3ec525e7e68cf2bcabe9f659fda3baddc`. Signature v2/v3 and 16 KB alignment verified; no native libraries.
- Download ZIP SHA-256: `f9b7cc3f55d8c68200471105b9cab9d9c7a050f4464cc29676adc04a042b6826`. CRC and exact contained-file byte comparisons passed. It contains the signed APK, install notes and public build verification; no private key or test/fixture APK.

# Decisions and Recommended Defaults

## Already established by the owner

- The next project is Nth Desktop Eight, a Windows 8-inspired Android launcher.
- The finished Nth Desktop Ten is the reuse source.
- The owner will create the new repository.
- The project should remain useful and coherent, with real functions rather than decorative dead ends.
- Carry existing preferences: free AI, understandable Nota actions, small guided tips instead of automatic tours, readable themes, reliable file navigation and dependable window/task state.

## Recommended defaults, not newly confirmed preferences

| Decision | Recommended default | Why |
|---|---|---|
| Historical target | Original Windows 8, before 8.1 | Matches the stated project name and avoids accidental feature mixing |
| Initial shell | Start first | Establishes the distinctive Eight experience |
| Desktop Start button | Hidden corner cue; optional visible N8 accessibility control | Preserves the original profile while keeping an escape route |
| Tile formats | Square and wide | Appropriate starting set |
| Phone navigation | Horizontal group travel with responsive rows | Preserves desktop Windows 8 identity rather than Windows Phone |
| Virtual desktops | Hidden/one desktop by default | Not part of the original Windows 8 shell |
| Ten services | Reuse engines; relocate useful features | Prevents regression and unnecessary deletion |
| Nota | Start tile plus contextual help; optional normal desktop pin | Useful Nth feature without claiming historical assistant chrome |
| Snap | Owned Nth apps only, when a usable compact layout exists | Honest and manageable on Android |
| Gesture fallback | Optional inner-edge handle and explicit alternate commands | Avoids system Back conflicts and hidden-only actions |
| Lock screen | Reference style only; Android security stays authoritative | Avoids a misleading security facade |
| First version | 0.1.0 with separate Eight applicationId | Coexistence and a clear independent release history |
| Import | Eight backups first; explicit Ten conversion later | Avoids silent data loss while the schema changes |

## Genuinely pending input

**New Eight repository URL.** The next implementation chat should use the actual URL provided by the owner. No URL is invented in this handoff.

Other choices can proceed with the defaults above. If the owner specifically prefers 8.1 later, switch the profile coherently: persistent desktop Start button, additional tile formats, revised Search/PC settings and flexible snap need a deliberate coordinated pass.

## Decisions to record during the first build

- Which Nth apps are immersive in the initial slice, and which remain desktop tools?
- One browser engine with two presentations, or separately managed sessions?
- What compact width is actually usable on the supported phone/tablet?
- Which optional accessibility affordances are enabled initially?
- Which period icon assets can be used as runtime artwork, with their actual provenance?

These are implementation decisions, not reasons to block the initial fork and shell spine.

# Explorer, windows and Recycle Bin: usage detail

This section expands the earlier audit into concrete workflows. Historical references are identified; acceptance rules are proposed Nth behavior.

## Desktop window identity

Use the flat Eight desktop finish and a centered caption with collision bounds. Keep a recognizable app icon, separate caption controls and a usable drag region. Explorer may also have a Quick Access Toolbar; that toolbar is different from Windows 10's Quick access location. The original Microsoft File Explorer reference supports the ribbon and context-sensitive tools. [S10]

**Nth proposal:** Retain remembered resize geometry, active/inactive distinction and touch-sized controls. A maximized window restores to its preceding rectangle. Rotation clamps windows to usable bounds without destroying their remembered preferred size. Closing, minimizing and hiding behind Start are separate operations. Use the already working Android app/task limits rather than portraying arbitrary external apps as owned Nth windows.

## Root and folder views

The reference name changes from Computer to This PC. The 8.1 publisher figure shows a Computer ribbon, drive tools and a right-hand details pane; its surrounding text describes local folders alongside drives. [E05]

| Location or selection | Proposed Nth presentation |
|---|---|
| Computer / This PC root | Real internal/removable storage, capacity information and permitted personal/linked folders |
| Ordinary folder | Folder heading, navigation tree, breadcrumbs/search, contents and status summary |
| Selected drive/root | Relevant storage/detail commands rather than ordinary file edit commands |
| Selected image | Picture actions only when implemented, with a clear Open/Preview route |
| Selected music/video | Playback destination and metadata where actually available |
| Search results | Visible scope, cancellable work, result location and a route back to the originating folder |
| Compressed archive | Extraction/open actions only with an actual archive backend |
| Recycle Bin | Recovery-specific metadata, selection and Manage commands |

Drive letters and Windows capacity labels are reference material. Android internal storage must identify the actual accessible volume; linked providers are not automatically writable disks. Keep storage permissions and reconnect actions within the familiar folder layout.

Additional Microsoft footage directly shows This PC with File/Computer/View tabs and an ordinary folder with File/Home/Share/View. The inspected configuration omits a visible Libraries node, so that node should not be treated as mandatory in every view. Cloud availability appears in a provider-specific example; Nth should display offline availability only when its actual backend establishes it. [V08]

## Navigation and selection rules

**Nth acceptance rules:** Back/Forward traverse visited locations. Up chooses the current location's parent. A breadcrumb selects the named ancestor. Typing a path validates and either navigates or explains the specific failure. Opening a linked provider follows its real URI semantics; never invent a parent path.

Preserve sort/view choice and reasonable scroll position when revisiting a folder. When search is active, Back first exits that result context. A canceled picker leaves the preceding location intact. Multiple Explorer windows maintain independent navigation histories while sharing the clipboard intentionally.

Selection should survive a context menu, window resize and supported view changes. Blank-area menus and item menus have different actions. Multi-selection should expose operations valid for the whole selection. The cut state should be visible and clear after completion or cancellation. Failed operations leave a useful error and an intact selection rather than silently disappearing.

## Ribbon and Folder Options

The point of the ribbon is predictable grouping and context. Retain File, the appropriate location tab, Share and View; add contextual tools only for an eligible selection. Avoid presenting identical active command groups at the root, in a normal folder and in the Recycle Bin. [S10]

Folder Options is reachable from View -> Options; the inspected publisher sample describes same/new-window navigation, single/double-click and navigation-pane behavior. [E05]

| Proposed group | Real controls worth supporting |
|---|---|
| Home/file work | Copy/cut/paste, move/copy destination, rename, new folder, recycle/delete, properties and selection |
| Share | Actual Android Share, supported export/print and archive operations |
| View | Icon/details layouts, sort/group, navigation/details/preview panes, extensions and hidden-item presentation |
| Folder Options / General | Click/open behavior and navigation-tree choices |
| Folder Options / View | Defaults for supported views and name/thumbnail visibility |
| Folder Options / Search | Search scope and available indexed/non-indexed behavior; no unsupported Windows indexing switches |

A disabled command must look disabled. An unavailable integration can explain its prerequisite, but should not masquerade as a successful action. Apply folder preferences to the intended template/location rather than unexpectedly resetting every open Explorer window.

## Open, Save and Properties dialogs

**Nth proposal:** Reuse the folder-navigation model in owned Open/Save flows: meaningful location, breadcrumb/up navigation, filename, supported type and clear confirm/cancel actions. State whether a save creates a new file or replaces an existing one. Reconcile the displayed extension with the actual encoder. Keep the document's dirty state until the bytes are successfully written.

Persist file access only when the provider permits it. After a revoked grant, offer reconnect or Save a copy. Canceling an overwrite leaves the existing file intact. Properties report actual type, size, location and known timestamps; omit or qualify fields the provider cannot supply. An Android system picker can still be the permission-grant step without replacing every owned file-management surface.

## Recycle Bin: commands and recovery states

The publisher's period reference shows Manage commands for emptying, properties and restoring all/selected items. It also explains that a deleted folder is restored as a folder, rather than browsed to recover arbitrary descendants. [E05]

**Nth proposal:** Show Name, Original location, Date deleted, Size and Type, with a readable narrow-screen layout. Match the displayed details to the recovery record. A selected row exposes Restore, Delete permanently and Properties; blank space exposes suitable whole-bin commands. An empty bin has no active Restore-all action. Empty and full desktop icons should reflect actual bin contents.

| State/action | Expected Nth result |
|---|---|
| Delete from Explorer | Recovery entry is considered complete only after the recovery copy and original-removal outcome are known |
| Original still exists | Describe the recovery copy truthfully; avoid claiming a move/deletion that did not happen |
| Restore with writable original destination | Restore safely; remove the recovery entry only after success |
| Restore name collision | Offer a clear choice such as keep both, replace or cancel; preserve data on cancellation/failure |
| Missing or revoked destination | Explain the missing access; reconnect or choose a supported alternative |
| Restore selected folder | Recover the recorded tree coherently with progress and partial-error reporting |
| Permanent delete / Empty | Explain irreversibility and the affected selection; keep unrelated entries |
| No selection / empty bin | Appropriate commands disable; show useful status and no fictional recoverable count |

The historical per-drive quota/immediate-delete properties are useful research references. They should not become an inaccurate Windows drive policy on Android. A launcher-owned retention or storage limit would be an explicit Nth feature with working enforcement and a clear name.

## Copy, move and recovery presentation

Windows Eight's richer copy presentation and pause control are documented in the existing ledger. [S24] **Nth proposal:** Show the real source/destination, completed items/bytes, elapsed state and cancellation outcome. Draw speed/remaining-time graphics only from measured data. Do not enable Pause if the provider cannot suspend/resume the operation safely. A failed move must not erase a successful recovery copy.

## Visual and functional acceptance set

Use original Eight and 8.1, portrait and landscape, small and large text, active and inactive windows, real and revoked folders, no selection and multi-selection, empty and populated Recycle Bin, slow/canceled copy, and colliding restore. Check app reopening and process recreation as well as screenshots. Historical appearance is successful only when the file operation and return path remain dependable.

<!-- reference links -->
[E05]: https://ptgmedia.pearsoncmg.com/images/9780789752284/samplepages/078975228X.pdf
[S10]: https://techcommunity.microsoft.com/blog/askperf/windows-8--windows-server-2012-file-explorer/375135
[S24]: https://blogs.windows.com/windowsexperience/2012/12/06/eight-enthusiast-updates-in-windows-8/

[V08]: https://www.microsoft.com/en-us/download/details.aspx?id=57337

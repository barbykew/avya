# Patches for `core`

The Discord Rich Presence lives in the `core` submodule, not in this repository, so the rebrand
to Avya could not reach it from here. `core-discord-rpc-avya.patch` carries that change, ready
to apply once `core` is forked.

## Why it matters

As shipped, `core/service/kizzy/src/commonMain/kotlin/com/my/kizzy/DiscordRPC.kt` publishes a
Discord status that advertises a different project:

- the activity name is `SimpMusic`, so the status reads *Listening to SimpMusic*
- the two buttons say **Listen on SimpMusic** and **Visit SimpMusic**, linking to
  `simpmusic.org` and `github.com/maxrave-dev/SimpMusic`
- the small image is an Appwrite-hosted asset belonging to that project
- `APPLICATION_ID` is `1271273225120125040`, which the Discord API reports as the application
  **"InnerTune" — "A Material 3 YouTube Music client for Android"**: a third project's
  application id, neither SimpMusic's nor ours

Everyone sharing a server with the user sees that.

## What the patch changes

| | before | after |
|---|---|---|
| `APPLICATION_ID` | `1271273225120125040` (InnerTune) | `1555687238300598432` |
| `APP_NAME` | `SimpMusic` | `Avya` |
| `APP_ICON` | Appwrite URL | application asset `avya` |
| button 1 | Listen on SimpMusic → simpmusic.org | Listen on YouTube Music → the playing track |
| button 2 | Visit SimpMusic → upstream repo | Get Avya → this repo |

It also adds `RpcImage.AppAsset`. This is needed rather than the existing `RpcImage.DiscordImage`,
which unconditionally prefixes `mp:` — correct for the CDN attachment paths it is used for
(`Ext.kt` only routes `attachments…` strings to it), but wrong for an application asset name,
which Discord will not resolve through the media proxy. `AppAsset` passes the name through
untouched. The change is additive, so no existing caller behaves differently.

Note that `applicationId` is only transmitted when the activity has buttons
(`KizzyRPC.kt`: `applicationId.takeIf { !buttons.isNullOrEmpty() }`). The patch keeps two
buttons, so the id is sent and the asset resolves.

## Applying it

1. Fork `maxrave-dev/core`, push it under your account, and repoint `.gitmodules` at your fork.
   (Needed regardless — see below.)
2. `git -C core apply ../patches/core-discord-rpc-avya.patch`
3. Commit and push `core`, then commit the updated submodule pointer here.

The Discord application `1555687238300598432` is currently named **"Avyaaaa <3"** in the
developer portal. The status text comes from `APP_NAME` and will read *Listening to Avya*, but
the portal name is what Discord shows in places that name the application itself, so rename it
there if you want those to match.

## Separately: this repository does not currently build

`core` is pinned to upstream `maxrave-dev/core`, which does not contain
`SpotifyImportProgress`, `SpotifyImportRepository` or `DataStoreManager.jennyWelcomeSeen` — all
of which the Spotify import and the welcome screen call. Those additions exist only on the
machine the last release was built on and were never pushed. Forking `core` and pushing the real
version fixes this too.

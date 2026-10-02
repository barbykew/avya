# Patches for `core`

The Discord Rich Presence lives in the `core` submodule, not in this repository, so the
rebrand to Avya could not reach it from here. `core-discord-rpc-avya.patch` carries that
change, ready to apply once `core` is forked.

## Why it matters

As shipped, `core/service/kizzy/src/commonMain/kotlin/com/my/kizzy/DiscordRPC.kt` publishes a
Discord status that advertises a different project:

- the activity name is `SimpMusic`, so the status reads *Listening to SimpMusic*
- the two buttons on the status say **Listen on SimpMusic** and **Visit SimpMusic**, linking to
  `simpmusic.org` and `github.com/maxrave-dev/SimpMusic`
- the small image is an Appwrite-hosted asset belonging to that project
- `APPLICATION_ID` is `1271273225120125040`, which the Discord API reports as the application
  **"InnerTune" — "A Material 3 YouTube Music client for Android"**: a third project's
  application id, not SimpMusic's and not ours

Everyone the user shares a server with sees that status.

## Applying it

1. Fork `maxrave-dev/core`, push it under your account, and repoint `.gitmodules` at your fork.
   (You need to do this regardless — see the note about the build below.)
2. `git -C core apply ../patches/core-discord-rpc-avya.patch`
3. Create your own application at <https://discord.com/developers/applications>, name it
   **Avya**, and replace `REPLACE_WITH_YOUR_DISCORD_APPLICATION_ID` with its id. Without this
   the presence still runs under someone else's application.
4. Commit and push `core`, then commit the updated submodule pointer here.

The patch also points `APP_ICON` at `composeApp/icon/avya_app_icon.png` as served by
raw.githubusercontent.com, so the small image needs no separate hosting, and swaps the first
button to a working `music.youtube.com` link for the track that is playing.

## Separately: this repository does not currently build

`core` is pinned to upstream `maxrave-dev/core`, which does not contain
`SpotifyImportProgress`, `SpotifyImportRepository` or `DataStoreManager.jennyWelcomeSeen` —
all of which the Spotify import and the welcome screen call. Those additions exist only on the
original author's machine and were never pushed. Forking `core` and pushing the real version is
the fix for this too.

<div align="center">

<img src="composeApp/icon/avya_app_icon.png" width="140" alt="Avya" />

# Avya

*a pastel music player for phone and PC*

**[⬇ Download for phone & PC](https://github.com/barbykew/avya/releases/latest)**

</div>

---

## What it is

Avya streams from YouTube Music with no ads and no tracking. It has a pastel theme with ten
colours, Discord Rich Presence, and an in-app Spotify playlist importer, and it runs on both
Android and Windows.

The first time you open it, a setup screen walks you through signing in to YouTube Music, Spotify
and Discord. After that it's just the music.

## Getting it

| | |
|---|---|
| **Phone** | `Avya-…-arm64.apk` from [Releases](https://github.com/barbykew/avya/releases/latest). Open it and allow installing from that app. |
| **PC** | `Avya-…-Windows-Setup.exe`. If Windows says it *protected your PC*, click **More info → Run anyway**. |

## What it does

- **Pastel everything.** Ten colours — rose, cotton candy, lavender, periwinkle, sky, mint,
  pistachio, butter, peach and clay — and the whole app follows the one you pick.
- **A welcome on first open.** Signing in to YouTube Music, Spotify and Discord happens in one
  place, and each one gets a tick when it's done.
- **Spotify playlists, brought over.** Use the **Import** button in Library. If you've already
  copied a playlist link, it's filled in for you.
- **Discord Rich Presence** shows what you're playing.
- No ads, background play, synced lyrics, an equalizer, crossfade and offline listening.

<details>
<summary><b>How the Spotify import picks songs</b></summary>

<br>

Each track is matched against YouTube Music by **running time** rather than by title, because a
title can't tell a studio recording apart from a live take, a sped-up edit or a remix, and all of
those often rank above the original in search. Anything more than six seconds off from the Spotify
duration is rejected, so a song is left out rather than replaced with the wrong one. The ones that
couldn't be found are counted and shown when the import finishes. YouTube Music doesn't have
everything Spotify has, so a few gaps are normal.

Nothing is saved until the whole playlist has been matched, so cancelling halfway leaves nothing
half-built behind.

</details>

<details>
<summary><b>Building it yourself</b></summary>

<br>

Needs JDK 21 and the Android SDK (`compileSdk 37`, listed by the SDK manager as
`platforms;android-37.0`). Put your SDK path in `local.properties` as `sdk.dir=...`, then:

```bash
./gradlew :androidApp:assembleRelease
```

For the Windows installer, build the app image and wrap it with [Inno Setup](https://jrsoftware.org/isinfo.php):

```bash
./gradlew :desktopApp:createDistributable
ISCC.exe installer/avya.iss
```

`core/` is a git submodule pointing at [barbykew/avya-core](https://github.com/barbykew/avya-core)
(branch `avya`), a fork of [maxrave-dev/core](https://github.com/maxrave-dev/core). Clone with
`--recurse-submodules`, or run `git submodule update --init` afterwards.

> **Known gap:** the pinned `core` does not yet define `SpotifyImportProgress`,
> `SpotifyImportRepository` or `DataStoreManager.jennyWelcomeSeen`, which the Spotify import and
> the welcome screen call, so `:androidApp:assembleDebug` currently fails with 45 unresolved
> references across four files. Those additions were made locally against an earlier checkout and
> never pushed; they need committing to `avya-core` before a release can be built. Everything
> else in the project compiles.

</details>

---

<div align="center">

<sub>

Avya is a derivative of [SimpMusic](https://github.com/maxrave-dev/SimpMusic) by
[maxrave-dev](https://github.com/maxrave-dev), and is licensed **GPL-3.0** like SimpMusic.
The playback engine, lyrics and most of what makes it a music player are their work.
Lyrics are supplied in part by the SimpMusic Lyrics community database.
See [LICENSE](LICENSE).

</sub>

</div>

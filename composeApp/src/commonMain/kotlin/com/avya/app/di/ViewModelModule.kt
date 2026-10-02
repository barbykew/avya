package com.avya.app.di

import com.avya.app.viewModel.AlbumViewModel
import com.avya.app.utils.VersionManager
import com.avya.app.viewModel.AnalyticsViewModel
import com.avya.app.viewModel.ListenTogetherSettingsViewModel
import com.avya.app.viewModel.ListenTogetherViewModel
import com.avya.app.viewModel.ArtistViewModel
import com.avya.app.viewModel.HomeViewModel
import com.avya.app.viewModel.ImportViewModel
import com.avya.app.viewModel.SpotifyImportViewModel
import com.avya.app.viewModel.LibraryDynamicPlaylistViewModel
import com.avya.app.viewModel.LibraryViewModel
import com.avya.app.viewModel.LocalPlaylistViewModel
import com.avya.app.viewModel.LogInViewModel
import com.avya.app.viewModel.MoodViewModel
import com.avya.app.viewModel.MoreAlbumsViewModel
import com.avya.app.viewModel.NotificationViewModel
import com.avya.app.viewModel.NowPlayingBottomSheetViewModel
import com.avya.app.viewModel.PlaylistViewModel
import com.avya.app.viewModel.PodcastViewModel
import com.avya.app.viewModel.RecentlySongsViewModel
import com.avya.app.viewModel.SearchViewModel
import com.avya.app.viewModel.AutoEqViewModel
import com.avya.app.viewModel.SettingsViewModel
import com.avya.app.viewModel.SharedViewModel
import com.avya.app.viewModel.SongSelectionViewModel
import com.avya.app.viewModel.WrappedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        single {
            SharedViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        single {
            SearchViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            SongSelectionViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            NowPlayingBottomSheetViewModel(
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LibraryViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LibraryDynamicPlaylistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            ImportViewModel(
                get(),
            )
        }
        viewModel {
            SpotifyImportViewModel(
                get(),
            )
        }
        viewModel {
            AlbumViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            HomeViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            AutoEqViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            SettingsViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            ArtistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            PlaylistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LogInViewModel(
                get(),
            )
        }
        viewModel {
            PodcastViewModel(
                get(),
            )
        }
        viewModel {
            MoreAlbumsViewModel(
                get(),
            )
        }
        viewModel {
            RecentlySongsViewModel(
                get(),
            )
        }
        viewModel {
            LocalPlaylistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            NotificationViewModel(
                get(),
            )
        }
        viewModel {
            MoodViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            AnalyticsViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            WrappedViewModel(
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            ListenTogetherSettingsViewModel(get())
        }
        viewModel {
            ListenTogetherViewModel(
                repository = get(),
                dataStore = get(),
                bridge = get(),
            )
        }

    }
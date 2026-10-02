package com.avya.app.ui.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.avya.app.expect.ui.PlatformBackdrop
import com.avya.app.ui.icon.AutoGraph
import com.avya.app.ui.icon.Home
import com.avya.app.ui.icon.LibraryMusic
import com.avya.app.ui.icon.Search
import com.avya.app.ui.icon.Sensors
import com.avya.app.ui.icon.AvyaIcons
import com.avya.app.ui.navigation.destination.home.AnalyticsDestination
import com.avya.app.ui.navigation.destination.home.HomeDestination
import com.avya.app.ui.navigation.destination.library.LibraryDestination
import com.avya.app.ui.navigation.destination.library.MixForYouDestination
import com.avya.app.ui.navigation.destination.search.SearchDestination
import com.avya.app.viewModel.SharedViewModel
import org.jetbrains.compose.resources.StringResource
import com.avya.app.resources.Res
import com.avya.app.resources.analytics
import com.avya.app.resources.home
import com.avya.app.resources.library
import com.avya.app.resources.mix
import com.avya.app.resources.search
import kotlin.reflect.KClass

@Composable
expect fun LiquidGlassAppBottomNavigationBar(
    startDestination: Any = HomeDestination,
    navController: NavController,
    backdrop: PlatformBackdrop,
    viewModel: SharedViewModel,
    isScrolledToTop: Boolean = false,
    showAnalyticsTab: Boolean = false,
    showMixForYouTab: Boolean = false,
    onOpenNowPlaying: () -> Unit = {},
    reloadDestinationIfNeeded: (KClass<*>) -> Unit = { _ -> },
)

sealed class BottomNavScreen(
    val ordinal: Int,
    val destination: Any,
    val title: StringResource,
    val icon: @Composable () -> Unit,
) {
    data object Home : BottomNavScreen(
        ordinal = 0,
        destination = HomeDestination,
        title = Res.string.home,
        icon = {
            Icon(
                AvyaIcons.Home,
                contentDescription = null,
            )
        },
    )

    data object Search : BottomNavScreen(
        ordinal = 1,
        destination = SearchDestination,
        title = Res.string.search,
        icon = {
            Icon(
                AvyaIcons.Search,
                contentDescription = null,
            )
        },
    )

    data object Library : BottomNavScreen(
        ordinal = 2,
        destination = LibraryDestination,
        title = Res.string.library,
        icon = {
            Icon(
                imageVector = AvyaIcons.LibraryMusic,
                contentDescription = null,
            )
        },
    )

    // Only shown when local tracking is enabled.
    data object Analytics : BottomNavScreen(
        ordinal = 3,
        destination = AnalyticsDestination,
        title = Res.string.analytics,
        icon = {
            Icon(
                imageVector = AvyaIcons.AutoGraph,
                contentDescription = null,
            )
        },
    )

    // Only shown while signed in to YouTube — an anonymous session gets no mixes.
    // Labelled "Mix", not "Mix for you": the full title is the widest label in the bar and forces
    // every tab to be that wide. The screen itself still uses the full title.
    data object MixForYou : BottomNavScreen(
        ordinal = 4,
        destination = MixForYouDestination,
        title = Res.string.mix,
        icon = {
            Icon(
                imageVector = AvyaIcons.Sensors,
                contentDescription = null,
            )
        },
    )
}
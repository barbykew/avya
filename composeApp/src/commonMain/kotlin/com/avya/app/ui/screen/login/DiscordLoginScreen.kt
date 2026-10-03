package com.avya.app.ui.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.avya.app.expect.ui.DiscordWebView
import com.avya.app.expect.ui.rememberWebViewState
import com.avya.app.extension.getStringBlocking
import com.avya.app.ui.component.DevLogInBottomSheet
import com.avya.app.ui.component.DevLogInType
import com.avya.app.ui.component.RippleIconButton
import com.avya.app.ui.icon.ArrowBackIosNew
import com.avya.app.ui.icon.LogoDev
import com.avya.app.ui.icon.AvyaIcons
import com.avya.app.ui.theme.typo
import com.avya.app.viewModel.LogInViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import com.avya.app.resources.Res
import com.avya.app.resources.cancel
import com.avya.app.resources.discord_token_warning_body
import com.avya.app.resources.discord_token_warning_continue
import com.avya.app.resources.discord_token_warning_title
import com.avya.app.resources.log_in_to_discord
import com.avya.app.resources.login_success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscordLoginScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: LogInViewModel = koinInject(),
    hideBottomNavigation: () -> Unit,
    showBottomNavigation: () -> Unit,
) {
    var devLoginSheet by rememberSaveable {
        mutableStateOf(false)
    }
    // Hide bottom navigation when entering this screen
    LaunchedEffect(Unit) {
        hideBottomNavigation()
    }

    // Show bottom navigation when leaving this screen
    DisposableEffect(Unit) {
        onDispose {
            showBottomNavigation()
        }
    }

    // Gated behind an explicit acknowledgement. This flow keeps the user's Discord account token
    // and later connects to Discord as them, which Discord's terms forbid and its abuse detection
    // reads as a stolen account — accounts have been disabled for it. That is not something to
    // find out afterwards, so the webview does not load until this has been read.
    var warningAcknowledged by rememberSaveable { mutableStateOf(false) }
    if (!warningAcknowledged) {
        AlertDialog(
            onDismissRequest = { navController.navigateUp() },
            title = { Text(text = stringResource(Res.string.discord_token_warning_title), style = typo().titleSmall) },
            text = { Text(text = stringResource(Res.string.discord_token_warning_body), style = typo().bodyMedium) },
            confirmButton = {
                TextButton(onClick = { warningAcknowledged = true }) {
                    Text(text = stringResource(Res.string.discord_token_warning_continue))
                }
            },
            dismissButton = {
                TextButton(onClick = { navController.navigateUp() }) {
                    Text(text = stringResource(Res.string.cancel))
                }
            },
        )
        return
    }

    val state = rememberWebViewState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(
                Modifier
                    .size(
                        innerPadding.calculateTopPadding() + 64.dp,
                    ),
            )
            // WebView for Discord login
            DiscordWebView(
                state,
                aboveContent = {
                    if (devLoginSheet) {
                        DevLogInBottomSheet(
                            onDismiss = {
                                devLoginSheet = false
                            },
                            onDone = { token ->
                                devLoginSheet = false
                                viewModel.saveDiscordToken(token)
                                viewModel.makeToast(getStringBlocking(Res.string.login_success))
                                navController.navigateUp()
                            },
                            type = DevLogInType.Discord,
                        )
                    }
                }
            ) { token ->
                viewModel.saveDiscordToken(token)
                viewModel.makeToast(getStringBlocking(Res.string.login_success))
                navController.navigateUp()
            }
        }
        TopAppBar(
            modifier =
                Modifier
                    .align(Alignment.TopCenter),
            title = {
                Text(
                    text = stringResource(Res.string.log_in_to_discord),
                    style = typo().titleMedium,
                )
            },
            navigationIcon = {
                Box(Modifier.padding(horizontal = 5.dp)) {
                    RippleIconButton(
                        AvyaIcons.ArrowBackIosNew,
                        Modifier.size(32.dp),
                        true,
                    ) {
                        navController.navigateUp()
                    }
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        devLoginSheet = true
                    },
                ) {
                    Icon(
                        AvyaIcons.LogoDev,
                        "Developer Mode",
                    )
                }
            },
            colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
        )
    }
}
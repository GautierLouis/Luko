package xyz.luko.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.app.main.MainViewModel.UiState
import xyz.luko.dictionary.home.DictionaryScreen
import xyz.luko.feed.FeedScreen
import xyz.luko.home.HomeScreen
import xyz.luko.profile.ProfileScreen
import xyz.luko.ui.core.window.rememberIsWiderThanTall
import xyz.luko.ui.designsystem.preview.PreviewScreen
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun MainScaffold(viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScaffold(
        state = state,
        screenContent = { paddingValues ->
            when (state.selectedItem) {
                MenuItem.Home -> HomeScreen(paddingValues)
                MenuItem.Dictionary -> DictionaryScreen(paddingValues)
                MenuItem.Feed -> FeedScreen()
                MenuItem.Profile -> ProfileScreen()
                MenuItem.Session -> { /*Main Action*/
                }
            }
        },
        onEvent = { event -> viewModel.onEventReceived(event) },
    )
}

@Composable
private fun MainScaffold(
    state: UiState,
    screenContent: @Composable (PaddingValues) -> Unit = {},
    onEvent: (MainScaffoldEvent) -> Unit = {},
) {

    val isWider = rememberIsWiderThanTall()
    val layoutDirection = LocalLayoutDirection.current
    val systemInsets = WindowInsets.navigationBars.asPaddingValues()

    val thickness = if (isWider) {
        96.dp + systemInsets.calculateStartPadding(layoutDirection)
    } else {
        64.dp + systemInsets.calculateBottomPadding()
    }

    Box {
        if (isWider) {
            screenContent(PaddingValues(start = thickness))
            NavigationRail(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight()
                    .width(thickness)
            ) {
                state.menuItems.forEach { item ->
                    StartMenuItem(
                        item = item,
                        modifier = Modifier.weight(1f),
                        selected = item == state.selectedItem,
                        onItemClick = { onEvent(MainScaffoldEvent.OnItemClick(it)) })
                }
            }
        } else {
            screenContent(PaddingValues(bottom = thickness))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(thickness)
                    .background(Theme.materialColors.surfaceContainer)
            ) {
                state.menuItems.forEach { item ->
                    BottomMenuItem(
                        item = item,
                        selected = item == state.selectedItem,
                        onItemClick = { onEvent(MainScaffoldEvent.OnItemClick(it)) })
                }
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewMainScaffold(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        MainScaffold(
            state =
                UiState(
                    selectedItem = MenuItem.Dictionary,
                    menuItems = persistentListOf(
                        MenuItem.Home,
                        MenuItem.Dictionary
                    )
                ),
            screenContent = {
                Box(Modifier.fillMaxSize())
            },
        )
    }
}

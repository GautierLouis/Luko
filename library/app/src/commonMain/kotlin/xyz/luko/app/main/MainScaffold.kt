package xyz.luko.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.app.main.MainViewModel.UiState
import xyz.luko.dictionary.home.DictionaryScreen
import xyz.luko.feed.FeedScreen
import xyz.luko.home.HomeScreen
import xyz.luko.profile.ProfileScreen
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
        screenContent = {
            when (state.selectedItem) {
                MenuItem.Home -> HomeScreen()
                MenuItem.Dictionary -> DictionaryScreen()
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
    screenContent: @Composable () -> Unit = {},
    onEvent: (MainScaffoldEvent) -> Unit = {},
) {
    val menu = rememberMenu()

    Box(
        modifier = Modifier
            .background(Theme.materialColors.background)
            .fillMaxSize(),
        contentAlignment = menu.boxAlignment
    ) {
        Box(modifier = Modifier.padding(menu.paddingToMenu)) {
            screenContent()
        }
        Menu(
            modifier = Modifier
                .align(menu.boxAlignment)
                .navigationBarsPadding()
                .padding(menu.paddingToEdge),
            leadingMenuItems = state.leadingMenuItems,
            trailingMenuItems = state.trailingMenuItems,
            selectedItem = state.selectedItem,
            orientation = menu.orientation,
            onItemClick = {
                onEvent(MainScaffoldEvent.OnItemClick(it))
            },
            onMainItemClick = {
                onEvent(MainScaffoldEvent.OnMainItemClick)
            },
        )
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
                    leadingMenuItems = persistentListOf(
                        MenuItem.Home,
                        MenuItem.Dictionary
                    ),
                    trailingMenuItems = persistentListOf(
                        MenuItem.Feed,
                        MenuItem.Profile
                    )
                ),
            screenContent = {
                Box(Modifier.fillMaxSize())
            },
        )
    }
}

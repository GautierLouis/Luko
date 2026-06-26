package xyz.luko.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.app.main.MainViewModel.UiState
import xyz.luko.app.main.MenuDefault.FloatingActionSize
import xyz.luko.dictionary.home.DictionaryScreen
import xyz.luko.feed.FeedScreen
import xyz.luko.home.HomeScreen
import xyz.luko.profile.ProfileScreen
import xyz.luko.ui.core.window.rememberWindowInfo
import xyz.luko.ui.designsystem.preview.PreviewScreen
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding

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
    val windowInfo = rememberWindowInfo()

    val boxAlignment = when {
        windowInfo.isCompact() -> Alignment.BottomCenter
        else -> Alignment.CenterEnd
    }

    val orientation = when {
        windowInfo.isCompact() -> Orientation.Horizontal
        else -> Orientation.Vertical
    }

    val paddingToEdge = when {
        windowInfo.isCompact() -> PaddingValues(
            bottom = Padding.large + WindowInsets.navigationBars.asPaddingValues()
                .calculateBottomPadding()
        )

        else -> PaddingValues(end = Padding.large)
    }

    val paddingToMenu = when {
        windowInfo.isCompact() -> PaddingValues(bottom = FloatingActionSize + Padding.large)
        else -> PaddingValues(end = FloatingActionSize + Padding.large)
    }

    Box(
        modifier = Modifier
            .background(Theme.materialColors.background)
            .fillMaxSize(),
        contentAlignment = boxAlignment
    ) {
        Box(modifier = Modifier.padding(paddingToMenu)) {
            screenContent()
        }
        Menu(
            leadingMenuItems = state.leadingMenuItems,
            trailingMenuItems = state.trailingMenuItems,
            selectedItem = state.selectedItem,
            orientation = orientation,
            modifier = Modifier
                .align(boxAlignment)
                .padding(paddingToEdge),
            onItemClick = {
                onEvent(MainScaffoldEvent.OnItemClick(it))
            },
            onMainItemClick = {
                onEvent(MainScaffoldEvent.OnMainItemClick)
            }
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

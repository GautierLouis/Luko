package com.louisgautier.app.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.app.main.MainScaffoldEvent.OnBottomItemClicked
import com.louisgautier.app.main.MainViewModel.UiState
import com.louisgautier.designsystem.components.page.BaseScaffold
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.dictionary.home.DictionaryScreen
import com.louisgautier.feed.FeedScreen
import com.louisgautier.home.HomeScreen
import com.louisgautier.profile.ProfileScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MainScaffold(
    viewModel: MainViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScaffold(
        state = state,
        screenContent = {
            when (state.selectedItem) {
                BottomNavItem.Home -> HomeScreen()
                BottomNavItem.Dictionary -> DictionaryScreen()
                BottomNavItem.Feed -> FeedScreen()
                BottomNavItem.Profile -> ProfileScreen()
            }
        },
        onEvent = { event -> viewModel.onEventReceived(event) }
    )
}

@Composable
private fun MainScaffold(
    state: UiState,
    screenContent: @Composable () -> Unit = {},
    onEvent: (MainScaffoldEvent) -> Unit = {},
) {
    BaseScaffold(
        bottomBar = {
            if (state.enableBottomBar) {
                BottomBar(
                    items = state.bottomNavItem,
                    selectedItem = state.selectedItem,
                    onClick = { onEvent(OnBottomItemClicked(it)) }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            screenContent()
        }
    }
}

@Preview
@Composable
private fun PreviewMainScaffold(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        MainScaffold(
            state = UiState(
                selectedItem = BottomNavItem.Dictionary
            ),
            screenContent = {
                Box(Modifier.fillMaxSize())
            }
        )
    }
}
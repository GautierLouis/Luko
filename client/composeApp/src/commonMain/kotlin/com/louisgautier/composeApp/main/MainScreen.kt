package com.louisgautier.composeApp.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.composeApp.home.HomeScreen
import com.louisgautier.composeApp.main.MainScreenEvent.OnBottomItemClicked
import com.louisgautier.composeApp.main.MainViewModel.UiState
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.dictionary.home.DictionaryScreen
import com.louisgautier.logger.AppLogger
import com.louisgautier.profile.ProfileScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScreen(
        state = state,
        screenContent = {
            when (state.selectedItem) {
                BottomNavItem.Home -> HomeScreen()
                BottomNavItem.Dictionary -> DictionaryScreen()
                BottomNavItem.Profile -> ProfileScreen()
            }
        },
        onEvent = { event -> viewModel.onEventReceived(event) }
    )
}

@Composable
private fun MainScreen(
    state: UiState,
    screenContent: @Composable () -> Unit = {},
    onEvent: (MainScreenEvent) -> Unit = {},
) {
    Scaffold(
        containerColor = Theme.materialColors.background,
        bottomBar = {
            AppLogger.d(tag = "MainScreen", message = "BottomBar loading")
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
private fun PreviewMainScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        MainScreen(
            state = UiState(
                selectedItem = BottomNavItem.Dictionary
            ),
            screenContent = {
                Box(Modifier.fillMaxSize())
            }
        )
    }
}
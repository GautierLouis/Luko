package xyz.luko.learning.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.device.rememberAdaptiveWindowInfo
import xyz.luko.baseui.preview.PreviewProvider
import xyz.luko.baseui.session.toUiModel
import xyz.luko.designsystem.components.metrics.SessionCard
import xyz.luko.designsystem.components.page.NestedScaffold
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.navigation.AppNavigation
import xyz.luko.navigation.AppRoute

@Composable
fun SessionListScreen() {
    val viewModel = koinViewModel<SessionListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    SessionListScreen(state)
}

@Composable
private fun SessionListScreen(
    state: SessionListViewModel.UiState
) {

    val windowState = rememberAdaptiveWindowInfo()

    NestedScaffold { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(
                if (windowState.isLandscape) 2 else 1
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = Padding.large)
                .padding(horizontal = Padding.large),
            verticalArrangement = Spacing.large,
            horizontalArrangement = Spacing.large,
        ) {
            items(
                items = state.sessions,
                key = { it.id },
            ) {
                SessionCard(
                    model = it.toUiModel(),
                    onClick = {
                        AppNavigation.navigate(
                            AppRoute.LearningRoute.SessionDetailsRoute(it.id)
                        )
                    }
                )
            }
        }
    }
}


@PreviewScreen
@Composable
private fun PreviewSessionListScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        SessionListScreen(
            state = SessionListViewModel.UiState(
                sessions = PreviewProvider.sessionList
            )
        )
    }
}



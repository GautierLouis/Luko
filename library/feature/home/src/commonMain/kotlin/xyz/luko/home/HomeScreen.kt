package xyz.luko.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.session.toUiModel
import xyz.luko.designsystem.components.metrics.MoreSessionCard
import xyz.luko.designsystem.components.metrics.OverallStatisticsCard
import xyz.luko.designsystem.components.metrics.SessionCard
import xyz.luko.designsystem.components.page.NestedScaffold
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.core.window.WindowInfo
import xyz.luko.ui.core.window.rememberWindowInfo
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state.value)
}

@Composable
private fun HomeScreen(
    state: HomeViewModel.UIState,
) {
    val windowInfo = rememberWindowInfo()

    val span = when (windowInfo) {
        WindowInfo.EXPANDED -> 2
        else -> 1
    }

    NestedScaffold(
        modifier = Modifier.testTag(TestTags.Screen.HOME),
    ) { paddingValues ->

        LazyVerticalGrid(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            columns = GridCells.Fixed(span),
            verticalArrangement = Spacing.large,
            horizontalArrangement = Spacing.large,
            contentPadding = PaddingValues(Padding.large)
        ) {
            item(
                span = { GridItemSpan(span) }
            ) {
                OverallStatisticsCard(
                    metrics = state.stats.toUiModel()
                )
            }


            if (state.lastSessions.isNotEmpty() && span == 1) {
                item {
                    Text(
                        text = Theme.strings.lastSessionTitle.uppercase(),
                        style = Theme.typography.titleMedium,
                        color = Theme.materialColors.outline
                    )
                }
            }

            items(
                count = state.lastSessions.size,
                key = { index -> state.lastSessions[index].id },
                contentType = { index -> index == 0 }
            ) { index ->
                val session = state.lastSessions[index]

                if (index == 0) {
                    SessionCard(model = session.toUiModel()) {
                        AppNavigation.navigate(
                            AppRoute.SessionsRoute.SessionListRoute(session.id)
                        )
                    }
                } else {
                    MoreSessionCard(
                        modifier = Modifier.testTag(TestTags.Action.SECONDARY),
                        model = session.toUiModel()
                    ) {
                        AppNavigation.navigate(AppRoute.SessionsRoute.SessionListRoute())
                    }
                }
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewHomeScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        HomeScreen(
            state =
                HomeViewModel.UIState(
                    lastSessions = PreviewProvider.sessionList,
                    stats = PreviewProvider.statistics,
                ),
        )
    }
}

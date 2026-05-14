package xyz.luko.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.AdaptiveLayout
import xyz.luko.baseui.TestTags
import xyz.luko.baseui.device.rememberAdaptiveWindowInfo
import xyz.luko.baseui.session.toUiModel
import xyz.luko.designsystem.components.metrics.OverallStatisticsCard
import xyz.luko.designsystem.components.metrics.SessionCard
import xyz.luko.designsystem.components.page.BaseScaffold
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.domain.previewSession
import xyz.luko.domain.previewStatistics
import xyz.luko.navigation.AppNavigation
import xyz.luko.navigation.AppRoute

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
    val windowInfo = rememberAdaptiveWindowInfo()

    val position = if (windowInfo.isPhoneLandscape) FabPosition.End
    else FabPosition.Center

    val fabAttrs =
        if (windowInfo.isPhoneLandscape) PracticeButtonAttrs.SMALL else PracticeButtonAttrs.LARGE

    BaseScaffold(
        modifier = Modifier.testTag(TestTags.Screen.HOME),
        floatingActionButtonPosition = position,
        floatingActionButton = {
            PracticeButton(
                attrs = fabAttrs,
                onClick = {
                    AppNavigation.navigate(AppRoute.LearningRoute.NewSessionRoute)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Padding.large),
        ) {
            AdaptiveLayout(
                spacing = Padding.large
            ) {
                OverallStatisticsCard(
                    streak = state.stats.currentDayStreak.toString(),
                    sessions = state.stats.sessionCount.toString(),
                    score = state.stats.totalScore.toString(),
                )

                state.lastSession?.let {
                    SessionCard(
                        model = state.lastSession.toUiModel()
                    )
                }
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewHomeScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        HomeScreen(
            state = HomeViewModel.UIState(
                isLoading = false,
                lastSession = previewSession,
                stats = previewStatistics
            ),
        )
    }
}
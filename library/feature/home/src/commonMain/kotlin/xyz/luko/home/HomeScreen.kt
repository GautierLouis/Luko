package xyz.luko.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.adaptive.AdaptiveLayout
import xyz.luko.baseui.device.rememberAdaptiveWindowInfo
import xyz.luko.baseui.session.toUiModel
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonRole
import xyz.luko.designsystem.components.button.attrs.ButtonShape
import xyz.luko.designsystem.components.metrics.OverallStatisticsCard
import xyz.luko.designsystem.components.metrics.SessionCard
import xyz.luko.designsystem.components.page.NestedScaffold
import xyz.luko.designsystem.modifier.sharedBounds
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.preview.PreviewProvider
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
    val windowInfo = rememberAdaptiveWindowInfo()

    val position =
        if (windowInfo.isPhoneLandscape) {
            FabPosition.End
        } else {
            FabPosition.Center
        }

    val fabAttrs =
        if (windowInfo.isPhoneLandscape) PracticeButtonAttrs.SMALL else PracticeButtonAttrs.LARGE

    NestedScaffold(
        modifier = Modifier.testTag(TestTags.Screen.HOME),
        floatingActionButtonPosition = position,
        floatingActionButton = {
            PracticeButton(
                attrs = fabAttrs,
                modifier = Modifier
                    .testTag(TestTags.Action.PRIMARY)
                    .navigationBarsPadding()
                    .sharedBounds(key = "start_session"),
                onClick = {
                    AppNavigation.navigate(AppRoute.LearningRoute.NewSessionRoute)
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Padding.large),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Spacing.large
            ) {
                AdaptiveLayout(
                    spacing = Padding.large,
                ) {
                    OverallStatisticsCard(
                        streak = state.stats.currentDayStreak.toString(),
                        sessions = state.stats.sessionCount.toString(),
                        score = state.stats.totalScore.toString(),
                    )

                    state.lastSession?.let {
                        SessionCard(
                            model = state.lastSession.toUiModel(),
                            onClick = {
                                AppNavigation.navigate(
                                    AppRoute.SessionsRoute.SessionListRoute(it.id)
                                )
                            }
                        )
                    }
                }
                if (state.hasMore) {
                    AppButton(
                        text = "See More",
                        shape = ButtonShape.Outlined,
                        modifier = Modifier.testTag(TestTags.Action.SECONDARY),
                        role = ButtonRole.Secondary,
                        onClick = {
                            AppNavigation.navigate(AppRoute.SessionsRoute.SessionListRoute())
                        },
                    )
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
                    hasMore = true,
                    lastSession = PreviewProvider.session,
                    stats = PreviewProvider.statistics,
                ),
        )
    }
}

package xyz.luko.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.session.toUiModel
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.core.window.rememberIsWiderThanTall
import xyz.luko.ui.designsystem.components.button.AppButton
import xyz.luko.ui.designsystem.components.button.attrs.ButtonRole
import xyz.luko.ui.designsystem.components.button.attrs.ButtonShape
import xyz.luko.ui.designsystem.components.button.attrs.ButtonSize
import xyz.luko.ui.designsystem.components.metrics.MoreSessionCard
import xyz.luko.ui.designsystem.components.metrics.OverallStatisticsCard
import xyz.luko.ui.designsystem.components.metrics.SessionCard
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.onboarding.OnboardingKey
import xyz.luko.ui.designsystem.preview.PreviewScreen
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.Spacing
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute.SessionsRoute
import xyz.luko.ui.onboarding.registerTooltip

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state.value) { viewModel.event(it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeViewModel.UIState,
    onEvent: (HomeScreenEvent) -> Unit = {}
) {
    val isWider = rememberIsWiderThanTall()
    val span = if (isWider) 2 else 1

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
                val metrics =
                    if (span == 1) state.toUiModelMinimal() else state.toUiModelExtended()
                OverallStatisticsCard(
                    metrics = metrics,
                    modifier = Modifier
                        .registerTooltip(
                            key = OnboardingKey.OVERALL_STATS,
                            anchorPosition = TooltipAnchorPosition.Below
                        )
                )
            }

            if (state.seeAll && !isWider) {
                item(span = { GridItemSpan(1) }) {
                    Text(
                        text = Theme.strings.lastSessionTitle.uppercase(),
                        style = Theme.typography.titleMedium,
                        color = Theme.materialColors.outline
                    )
                }
            }

            state.lastSession?.let {
                item {
                    SessionCard(
                        model = state.lastSession.toUiModel(),
                        onClick = {
                            AppNavigation.navigate(SessionsRoute.SessionListRoute(state.lastSession.id))
                        }
                    )
                }
            }

            if (state.seeAll) {
                item {
                    if (!isWider) {
                        AppButton(
                            text = "See More",
                            shape = ButtonShape.Outlined,
                            role = ButtonRole.Secondary,
                            size = ButtonSize.Large,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { AppNavigation.navigate(SessionsRoute.SessionListRoute()) }
                        )
                    } else {
                        MoreSessionCard(
                            model = state.lastSession!!.toUiModel(),
                            onClick = { AppNavigation.navigate(SessionsRoute.SessionListRoute()) }
                        )
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
                    lastSession = PreviewProvider.session,
                    seeAll = true,
                    streakCount = 1,
                    sessionCount = PreviewProvider.statistics.sessionCount,
                    avgDifficultyLevel = PreviewProvider.statistics.averageDifficulty,
                    avgAccuracy = PreviewProvider.statistics.averageAccuracy,
                ),
        )
    }
}

package com.louisgautier.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.baseui.session.toUiModel
import com.louisgautier.designsystem.components.metrics.OverallStatisticsCard
import com.louisgautier.designsystem.components.metrics.SessionCard
import com.louisgautier.designsystem.components.page.BaseScaffold
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.previewSession
import com.louisgautier.domain.previewStatistics
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.AppRoute
import org.koin.compose.viewmodel.koinViewModel

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
    BaseScaffold(
        topBar = { HomeTopbar(state.topbarTitle) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            PracticeButton(
                onClick = {
                    AppNavigation.navigate(AppRoute.LearningRoute.NewSessionRoute)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Padding.large),
        ) {
            Column(
                verticalArrangement = Spacing.largest
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

@Preview
@Composable
private fun PreviewHomeScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        HomeScreen(
            state = HomeViewModel.UIState(
                isLoading = false,
                lastSession = previewSession,
                stats = previewStatistics
            ),
        )
    }
}
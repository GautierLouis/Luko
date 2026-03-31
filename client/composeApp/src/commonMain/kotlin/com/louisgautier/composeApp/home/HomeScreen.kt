package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.metrics.OverallStatisticsCard
import com.louisgautier.designsystem.components.metrics.SessionCard
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.previewSession
import com.louisgautier.domain.previewStatistics
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.BuilderKey
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(state.value)
}

@Composable
fun HomeScreen(
    state: HomeViewModel.UIState,
) {
    Scaffold(
        topBar = { HomeTopbar(state.topbarTitle) },
        containerColor = Theme.materialColors.background
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
                        date = state.lastSession.date,
                        duration = state.lastSession.duration,
                        questionsCount = state.lastSession.questionsCount.toString(),
                        difficulty = when (state.lastSession.difficulty) {
                            Difficulty.EASY -> Theme.strings.easy
                            Difficulty.MEDIUM -> Theme.strings.medium
                            Difficulty.HARD -> Theme.strings.hard
                        },
                        score = state.lastSession.score
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            PracticeButton(
                Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    AppNavigation.navigate(BuilderKey)
                }
            )
        }
    }
}

@Composable
@Preview
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
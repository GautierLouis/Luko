package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.previewSession
import com.louisgautier.domain.previewStatistics
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.BuilderKey
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Padding.large),
    ) {
        Column(
            verticalArrangement = Spacing.largest
        ) {
            StatisticsCard(
                streak = state.stats.currentDayStreak.toString(),
                sessions = state.stats.sessionCount.toString(),
                score = state.stats.totalScore.toString(),
            )

            state.lastSession?.let {
                LastSessionCard(state.lastSession)
            }
        }

        Spacer(Modifier.weight(1f))

        Column(
            verticalArrangement = Spacing.large
        ) {
            AppButton(
                modifier = Modifier,
                onClick = { AppNavigation.navigate(BuilderKey) }
            ) {
                Text(Theme.strings.practice)
            }
        }
    }
}

@Composable
@Preview()
private fun PreviewHomeScreen() {
    HomeScreen(
        state = HomeViewModel.UIState(
            isLoading = false,
            lastSession = previewSession,
            stats = previewStatistics
        ),
    )
}



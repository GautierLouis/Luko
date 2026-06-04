package xyz.luko.learning.congratulation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.designsystem.components.page.LoadingScreen
import xyz.luko.learning.congratulation.EndOfSessionViewModel.UIState
import xyz.luko.learning.congratulation.stats.EndOfSessionStatsScreen
import xyz.luko.learning.congratulation.streak.StreakRefreshScreen

@Composable
internal fun EndOfSessionRouterScreen() {
    val viewModel = koinViewModel<EndOfSessionViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    EndOfSessionRouterScreen(state) {
        viewModel.navigateToStats()
    }
}

@Composable
private fun EndOfSessionRouterScreen(
    state: UIState,
    onClick: () -> Unit = {},
) {
    when (state) {
        UIState.Loading -> LoadingScreen()
        is UIState.Success -> when (state.screen) {
            EndOfSessionScreens.STREAK -> StreakRefreshScreen(state.newStreak, onClick)
            EndOfSessionScreens.STATS -> EndOfSessionStatsScreen(state.session)
        }
    }
}

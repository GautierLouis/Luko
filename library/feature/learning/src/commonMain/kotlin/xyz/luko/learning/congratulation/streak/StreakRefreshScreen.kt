package xyz.luko.learning.congratulation.streak

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonSize
import xyz.luko.designsystem.components.page.NestedScaffold
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.Session
import xyz.luko.learning.congratulation.streak.ui.DayCount
import xyz.luko.learning.congratulation.streak.ui.StreakWeek
import xyz.luko.learning.congratulation.streak.ui.previewStreakDays
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.navigation.AppNavigation

@Composable
internal fun StreakRefreshScreen(
    newStreak: Int,
    lastSession: Session
) {
    val viewModel = koinViewModel<StreakRefreshViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    StreakRefreshScreen(
        state = state,
        newStreak = newStreak,
        onClick = {
            AppNavigation.navigate(
                route = LearningInternalRoute.CongratulationRoute(lastSession),
                clearBackStack = true
            )
        }
    )
}

@Composable
private fun StreakRefreshScreen(
    state: StreakRefreshViewModel.UIState,
    newStreak: Int,
    onClick: () -> Unit = {},
) {

    NestedScaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Padding.medium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Spacing.extraExtraLarge,
            ) {
                DayCount(
                    showNew = state.startFirstAnim,
                    newStreak = newStreak
                )
                StreakWeek(
                    days = state.streakDays,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Padding.extraLarge),
                )
            }

            AnimatedVisibility(
                visible = state.startSecondAnim,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                enter = fadeIn(tween(350))
            ) {
                AppButton(
                    text = Theme.strings.sessionStreakRefreshButtonNext,
                    size = ButtonSize.Large,
                    onClick = onClick,
                    modifier = Modifier.testTag(TestTags.Action.PRIMARY)
                )
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewSteakRefreshScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        StreakRefreshScreen(
            state = StreakRefreshViewModel.UIState(
                startSecondAnim = true,
                startFirstAnim = true,
                streakDays = previewStreakDays
            ),
            newStreak = 2
        )
    }
}

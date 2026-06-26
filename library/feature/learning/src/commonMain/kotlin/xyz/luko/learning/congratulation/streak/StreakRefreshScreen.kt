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
import org.koin.core.parameter.parametersOf
import xyz.luko.learning.congratulation.streak.ui.DayCount
import xyz.luko.learning.congratulation.streak.ui.StreakWeek
import xyz.luko.learning.congratulation.streak.ui.previewStreakDays
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.window.WindowInfo
import xyz.luko.ui.core.window.rememberWindowInfo
import xyz.luko.ui.designsystem.components.button.AppButton
import xyz.luko.ui.designsystem.components.button.attrs.ButtonSize
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.PreviewScreen
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.Spacing

@Composable
internal fun StreakRefreshScreen(
    route: LearningInternalRoute.StreakRoute
) {
    val viewModel = koinViewModel<StreakRefreshViewModel> {
        parametersOf(route.lastSession.id)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    StreakRefreshScreen(
        state = state,
        newStreak = route.newValue,
        onClick = { viewModel.next() }
    )
}

@Composable
private fun StreakRefreshScreen(
    state: StreakRefreshViewModel.UIState,
    newStreak: Int,
    onClick: () -> Unit = {},
) {

    val windowInfo = rememberWindowInfo()
    val align = when (windowInfo) {
        WindowInfo.HEIGHT_COMPACT -> Alignment.TopCenter
        else -> Alignment.Center
    }

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
                    .align(align),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Spacing.extraExtraLarge,
            ) {
                DayCount(
                    showNew = state.startFirstAnim,
                    newStreak = newStreak
                )
                StreakWeek(
                    days = state.streakDays,
                    startFirstAnim = state.startFirstAnim,
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

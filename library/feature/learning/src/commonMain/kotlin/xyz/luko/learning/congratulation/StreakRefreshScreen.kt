package xyz.luko.learning.congratulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.test.TestTags
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
import kotlin.time.Clock

@Composable
fun StreakRefreshScreen(
    newStreak: Int,
    onClick: () -> Unit = {},
) {
    val viewModel = koinViewModel<StreakRefreshViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    StreakRefreshScreen(
        state = state,
        newStreak = newStreak,
        onClick = onClick
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
                DayCount(state, newStreak)
                StreakWeek(
                    week = state.streak,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Padding.extraLarge)
                )

            }

            AnimatedVisibility(
                visible = state.showBtn,
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

@Composable
private fun DayCount(
    state: StreakRefreshViewModel.UIState,
    newStreak: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            this@Column.AnimatedVisibility(
                visible = !state.showNew,
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(350)
                ) + fadeOut(tween(350))
            ) {
                Text(
                    text = "${newStreak.dec()}",
                    style = Theme.typography.displayLarge,
                    fontWeight = FontWeight.Medium,
                )
            }

            this@Column.AnimatedVisibility(
                visible = state.showNew,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(350, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(350))
            ) {
                Text(
                    text = "$newStreak",
                    style = Theme.typography.displayLarge,
                    fontWeight = FontWeight.Medium,
                    color = Theme.appLevelColors.easy.primary
                )
            }
        }

        Text(
            text = "Day Streak!",
            style = Theme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@PreviewScreen
@Composable
private fun PreviewSteakRefreshScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val firstJune = LocalDate(2026, 6, 1)

    val data = WeekStreak(
        listOf(
            StreakDay(
                date = firstJune,
                isCompleted = true
            ),
            StreakDay(
                date = today.plus(1, DateTimeUnit.DAY),
                isCompleted = true
            ),
            StreakDay(
                date = today.plus(2, DateTimeUnit.DAY),
                isCompleted = true
            ),
            StreakDay(
                date = today.plus(3, DateTimeUnit.DAY),
                isCompleted = true
            ),
            StreakDay(
                date = today.plus(4, DateTimeUnit.DAY),
                isCompleted = true
            ),
            StreakDay(
                date = today.plus(5, DateTimeUnit.DAY),
                isCompleted = true
            ),
            StreakDay(
                date = today.plus(6, DateTimeUnit.DAY),
                isCompleted = true
            ),
        ).toImmutableList()
    )

    AppTheme(themeMode) {
        StreakRefreshScreen(
            state = StreakRefreshViewModel.UIState(
                showBtn = true,
                showNew = true,
                streak = data
            ),
            newStreak = 2
        )
    }
}

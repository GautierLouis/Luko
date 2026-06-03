package xyz.luko.learning.congratulation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing

@Composable
internal fun StreakWeek(
    week: WeekStreak,
    modifier: Modifier = Modifier
) {
    val days = Theme.strings.dayOfWeekName

    val todayIndex = week.days.indexOfFirst { it.isToday }

    val animatedIndex = remember(week) {
        week.days.indexOfLast { it.isCompleted }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.materialColors.background)
            .border(
                border = BorderStrokeDefaults.minimum(Theme.materialColors.outline),
                shape = ShapeDefaults.card()
            )
            .padding(vertical = Padding.small),
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(days.size) { index ->

                val current = days[index]
                val prev = week.days.getOrNull(index - 1)
                val next = week.days.getOrNull(index + 1)

                Column(
                    verticalArrangement = Spacing.small,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = Padding.medium, bottom = Padding.large)
                ) {
                    Text(
                        text = current.first().toString(),
                        style = Theme.typography.bodyMedium,
                        color = Theme.materialColors.onBackground,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = Theme.typography.bodyLarge.fontSize,
                            maxFontSize = Theme.typography.titleLarge.fontSize
                        )
                    )

                    val shouldAnimate = index == animatedIndex

                    var animate by remember(week) {
                        mutableStateOf(!shouldAnimate)
                    }

                    LaunchedEffect(shouldAnimate) {
                        if (shouldAnimate) {
                            animate = true
                        }
                    }

                    val progress by animateFloatAsState(
                        targetValue = when {
                            !week.days[index].isCompleted -> 0f
                            animate -> 1f
                            else -> 0f
                        },
                        animationSpec = tween(
                            durationMillis = 800,
                            easing = FastOutSlowInEasing
                        ),
                        label = "streak-progress"
                    )

                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .height(36.dp)
                                .background(
                                    color = Theme.materialColors.primary.copy(alpha = .4f),
                                    shape = calculateShape(index, prev, next, progress)
                                )
                        )

                        StreakDayItem(
                            progress = progress,
                            day = week.days[index],
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

private fun calculateShape(
    index: Int,
    prev: StreakDay? = null,
    next: StreakDay? = null,
    progress: Float,
): RoundedCornerShape {

    val start = when {
        index == 0 || prev?.isCompleted == true -> 0.dp
        else -> 50.dp
    }

    val end = when {
        index == 6 -> lerp(50.dp, 0.dp, progress)
        next?.isCompleted == true -> 0.dp
        else -> 50.dp
    }
    return RoundedCornerShape(topStart = start, bottomStart = start, topEnd = end, bottomEnd = end)
}

@Preview
@Composable
private fun PreviewStreakWeek(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    val data = WeekStreak(
        listOf(
            StreakDay(
                date = LocalDate(2026, 6, 1),
                isCompleted = true
            ),
            StreakDay(
                date = LocalDate(2026, 6, 2),
                isCompleted = false
            ),
            StreakDay(
                date = LocalDate(2026, 6, 3),
                isCompleted = true
            ),
            StreakDay(
                date = LocalDate(2026, 6, 4),
                isCompleted = true
            ),
            StreakDay(
                date = LocalDate(2026, 6, 5),
                isCompleted = true
            ),
            StreakDay(
                date = LocalDate(2026, 6, 6),
                isCompleted = false
            ),
            StreakDay(
                date = LocalDate(2026, 6, 7),
                isCompleted = true
            ),
        ).toImmutableList()
    )
    AppTheme(themeMode) {

        StreakWeek(
            week = data,
        )
    }
}

package xyz.luko.learning.congratulation.streak.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import xyz.luko.learning.congratulation.streak.DayStreak
import xyz.luko.learning.congratulation.streak.dayStreakPreview
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StreakWeek(
    days: ImmutableList<DayStreak>,
    startFirstAnim: Boolean,
    modifier: Modifier = Modifier,
) {
    val daysLabels = Theme.strings.dayOfWeekName


    var hasPlayedAnim by rememberSaveable { mutableStateOf(false) }
    val todayIsActive = days.any { it.isToday && it.hasSession }
    val todayIndex = days.indexOfFirst { it.isToday }

    LaunchedEffect(startFirstAnim, todayIsActive) {
        if (!hasPlayedAnim && todayIsActive && startFirstAnim) {
            hasPlayedAnim = true
        }
    }

    val todayProgress by animateFloatAsState(
        targetValue = if (hasPlayedAnim) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "today-progress"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.materialColors.background)
            .padding(vertical = Padding.small),
        contentAlignment = Alignment.BottomStart
    ) {
        WeekStreakSlider(
            days = days,
            todayProgress = todayProgress,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Padding.small),
        ) {
            days.zip(daysLabels).forEachIndexed { index, (day, label) ->
                val isBadgeVisible = when {
                    index != todayIndex -> day.hasSession
                    else -> todayProgress == 1f
                }
                DayCircle(
                    title = label,
                    isActive = day.hasSession,
                    isBadgeVisible = isBadgeVisible,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeekStreakSlider(
    days: List<DayStreak>,
    todayProgress: Float,
    modifier: Modifier = Modifier,
) {
    if (days.size < 2) return

    val state = remember {
        RangeSliderState(
            activeRangeStart = 0f,
            activeRangeEnd = 0f,
            valueRange = 0f..1f,
            steps = days.size - 2, // 7 days -> 6 gaps -> 5 intermediate steps
        )
    }

    fun activeness(index: Int): Float = when {
        days[index].isToday -> todayProgress
        days[index].hasSession -> 1f
        else -> 0f
    }

    RangeSlider(
        state = state,
        enabled = false,
        modifier = modifier.height(36.dp),
        startThumb = {},
        endThumb = {},
        track = {
            WeekTrack(dayCount = days.size, activeness = ::activeness)
        }
    )
}

@Composable
private fun WeekTrack(
    dayCount: Int,
    activeness: (Int) -> Float,
    cornerRadius: Dp = 18.dp,
) {
    Row(modifier = Modifier.height(36.dp)) {
        repeat(dayCount) { index ->
            val progress = activeness(index)
            val isFirst = index == 0
            val isLast = index == dayCount - 1
            val prevProgress = if (index > 0) activeness(index - 1) else 0f
            val nextProgress = if (index < dayCount - 1) activeness(index + 1) else 0f

            val startRadius = if (isFirst) cornerRadius else cornerRadius * (1f - prevProgress)
            val endRadius = if (isLast) cornerRadius else cornerRadius * (1f - nextProgress)

            val shape = if (progress == 0f) {
                RectangleShape
            } else {
                RoundedCornerShape(
                    topStart = startRadius, bottomStart = startRadius,
                    topEnd = endRadius, bottomEnd = endRadius,
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = Theme.materialColors.primary.copy(alpha = .4f * progress),
                        shape = shape
                    )
            )
        }
    }
}

@Composable
private fun DayCircle(
    title: String,
    isActive: Boolean,
    isBadgeVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val streakColor = if (isActive) {
        Theme.materialColors.primary
    } else {
        Theme.materialColors.outline
    }

    Column(
        verticalArrangement = Spacing.medium,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = title.first().toString(),
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

        StreakBadge(
            visible = isBadgeVisible,
            streakColor = streakColor,
        )
    }
}

@Preview
@Composable
private fun PreviewStreakWeek(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        StreakWeek(
            days = dayStreakPreview.toImmutableList(),
            startFirstAnim = true
        )
    }
}

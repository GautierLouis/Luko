package xyz.luko.baseui.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.Statistics
import xyz.luko.ui.designsystem.components.metrics.SessionUiModel
import xyz.luko.ui.designsystem.components.metrics.attrs.AppStatistic
import xyz.luko.ui.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.utils.toAccessibilityDate
import xyz.luko.utils.toFormattedString
import xyz.luko.utils.toHHMMSS
import xyz.luko.utils.toISODateString
import xyz.luko.utils.toPercentage

@Composable
fun Session.toUiModel(): SessionUiModel {
    val pattern = Theme.strings.datePattern
    return SessionUiModel(
        date = remember(date) { date.toISODateString(pattern) },
        accessibilityDate = remember(date) { date.toAccessibilityDate() },
        duration = remember(duration) { duration.toHHMMSS() },
        difficulty =
            when (difficulty) {
                DifficultyLevel.EASY -> Theme.strings.easy
                DifficultyLevel.MEDIUM -> Theme.strings.medium
                DifficultyLevel.HARD -> Theme.strings.hard
            },
        questionsCount = questionsCount.toString(),
        score = score.toFormattedString(),
    )
}

fun Statistics.toUiModel(): ImmutableList<MetricItem.AppMetric> {
    return persistentListOf(
        MetricItem.AppMetric(
            metric = AppStatistic.Streak,
            value = currentDayStreak.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.Sessions,
            value = sessionCount.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.AvgAccuracy,
            value = averageAccuracy.toPercentage(),
        ),
    )
}

@Composable
fun Statistics.toUiModelExtended(): ImmutableList<MetricItem.AppMetric> {
    return persistentListOf(
        MetricItem.AppMetric(
            metric = AppStatistic.Streak,
            value = currentDayStreak.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.Sessions,
            value = sessionCount.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.AvgDifficulty,
            value = averageDifficulty?.title() ?: "-",
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.AvgAccuracy,
            value = averageAccuracy.toPercentage(),
        ),
    )
}


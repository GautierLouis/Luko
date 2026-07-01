package xyz.luko.home

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.baseui.session.title
import xyz.luko.ui.designsystem.components.metrics.attrs.AppStatistic
import xyz.luko.ui.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.utils.toPercentage

@Composable
internal fun HomeViewModel.UIState.toUiModelExtended(): ImmutableList<MetricItem.AppMetric> {
    return persistentListOf(
        MetricItem.AppMetric(
            metric = AppStatistic.Streak,
            value = streakCount.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.Sessions,
            value = sessionCount.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.AvgDifficulty,
            value = avgDifficultyLevel?.title() ?: "-",
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.AvgAccuracy,
            value = avgAccuracy.toPercentage(),
        ),
    )
}

@Composable
internal fun HomeViewModel.UIState.toUiModelMinimal(): ImmutableList<MetricItem.AppMetric> {
    return persistentListOf(
        MetricItem.AppMetric(
            metric = AppStatistic.Streak,
            value = streakCount.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.Sessions,
            value = sessionCount.toString(),
        ),
        MetricItem.AppMetric(
            metric = AppStatistic.AvgAccuracy,
            value = avgAccuracy.toPercentage(),
        ),
    )
}

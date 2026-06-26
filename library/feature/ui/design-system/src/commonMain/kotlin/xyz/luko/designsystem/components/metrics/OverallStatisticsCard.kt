package xyz.luko.designsystem.components.metrics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.designsystem.components.metrics.attrs.AppStatistic
import xyz.luko.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedBarChart
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

@Composable
fun OverallStatisticsCard(
    metrics: ImmutableList<MetricItem.AppMetric>,
    modifier: Modifier = Modifier,
) {
    MetricCardLayout(
        header = {
            MetricHeader(
                title = Theme.strings.overallStatistics,
                icon = AppIcon.RoundedBarChart,
            )
        },
        modifier = modifier,
        items = metrics,
    )
}

@Preview
@Composable
private fun PreviewOverallStatisticsCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        OverallStatisticsCard(
            metrics = persistentListOf(
                MetricItem.AppMetric(
                    metric = AppStatistic.Streak,
                    value = "2",
                ),
                MetricItem.AppMetric(
                    metric = AppStatistic.Sessions,
                    value = "1",
                ),
                MetricItem.AppMetric(
                    metric = AppStatistic.AvgAccuracy,
                    value = "90%",
                ),
            )
        )
    }
}

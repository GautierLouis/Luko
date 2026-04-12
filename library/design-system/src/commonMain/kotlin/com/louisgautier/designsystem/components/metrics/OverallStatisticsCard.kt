package com.louisgautier.designsystem.components.metrics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBarChart
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun OverallStatisticsCard(
    streak: String,
    sessions: String,
    score: String,
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
        items = persistentListOf(
            MetricItem.AppMetric(
                metric = AppStatistic.Streak,
                value = streak
            ),
            MetricItem.AppMetric(
                metric = AppStatistic.Sessions,
                value = sessions
            ),
            MetricItem.AppMetric(
                metric = AppStatistic.TotalScore,
                value = score
            )
        ),
    )
}

@Preview
@Composable
private fun PreviewOverallStatisticsCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        OverallStatisticsCard(
            streak = "10",
            sessions = "3",
            score = "1500",
        )
    }
}
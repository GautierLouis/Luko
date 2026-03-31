package com.louisgautier.designsystem.components.metrics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBarChart
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

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
                AppStatistic.Sessions,
                sessions
            ),
            MetricItem.AppMetric(
                AppStatistic.TotalScore,
                score
            )
        ),
    )
}

@Preview
@Composable
private fun PreviewOverallStatisticsCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        OverallStatisticsCard(
            streak = "10",
            sessions = "3",
            score = "1500",
        )
    }
}
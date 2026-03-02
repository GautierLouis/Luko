package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.ai.Teal50
import com.louisgautier.designsystem.ai.Teal600
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBarChart
import com.louisgautier.domain.previewStatistics
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StatisticsCard(
    streak: String,
    sessions: String,
    score: String,
    modifier: Modifier = Modifier,
) {
    HomeCard(modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RoundIcon(
                icon = AppIcon.RoundedBarChart,
                containerColor = Teal50,
                contentColor = Teal600,
                size = 24.dp,
                padding = 4.dp
            )
            Text(text = "Overall statistics")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatisticValue(
                metric = StatisticMetric.Streak,
                value = streak,
                modifier = Modifier.weight(1f)
            )
            StatisticValue(
                metric = StatisticMetric.Sessions,
                value = sessions,
                modifier = Modifier.weight(1f)
            )
            StatisticValue(
                metric = StatisticMetric.TotalScore,
                value = score,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun StatisticsCardPreview() {
    StatisticsCard(
        streak = previewStatistics.currentDayStreak.toString(),
        sessions = previewStatistics.sessionCount.toString(),
        score = previewStatistics.totalScore.toString(),
    )
}
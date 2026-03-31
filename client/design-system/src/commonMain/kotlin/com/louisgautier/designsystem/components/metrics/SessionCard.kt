package com.louisgautier.designsystem.components.metrics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedTrophy
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.utils.toHHMMSS
import com.louisgautier.utils.toISODateString
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun SessionCard(
    date: Instant,
    duration: Duration,
    questionsCount: String,
    difficulty: String,
    score: Int,
    modifier: Modifier = Modifier
) {

    MetricCardLayout(
        modifier = modifier,
        header = {
            MetricHeader(
                title = date.toISODateString(),
                icon = AppIcon.RoundedTrophy,
                trailing = { SessionScore(score) }
            )
        },
        items = persistentListOf(
            MetricItem.SessionMetric(
                metric = SessionStatistic.QuestionCount,
                value = questionsCount
            ),
            MetricItem.SessionMetric(
                metric = SessionStatistic.Time,
                value = duration.toHHMMSS()
            ),
            MetricItem.SessionMetric(
                metric = SessionStatistic.Difficulty,
                value = difficulty
            )
        )
    )
}

@Composable
@Preview
private fun PreviewSessionCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        SessionCard(
            date = Clock.System.now(),
            duration = Duration.ZERO,
            questionsCount = "10",
            difficulty = "Hard",
            score = 1000
        )
    }
}
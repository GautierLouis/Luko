package com.louisgautier.designsystem.components.metrics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedTrophy
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SessionUiModel(
    val date: String,
    val duration: String,
    val difficulty: String,
    val questionsCount: String,
    val score: String,
)

@Composable
fun SessionCard(
    model: SessionUiModel,
    modifier: Modifier = Modifier,
) {
    MetricCardLayout(
        modifier = modifier,
        header = {
            MetricHeader(
                title = model.date,
                icon = AppIcon.RoundedTrophy,
                trailing = { SessionScore(model.score) },
            )
        },
        items =
            persistentListOf(
                MetricItem.SessionMetric(
                    metric = SessionStatistic.QuestionCount,
                    value = model.questionsCount,
                ),
                MetricItem.SessionMetric(
                    metric = SessionStatistic.Time,
                    value = model.duration,
                ),
                MetricItem.SessionMetric(
                    metric = SessionStatistic.Difficulty,
                    value = model.difficulty,
                ),
            ),
    )
}

@Preview
@Composable
private fun PreviewSessionCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        SessionCard(
            model =
                SessionUiModel(
                    date = "2026-31-01",
                    duration = "0",
                    questionsCount = "10",
                    difficulty = "Hard",
                    score = "1000",
                ),
        )
    }
}

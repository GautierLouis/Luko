package xyz.luko.designsystem.components.metrics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.designsystem.components.metrics.attrs.SessionStatistic
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedTrophy
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme

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

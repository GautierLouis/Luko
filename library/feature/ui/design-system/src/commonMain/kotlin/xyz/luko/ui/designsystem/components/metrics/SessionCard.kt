package xyz.luko.ui.designsystem.components.metrics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.ui.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.ui.designsystem.components.metrics.attrs.SessionStatistic
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.RoundedTrophy
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme

@Immutable
data class SessionUiModel(
    val date: String,
    val accessibilityDate: String,
    val duration: String,
    val difficulty: String,
    val questionsCount: String,
    val accuracy: String,
)

@Composable
fun SessionCard(
    model: SessionUiModel,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    onClick: () -> Unit = {}
) {
    val accessibleDate = Theme.strings.sessionCardAccessibleDate(model.accessibilityDate)
    val clickLabel = Theme.strings.sessionCardLabel

    MetricCardLayout(
        onClick = onClick,
        enable = clickable,
        modifier = modifier
            .semantics {
                if (clickable) {
                    contentDescription = accessibleDate
                    onClick(label = clickLabel, action = null)
                }
            },
        header = {
            MetricHeader(
                title = model.date,
                icon = AppIcon.RoundedTrophy,
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
                    accessibilityDate = "1st January 2026",
                    duration = "0",
                    questionsCount = "10",
                    difficulty = "Hard",
                    accuracy = "90%"
                ),
        )
    }
}

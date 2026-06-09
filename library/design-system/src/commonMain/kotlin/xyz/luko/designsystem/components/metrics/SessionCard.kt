package xyz.luko.designsystem.components.metrics

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonRole
import xyz.luko.designsystem.components.button.attrs.ButtonShape
import xyz.luko.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.designsystem.components.metrics.attrs.SessionStatistic
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedTrophy
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.ShapeDefaults

@Immutable
data class SessionUiModel(
    val date: String,
    val accessibilityDate: String,
    val duration: String,
    val difficulty: String,
    val questionsCount: String,
    val score: String,
)

@Composable
fun MoreSessionCard(
    model: SessionUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .clip(ShapeDefaults.card())
                .blur(10.dp),
        ) {
            SessionCard(
                model = model,
                clickable = false,
            )
        }
        AppButton(
            text = "See More",
            shape = ButtonShape.Ghost,
            role = ButtonRole.Secondary,
            onClick = onClick,
        )
    }
}

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
                    accessibilityDate = "1st January 2026",
                    duration = "0",
                    questionsCount = "10",
                    difficulty = "Hard",
                    score = "1000",
                ),
        )
    }
}

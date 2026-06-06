package xyz.luko.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.color.model.LevelColors
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.SessionResponse
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.drawing.DrawableArea
import xyz.luko.ui.drawing.DrawableAreaDefault
import kotlin.math.roundToInt

@Composable
internal fun ResponseItem(
    response: SessionResponse,
    onClick: () -> Unit = {},
) {
    val thumbnailSize = 80.dp
    val label = Theme.strings.sessionDetailClickLabel(response.pinyin)

    Card(
        modifier = Modifier.fillMaxWidth()
            .semantics { onClick(label) { true } },
        shape = ShapeDefaults.card(),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer,
            contentColor = Theme.materialColors.onBackground,
        ),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(Padding.large),
            horizontalArrangement = Spacing.medium
        ) {

            DrawableArea(
                enableDrawing = false,
                reference = response.references,
                userStroke = response.strokes,
                strokeWidth = DrawableAreaDefault.STROKE_WIDTH_MINI,
                modifier = Modifier.size(thumbnailSize).background(
                    color = Theme.materialColors.surfaceContainer, shape = ShapeDefaults.card()
                ).border(
                    border = BorderStrokeDefaults.medium(Theme.materialColors.outline),
                    shape = ShapeDefaults.tag(),
                ).padding(Padding.medium).clipToBounds()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(thumbnailSize),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = response.pinyin,
                    style = Theme.typography.titleLarge,
                )
                Row(
                    horizontalArrangement = Spacing.small,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AccuracyTag(
                        value = response.statistics.overallAccuracy,
                        color = response.statistics.overallAccuracy.toAccuracyColor()
                    )
                    Text(
                        text = Theme.strings.sessionDetailTotalStrokes(
                            response.statistics.strokeAccuracies.size,
                            response.references.size
                        )

                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    response.statistics.strokeAccuracies.forEach { accuracy ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(CircleShape)
                                .background(accuracy.toAccuracyColor().primary)
                        )
                    }
                }
            }
        }
    }
}

//TODO Use new appLevelColor
@Composable
internal fun Float.toAccuracyColor() = when {
    this >= 80f -> Theme.appLevelColors.easy
    this >= 50f -> Theme.appLevelColors.medium
    else -> Theme.appLevelColors.hard
}

@Composable
internal fun AccuracyTag(
    value: Float,
    color: LevelColors,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color.subtle, CircleShape)
            .border(BorderStrokeDefaults.minimum(color.primary), CircleShape)
            .padding(horizontal = Padding.medium),
    ) {
        Text(
            text = "${value.roundToInt()}%",
            style = Theme.typography.labelSmall,
            color = color.onSubtle
        )
    }
}

@Preview
@Composable
private fun PreviewResponseItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        ResponseItem(
            response = PreviewProvider.sessionResponse.first()
        )
    }
}

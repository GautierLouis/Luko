package xyz.luko.learning.session.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.zIndex
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.learning.session.model.DrawingPageState
import xyz.luko.learning.session.model.SessionScreenEvent
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.drawing.DrawableArea

@Composable
internal fun GraphicSketcher(
    state: DrawingPageState,
    modifier: Modifier = Modifier,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = Theme.materialColors.surfaceContainer,
            ),
        modifier =
            modifier
                .background(
                    color = Color.Unspecified,
                    shape = ShapeDefaults.card(),
                ).border(
                    border = BorderStrokeDefaults.medium(Theme.materialColors.primary),
                    shape = ShapeDefaults.tag(),
                ),
    ) {
        Box {
            this@Card.AnimatedVisibility(
                visible = state.userPreviousOffsets.isNotEmpty(),
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(Padding.large)
                        .zIndex(1f),
            ) {
                ResetButton(onClick = { onEvent(SessionScreenEvent.Reset) })
            }

            DrawableArea(
                enableDrawing = !state.isComplete,
                reference = state.referenceStrokes,
                hint = state.referenceHint,
                userStroke = state.userPreviousOffsets,
                onDraw = {
                    onEvent(SessionScreenEvent.StrokeCompleted(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(heightDp = 1180)
@Composable
private fun PreviewGraphicPager(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {

    val mockState =
        DrawingPageState(
            referenceStrokes = PreviewProvider.dictionary.medians,
            referenceHint = PreviewProvider.dictionary.medians.firstOrNull(),
            userPreviousOffsets = emptyList()
        )

    AppTheme(themeMode) {
        Column {
            GraphicSketcher(
                state = mockState,
                modifier = Modifier.aspectRatio(1f),
            )

            GraphicSketcher(
                state = mockState,
                modifier = Modifier.aspectRatio(1f),
            )

            GraphicSketcher(
                state = mockState,
                modifier = Modifier.aspectRatio(1f),
            )
        }
    }
}

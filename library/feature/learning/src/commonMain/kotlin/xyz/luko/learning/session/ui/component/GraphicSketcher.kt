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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntSize
import xyz.luko.baseui.drawing.StrokeTransformer
import xyz.luko.baseui.preview.PreviewProvider
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.learning.session.drawing.DrawableArea
import xyz.luko.learning.session.drawing.drawingDetector
import xyz.luko.learning.session.model.DrawingPageState
import xyz.luko.learning.session.model.SessionScreenEvent

@Composable
internal fun GraphicSketcher(
    state: DrawingPageState,
    modifier: Modifier = Modifier,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    val drawnStroke = remember { mutableStateListOf<Offset>() }
    val transformer = remember { StrokeTransformer() }

    val referencePaths = remember(state.referenceStrokes, canvasSize) {
        state.referenceStrokes.map { transformer.toCanvasPath(it, canvasSize) }
    }

    val referenceHint = remember(state.referenceHint, canvasSize) {
        state.referenceHint?.let { transformer.toCanvasHint(it, canvasSize) }
    }

    val previousUserDraw = remember(state.userPreviousOffsets, canvasSize) {
        state.userPreviousOffsets.map { offsets ->
            transformer.toCanvasOffsets(offsets, canvasSize)
        }
    }


    val drawingModifier =
        if (state.isComplete) {
            Modifier
        } else {
            Modifier.drawingDetector(
                points = drawnStroke,
                onGestureComplete = {
                    val raw =
                        transformer.fromCanvasOffsets(drawnStroke.toList(), canvasSize)
                    onEvent(SessionScreenEvent.StrokeCompleted(raw))
                    drawnStroke.clear()
                },
            )
        }

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
                        .padding(Padding.large),
            ) {
                ResetButton(onClick = { onEvent(SessionScreenEvent.Reset) })
            }

            DrawableArea(
                referencePath = referencePaths,
                referenceHint = referenceHint,
                previousDrawnStrokes = previousUserDraw,
                ongoingStroke = drawnStroke,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .onGloballyPositioned { canvasSize = it.size }
                    .then(drawingModifier),
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
            referenceStrokes = PreviewProvider.graphic.smoothMedians,
            referenceHint = PreviewProvider.graphic.smoothMedians.firstOrNull(),
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

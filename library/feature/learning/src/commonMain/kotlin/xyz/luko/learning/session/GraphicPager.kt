package xyz.luko.learning.session

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
import xyz.luko.designsystem.drawing.TransformStroke
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.domain.previewDictionaryWithGraphic
import xyz.luko.learning.session.drawing.DrawableArea
import xyz.luko.learning.session.drawing.drawingDetector

@Composable
internal fun GraphicSketcher(
    state: SessionViewModel.QuestionState,
    modifier: Modifier = Modifier,
    drawReference: Boolean = false,
    drawHint: Boolean = false,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    val graphic = state.question.graphics

    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }
    val drawnStroke = remember { mutableStateListOf<Offset>() }

    val referenceStrokes = remember(graphic.code, canvasSize) {
        graphic.medians.map { stroke ->
            TransformStroke.projectToCanvas(stroke.points.map { Offset(it.x, it.y) }, canvasSize)
        }
    }

    val previousDrawnScaled = remember(state.previousDrawnStrokes, canvasSize) {
        state.previousDrawnStrokes.map { stroke ->
            TransformStroke.projectToCanvas(stroke, canvasSize)
        }
    }

    val referenceHint = referenceStrokes.getOrNull(state.previousDrawnStrokes.size)

    val isComplete = state.previousDrawnStrokes.size == referenceStrokes.size

    val drawingModifier = if (isComplete) Modifier
    else Modifier.drawingDetector(
        points = drawnStroke,
        onGestureComplete = {
            onEvent(
                SessionScreenEvent.StrokeCompleted(
                    TransformStroke.unprojectFromCanvas(drawnStroke.toList(), canvasSize),
                    referenceStrokes
                )
            )
            drawnStroke.clear()
        }
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer
        ),
        modifier = modifier
            .background(
                color = Color.Unspecified,
                shape = ShapeDefaults.card()
            )
            .border(
                border = BorderStrokeDefaults.medium(Theme.materialColors.primary),
                shape = ShapeDefaults.tag()
            ),
    ) {
        Box {
            this@Card.AnimatedVisibility(
                visible = state.previousDrawnStrokes.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Padding.large),
            ) {
                ResetButton(onClick = { onEvent(SessionScreenEvent.Reset) })
            }

            DrawableArea(
                referenceStrokes = referenceStrokes
                    .takeIf { drawReference }
                    .orEmpty(),
                referenceHint = referenceHint
                    ?.takeIf { drawHint }
                    .orEmpty(),
                previousDrawnStrokes = previousDrawnScaled,
                ongoingStroke = drawnStroke,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .onGloballyPositioned { coordinates -> canvasSize = coordinates.size }
                    .then(drawingModifier)
            )
        }
    }
}

@Preview(heightDp = 1180)
@Composable
private fun PreviewGraphicPager(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    val mockState = SessionViewModel.QuestionState(
        question = previewDictionaryWithGraphic,
        previousDrawnStrokes = listOf(listOf(Offset(1f, 1f))),
    )

    AppTheme(themeMode) {
        Column {
            GraphicSketcher(
                drawReference = true,
                drawHint = true,
                state = mockState,
                modifier = Modifier.aspectRatio(1f)
            )

            GraphicSketcher(
                drawReference = false,
                drawHint = true,
                state = mockState,
                modifier = Modifier.aspectRatio(1f)
            )

            GraphicSketcher(
                drawReference = false,
                drawHint = false,
                state = mockState,
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}
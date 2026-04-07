package com.louisgautier.learning.session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.TransformStroke
import com.louisgautier.designsystem.modifier.dashedBorder
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.learning.session.drawing.DrawableArea
import com.louisgautier.learning.session.drawing.drawingDetector

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
            TransformStroke.transformOffset(stroke.points.map { Offset(it.x, it.y) }, canvasSize)
        }
    }

    val referenceHint = referenceStrokes.getOrNull(state.previousDrawnStrokes.size)

    val isComplete = state.previousDrawnStrokes.size == referenceStrokes.size

    val drawingModifier = if (isComplete) Modifier
    else Modifier.drawingDetector(
        points = drawnStroke,
        onGestureComplete = {
            onEvent(SessionScreenEvent.StrokeCompleted(drawnStroke.toList(), referenceStrokes))
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
            .dashedBorder(
                width = 2.dp,
                color = Theme.materialColors.primary,
                shape = RoundedCornerShape(11.dp),
                on = 10.dp,
                off = 10.dp,
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
                previousDrawnStrokes = state.previousDrawnStrokes,
                ongoingStroke = drawnStroke,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .aspectRatio(1f)
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

    AppThemeWrapper(themeMode) {
        Column {
            GraphicSketcher(
                drawReference = true,
                drawHint = true,
                state = mockState,
            )

            GraphicSketcher(
                drawReference = false,
                drawHint = true,
                state = mockState,
            )

            GraphicSketcher(
                drawReference = false,
                drawHint = false,
                state = mockState,
            )
        }
    }
}
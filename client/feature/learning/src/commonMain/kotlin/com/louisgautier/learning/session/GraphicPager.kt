package com.louisgautier.learning.session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.TransformStroke
import com.louisgautier.designsystem.ai.Gray400
import com.louisgautier.designsystem.ai.Green700
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.Reset
import com.louisgautier.designsystem.modifier.dashedBorder
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.learning.drawing.drawingDetector
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun GraphicSketcher(
    questionState: SessionViewModel.QuestionState,
    modifier: Modifier = Modifier,
    drawReference: Boolean = false,
    drawHint: Boolean = false,
    onEvent: (SessionEvent) -> Unit = {},
) {
    val graphic = questionState.question.graphics
    val sketcherState = questionState.sketcherState

    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }
    val drawnStroke = remember { mutableStateListOf<Offset>() }

    val referenceStrokes = remember(graphic.code, canvasSize) {
        graphic.medians.map { stroke ->
            TransformStroke.transformOffset(stroke.points.map { Offset(it.x, it.y) }, canvasSize)
        }
    }

    val referenceHint = referenceStrokes.getOrNull(sketcherState.ongoingStrokeIndex)

    val isComplete = sketcherState.ongoingStrokeIndex == referenceStrokes.size

    val drawingModifier = if (isComplete) Modifier
    else Modifier.drawingDetector(drawnStroke) {
        onEvent(SessionEvent.StrokeCompleted(drawnStroke.toList(), referenceStrokes))
        drawnStroke.clear()
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .dashedBorder(
                width = 2.dp,
                color = Green700,
                shape = RoundedCornerShape(11.dp),
                on = 10.dp,
                off = 10.dp,
            ),
    ) {
        Box {
            this@Card.AnimatedVisibility(
                visible = sketcherState.previousDrawnStrokes.isNotEmpty(),
                label = "Reset btn visibility",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
            ) {
                ResetButton(onClick = { onEvent(SessionEvent.Reset) })
            }

            DrawableArea(
                referenceStrokes = referenceStrokes
                    .takeIf { drawReference }
                    .orEmpty(),
                referenceHint = referenceHint
                    ?.takeIf { drawHint }
                    .orEmpty(),
                previousDrawnStrokes = sketcherState.previousDrawnStrokes,
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

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(38.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Gray400,
            contentColor = Color.White
        ),
        shape = ShapeDefaults.button()
    ) {
        Icon(
            imageVector = AppIcon.Reset,
            contentDescription = "reset drawing",
            modifier = Modifier
                .padding(8.dp)
                .graphicsLayer { scaleX = -1f }
        )
    }
}

class DifficultyPreviewParameter() : PreviewParameterProvider<Difficulty> {
    override val values: Sequence<Difficulty>
        get() = Difficulty.entries.asSequence()
}

@Preview
@Composable
fun GraphicPagerPreview(
    @PreviewParameter(DifficultyPreviewParameter::class) difficulty: Difficulty
) {
    GraphicSketcher(
        drawReference = difficulty != Difficulty.HARD,
        drawHint = difficulty == Difficulty.EASY,
        questionState = SessionViewModel.QuestionState(
            question = previewDictionaryWithGraphic,
            sketcherState = SessionViewModel.GraphicSketcherState(
                ongoingStrokeIndex = 0,
                previousDrawnStrokes = emptyList(),
                drawnStroke = emptyList()
            ),
        ),
        onEvent = {},
    )
}
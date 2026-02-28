package com.louisgautier.composeApp.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import com.louisgautier.apicontracts.dto.Point
import com.louisgautier.apicontracts.dto.Stroke
import com.louisgautier.composeApp.design.previewGraphic
import com.louisgautier.domain.model.StrokeComparisonResult
import learn_chinese.client.composeapp.generated.resources.Res
import learn_chinese.client.composeapp.generated.resources.ic_rounded_arrow_cool_down
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.drawscope.Stroke as AndroidStroke

@Composable
fun DrawableArea(
    medians: List<Stroke>,
    drawReference: Boolean,
    drawHint: Boolean,
    modifier: Modifier = Modifier,
    onStartDrawing: () -> Unit = {},
    onComplete: (List<Stroke>, StrokeComparisonResult) -> Unit = { _, _ -> },
) {
    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }
    //All drawn strokes from user's input
    var allDrawnStrokes = remember { mutableStateListOf<List<Offset>>() }
    // Last drawn points from user's input
    val drawnStroke = remember { mutableStateListOf<Offset>() }
    //Current median stroke index to be drawn
    var currentStroke by remember { mutableIntStateOf(0) }

    // All the points for a complete drawing,
    val points = medians.map { stroke ->
        val offset = stroke.points.map { point -> Offset(point.x, point.y) }
        TransformStroke.transformOffset(offset, canvasSize)
    }

    if (allDrawnStrokes.isNotEmpty()) {
        onStartDrawing()
    }

    //Get current line to be shown as hint. null if all lines have been drawn
    val currentLinePoints = points.getOrNull(currentStroke)

    //Drawing helpers
    val hintIcon = painterResource(Res.drawable.ic_rounded_arrow_cool_down)
    val textMeasurer = rememberTextMeasurer()

    if (currentStroke == points.size) {
        val parsed = allDrawnStrokes.map { s ->
            Stroke(s.map { point -> Point(point.x, point.y) })
        }
        val output = analyzeUserDrawing(points, allDrawnStrokes)
        onComplete(parsed, output)
    }

    val drawingModifier = if (currentStroke == points.size) Modifier
    else Modifier.drawingDetector(drawnStroke) {
        allDrawnStrokes = allDrawnStrokes.apply { add(drawnStroke.toList()) }
        drawnStroke.clear()
        currentStroke += 1
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .onGloballyPositioned { coordinates -> canvasSize = coordinates.size }
            .then(drawingModifier)
    ) {

        if (drawReference) {
            points.forEach { drawStroke(it) }
        }

        if (drawHint) {
            currentLinePoints?.let {
                drawDashedLineWithFilledArrow(it)
            }
        }

        //Previous drawn stroke
        allDrawnStrokes.forEach {
            val path = it.pointsToPath()
            drawPath(
                path = path,
                color = DrawableAreaDefault.STROKE_USER_COLOR,
                style = AndroidStroke(
                    width = DrawableAreaDefault.STROKE_WIDTH,
                    cap = StrokeCap.Round
                )
            )
        }

        // Draw the ongoing path
        if (drawnStroke.isNotEmpty()) {
            val livePath = drawnStroke.pointsToPath()
            drawPath(
                path = livePath,
                color = DrawableAreaDefault.STROKE_USER_COLOR,
                style = AndroidStroke(
                    width = DrawableAreaDefault.STROKE_WIDTH,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}

@Preview
@Composable
fun DrawableAreaPreview(
) {
    DrawableArea(
        medians = previewGraphic.medians,
        drawReference = true,
        drawHint = true,
        onComplete = { _, _ -> }
    )
}
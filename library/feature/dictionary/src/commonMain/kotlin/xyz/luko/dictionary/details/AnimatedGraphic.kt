package xyz.luko.dictionary.details

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import xyz.luko.designsystem.drawing.TransformStroke
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.domain.model.Graphic
import xyz.luko.domain.previewGraphic

@Composable
internal fun AnimatedGraphic(
    graphic: Graphic,
    modifier: Modifier = Modifier,
) {
    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }

    val strokes = TransformStroke.projectToCanvas(graphic.strokes, canvasSize)
    val medians = graphic.medians.map { s -> s.points.map { p -> Offset(p.x, p.y) } }
    val strokeColor = Theme.materialColors.onBackground

    // true in preview, false at runtime
    val isPreview = LocalInspectionMode.current
    var started by remember { mutableStateOf(isPreview) }
    val progress by animateFloatAsState(
        targetValue = if (started) strokes.size.toFloat() else 0f,
        animationSpec = tween(3000, easing = LinearEasing),
    )

    LaunchedEffect(Unit) {
        if (!isPreview) {
            delay(500)
            started = true
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier =
                Modifier
                    .width(150.dp)
                    .aspectRatio(1f)
                    .onGloballyPositioned { coordinates -> canvasSize = coordinates.size },
        ) {
            strokes.forEachIndexed { index, path ->

                val strokeProgress = (progress - index).coerceIn(0f, 1f)

                val currentMedian = TransformStroke.projectToCanvas(medians[index], canvasSize)
                if (strokeProgress > 0f) {
                    clipPath(path) {
                        val medianPath = Path()
                        val totalPoints = currentMedian.size
                        val currentIndex = (strokeProgress * (totalPoints - 1)).toInt()
                        val fraction = (strokeProgress * (totalPoints - 1)) - currentIndex

                        medianPath.moveTo(
                            currentMedian[0].x,
                            currentMedian[0].y,
                        )

                        for (i in 1..currentIndex) {
                            medianPath.lineTo(
                                currentMedian[i].x,
                                currentMedian[i].y,
                            )
                        }

                        if (currentIndex < totalPoints - 1) {
                            val current = currentMedian[currentIndex]
                            val next = currentMedian[currentIndex + 1]
                            medianPath.lineTo(
                                current.x + (next.x - current.x) * fraction,
                                current.y + (next.y - current.y) * fraction,
                            )
                        }

                        drawPath(
                            path = medianPath,
                            color = strokeColor,
                            style = Stroke(width = 100f, cap = StrokeCap.Round),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAnimatedGraphic(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        AnimatedGraphic(
            graphic = previewGraphic,
        )
    }
}

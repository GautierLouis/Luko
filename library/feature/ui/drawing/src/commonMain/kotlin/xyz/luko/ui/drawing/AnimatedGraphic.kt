package xyz.luko.ui.drawing

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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import xyz.luko.domain.model.Stroke
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.drawing.internal.StrokeTransformer
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.ui.graphics.drawscope.Stroke as ComposeStroke

@Composable
fun AnimatedGraphic(
    strokes: List<String>,
    medians: List<Stroke>,
    modifier: Modifier = Modifier,
) {
    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }
    val transformer = remember { StrokeTransformer() }
    val strokeColor = Theme.materialColors.onBackground

    val strokePaths = remember(strokes, canvasSize) {
        transformer.toCanvasPaths(strokes, canvasSize)
    }

    val medianPaths = remember(medians, canvasSize) {
        medians.map { transformer.toCanvasPath(it, canvasSize) }
    }

    // true in preview, false at runtime
    val isPreview = LocalInspectionMode.current
    var started by remember { mutableStateOf(isPreview) }
    val progress by animateFloatAsState(
        targetValue = if (started) strokePaths.size.toFloat() else 0f,
        animationSpec = tween(3000, easing = LinearEasing),
    )

    LaunchedEffect(Unit) {
        if (!isPreview) {
            delay(500.milliseconds)
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
            strokePaths.forEachIndexed { index, strokePath ->
                val strokeProgress = (progress - index).coerceIn(0f, 1f)
                if (strokeProgress <= 0f) return@forEachIndexed

                val pathMeasure = PathMeasure().apply { setPath(medianPaths[index], false) }
                val segmentPath = Path()
                pathMeasure.getSegment(
                    startDistance = 0f,
                    stopDistance = pathMeasure.length * strokeProgress,
                    destination = segmentPath,
                    startWithMoveTo = true,
                )

                clipPath(strokePath) {
                    drawPath(
                        path = segmentPath,
                        color = strokeColor,
                        style = ComposeStroke(width = 100f, cap = StrokeCap.Round),
                    )
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
            strokes = PreviewProvider.dictionary.strokes,
            medians = PreviewProvider.dictionary.medians,
        )
    }
}

package xyz.luko.ui.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.drawing.internal.StrokeTransformer

@Composable
fun SvgGraphic(
    strokes: List<String>,
    modifier: Modifier = Modifier,
    color: Color = Theme.materialColors.onSurface
) {

    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }
    val transformer = remember { StrokeTransformer() }

    val strokePaths = remember(strokes, canvasSize) {
        transformer.toCanvasPaths(strokes, canvasSize)
    }

    Canvas(
        modifier = modifier
            .onGloballyPositioned { coordinates -> canvasSize = coordinates.size },
    ) {
        strokePaths.forEach { path ->
            drawPath(
                path = path,
                color = color,
                style = Fill
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSvgGraphic(
    @PreviewParameter(provider = ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        SvgGraphic(
            PreviewProvider.dictionary.strokes,
            modifier = Modifier.size(200.dp)
        )
    }
}

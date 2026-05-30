package xyz.luko.learning.drawing

import androidx.compose.ui.unit.dp

internal object DrawableAreaDefault {
    val STROKE_WIDTH = 12.dp
    val STROKE_DASH_WIDTH = 9.dp
    val DASH_WIDTH = STROKE_WIDTH * 1.5f   // 18.dp — dash slightly longer than stroke
    val DASH_GAP = STROKE_WIDTH * 2f       // 24.dp — gap twice the stroke width
    val DASH_PHASE = STROKE_WIDTH * 0.5f   // 6.dp
    val ARROW_SIZE = 8.dp
}

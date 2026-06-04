package xyz.luko.learning.congratulation.streak.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

internal fun Modifier.stroke(enable: Boolean, color: Color): Modifier {
    return this.drawBehind {
        if (enable) {
            drawLine(
                color = color,
                start = Offset(
                    x = 0f,
                    y = size.height / 2
                ),
                end = Offset(
                    x = size.width,
                    y = size.height / 2
                ),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

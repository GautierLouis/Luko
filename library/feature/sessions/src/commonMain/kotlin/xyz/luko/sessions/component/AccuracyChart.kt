package xyz.luko.sessions.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun AccuracyChart(
    accuracies: List<Float>,
    modifier: Modifier = Modifier,
) {
    val limitedAccuracies = accuracies.takeLast(10)
    val guidelineColor = Theme.materialColors.onSurface.copy(alpha = 0.08f)
    val guidelineLabelColor = Theme.materialColors.onSurface.copy(alpha = 0.4f)

    Column(modifier = modifier.fillMaxWidth()) {
        Canvas(modifier = Modifier.fillMaxWidth().height(80.dp)) {
            if (limitedAccuracies.isEmpty()) return@Canvas

            val totalSlots = 10
            val gapRatio = 0.15f
            val barWidth = size.width / (totalSlots + (totalSlots - 1) * gapRatio)
            val gap = barWidth * gapRatio
            val cornerRadius = CornerRadius(4.dp.toPx())

            // Guidelines at 25, 50, 75, 100
            listOf(25f, 50f, 75f, 100f).forEach { level ->
                val y = size.height - (level / 100f) * size.height
                drawLine(
                    color = guidelineColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(
                            4.dp.toPx(),
                            4.dp.toPx()
                        )
                    )
                )
            }

            limitedAccuracies.forEachIndexed { index, value ->
                val color = when {
                    value >= 80f -> Color(0xFF97C459)
                    value >= 50f -> Color(0xFFEF9F27)
                    else -> Color(0xFFF09595)
                }
                val barHeight = (value / 100f) * size.height
                val x = index * (barWidth + gap)
                val y = size.height - barHeight

                val path = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = x,
                            top = y,
                            right = x + barWidth,
                            bottom = size.height,
                            topLeftCornerRadius = cornerRadius,
                            topRightCornerRadius = cornerRadius,
                            bottomLeftCornerRadius = CornerRadius.Zero,
                            bottomRightCornerRadius = CornerRadius.Zero,
                        )
                    )
                }
                drawPath(path, color)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "first",
                fontSize = 11.sp,
                color = guidelineLabelColor
            )
            Text(
                text = "last",
                fontSize = 11.sp,
                color = guidelineLabelColor
            )
        }
    }
}

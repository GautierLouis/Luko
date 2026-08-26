package xyz.luko.home.item

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.luko.domain.repository.DownloadState
import xyz.luko.home.strings.learningStrings
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun DownloadCard(
    state: DownloadState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier.height(52.dp)
            .then(
                if (state is DownloadState.Downloading) {
                    Modifier.animatedBorder()
                } else {
                    Modifier.border(1.dp, Theme.materialColors.error, shape)
                }
            )
            .background(
                color = Theme.materialColors.surfaceContainer,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is DownloadState.Downloading -> {
                Text(
                    text = Theme.learningStrings.ongoingSync,
                    style = Theme.typography.labelMedium,
                    color = Theme.materialColors.onSurface
                )
            }

            is DownloadState.Failed -> {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = Theme.learningStrings.failedSync,
                        style = Theme.typography.labelMedium,
                        color = Theme.materialColors.onSurface
                    )
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Theme.materialColors.error,
                            contentColor = Theme.materialColors.onError
                        )
                    ) {
                        Text(
                            Theme.learningStrings.retrySync,
                            style = Theme.typography.labelMedium
                        )
                    }
                }
            }

            else -> Unit // Idle / Downloaded shouldn't reach this card
        }
    }
}

private fun Modifier.animatedBorder(
    strokeWidth: Dp = 2.dp,
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "border_rotation")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing)
        ),
        label = "progress"
    )

    val accent = Theme.materialColors.primary

    drawWithCache {
        val cornerRadiusPx = 8.dp.toPx()
        val strokePx = strokeWidth.toPx()
        val c = size.center

        // base pattern: position(0.0) color == position(1.0) color -> closes cleanly
        val basePositions = listOf(0.0f, 0.2f, 0.5f, 0.8f, 1.0f)
        val baseColors = listOf(
            accent.copy(alpha = 0f),
            accent.copy(alpha = 0.2f),
            accent,
            accent.copy(alpha = 0.2f),
            accent.copy(alpha = 0f),
        )

        fun sampleBase(pos: Float): Color {
            val p = pos.mod(1f)
            val idx = basePositions.indexOfLast { it <= p }.coerceAtLeast(0)
            val nextIdx = (idx + 1).coerceAtMost(basePositions.lastIndex)
            if (idx == nextIdx) return baseColors[idx]
            val range = basePositions[nextIdx] - basePositions[idx]
            val t = if (range == 0f) 0f else (p - basePositions[idx]) / range
            return lerp(baseColors[idx], baseColors[nextIdx], t)
        }

        // sample a fixed number of evenly spaced stops -> positions never move,
        // only the colors shift, so there's nothing to re-sort and no seam
        val stopCount = 32
        val rotatedStops = (0..stopCount).map { i ->
            val pos = i / stopCount.toFloat()
            pos to sampleBase((pos - progress).mod(1f))
        }

        onDrawWithContent {
            drawContent()

            drawRoundRect(
                brush = Brush.sweepGradient(
                    colorStops = rotatedStops.toTypedArray(),
                    center = c
                ),
                cornerRadius = CornerRadius(cornerRadiusPx),
                style = Stroke(width = strokePx)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDownloadCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        DownloadCard(
//            DownloadState.Failed(Throwable("Test")),
            DownloadState.Downloading,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

package xyz.luko.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import xyz.luko.ui.designsystem.theme.Theme
import kotlin.math.floor

@Composable
internal fun PageIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier.Companion,
) {
    val pageCount = pagerState.pageCount

    val dotSize = 8.dp
    val dotSpacing = 8.dp

    val activeColor = Theme.materialColors.primary
    val inactiveColor = Theme.materialColors.primary.copy(alpha = 0.3f)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .height(dotSize)
                .width(dotSize * pageCount + dotSpacing * (pageCount - 1))
        ) {
            val radius = dotSize.toPx() / 2f
            val step = (dotSize + dotSpacing).toPx()
            val centerY = size.height / 2f

            // static dots underneath
            for (i in 0 until pageCount) {
                drawCircle(
                    color = inactiveColor,
                    radius = radius,
                    center = Offset(radius + step * i, centerY)
                )
            }

            // continuous page position across the whole pager (e.g. 2.35 = 35% between page 2 and 3)
            val exactPage = (pagerState.currentPage + pagerState.currentPageOffsetFraction)
                .coerceIn(0f, (pageCount - 1).toFloat())

            val basePage = floor(exactPage).toInt().coerceIn(0, pageCount - 1)
            val targetPage = (basePage + 1).coerceAtMost(pageCount - 1)
            val frac = (exactPage - basePage).coerceIn(0f, 1f)

            val leftCenterX = radius + step * basePage
            val rightCenterX = radius + step * targetPage

            // first half: front edge stretches toward the next dot, back edge stays put
            // second half: back edge catches up, front edge stays put -> "drop" pinches back together
            val (leftEdge, rightEdge) = when {
                basePage == targetPage -> leftCenterX to leftCenterX
                frac <= 0.5f -> {
                    val t = FastOutSlowInEasing.transform(frac / 0.5f)
                    leftCenterX to lerp(leftCenterX, rightCenterX, t)
                }

                else -> {
                    val t = FastOutSlowInEasing.transform((frac - 0.5f) / 0.5f)
                    lerp(leftCenterX, rightCenterX, t) to rightCenterX
                }
            }

            drawRoundRect(
                color = activeColor,
                topLeft = Offset(leftEdge - radius, centerY - radius),
                size = Size(rightEdge - leftEdge + dotSize.toPx(), dotSize.toPx()),
                cornerRadius = CornerRadius(radius, radius)
            )
        }
    }
}

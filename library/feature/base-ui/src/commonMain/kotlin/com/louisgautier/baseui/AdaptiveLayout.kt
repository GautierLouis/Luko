package com.louisgautier.baseui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.louisgautier.baseui.AdaptiveLayoutOrder.Companion.sort
import com.louisgautier.baseui.AdaptiveLayoutOrientation.COLUMN
import com.louisgautier.baseui.AdaptiveLayoutOrientation.ROW
import com.louisgautier.baseui.device.rememberAdaptiveWindowInfo

@Composable
fun AdaptiveLayout(
    modifier: Modifier = Modifier,
    orientation: AdaptiveLayoutOrientation = if (rememberAdaptiveWindowInfo().isLandscape) ROW else COLUMN,
    spacing: Dp = 8.dp,
    order: AdaptiveLayoutOrder = AdaptiveLayoutOrder.NATURAL,
    content: @Composable () -> Unit,
) {
    val spacingPx = with(LocalDensity.current) { spacing.roundToPx() }

    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        when (orientation) {
            ROW -> {
                val totalSpacing = spacingPx * (measurables.size - 1)
                val itemWidth = (constraints.maxWidth - totalSpacing) / measurables.size
                val itemHeight = measurables.maxOf { it.maxIntrinsicHeight(itemWidth) }

                val placeables =
                    measurables.map {
                        it.measure(
                            constraints.copy(
                                minWidth = itemWidth,
                                maxWidth = itemWidth,
                                minHeight = itemHeight,
                                maxHeight = itemHeight,
                            ),
                        )
                    }

                layout(constraints.maxWidth, itemHeight) {
                    var x = 0
                    placeables.sort(order).forEach {
                        it.placeRelative(x, 0)
                        x += itemWidth + spacingPx
                    }
                }
            }

            COLUMN -> {
                val itemHeight = measurables.maxOf { it.maxIntrinsicHeight(constraints.maxWidth) }

                val placeables =
                    measurables.map {
                        it.measure(
                            constraints.copy(
                                minWidth = constraints.maxWidth,
                                maxWidth = constraints.maxWidth,
                                minHeight = constraints.minHeight,
                                maxHeight = constraints.maxHeight,
                            ),
                        )
                    }

                val totalHeight =
                    placeables.sumOf { it.height } + spacingPx * (measurables.size - 1)

                layout(constraints.maxWidth, totalHeight) {
                    var y = 0
                    placeables.sort(order).forEach {
                        it.placeRelative(0, y)
                        y += itemHeight + spacingPx
                    }
                }
            }
        }
    }
}

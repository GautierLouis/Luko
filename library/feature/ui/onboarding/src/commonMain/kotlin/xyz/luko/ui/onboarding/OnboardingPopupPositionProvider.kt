package xyz.luko.ui.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import xyz.luko.ui.designsystem.onboarding.CutoutArea
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
internal class OnboardingPopupPositionProvider(
    private val cutoutArea: CutoutArea?,
    private val anchorPosition: TooltipAnchorPosition,
    private val gap: Float = 24f,
    private val lastPosition: IntOffset,
    private val updateLastPosition: (IntOffset) -> Unit
) : PopupPositionProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        if (cutoutArea == null) return lastPosition

        val center: Offset
        val top: Float
        val bottom: Float
        val left: Float
        val right: Float

        when (cutoutArea) {
            is CutoutArea.Circle -> {
                center = cutoutArea.center
                top = cutoutArea.center.y - cutoutArea.radius
                bottom = cutoutArea.center.y + cutoutArea.radius
                left = cutoutArea.center.x - cutoutArea.radius
                right = cutoutArea.center.x + cutoutArea.radius
            }

            is CutoutArea.Rounded,
            is CutoutArea.Rectangular -> {
                val rect = when (cutoutArea) {
                    is CutoutArea.Rounded -> cutoutArea.rect
                    is CutoutArea.Rectangular -> cutoutArea.rect
                }
                center = rect.center
                top = rect.top
                bottom = rect.bottom
                left = rect.left
                right = rect.right
            }
        }

        val x: Float
        val y: Float

        when (anchorPosition) {
            TooltipAnchorPosition.Above -> {
                x = center.x - popupContentSize.width / 2
                y = top - popupContentSize.height - gap
            }

            TooltipAnchorPosition.Below -> {
                x = center.x - popupContentSize.width / 2
                y = bottom + gap
            }

            TooltipAnchorPosition.Left -> {
                x = left - popupContentSize.width - gap
                y = center.y - popupContentSize.height / 2
            }

            TooltipAnchorPosition.Right -> {
                x = right + gap
                y = center.y - popupContentSize.height / 2
            }

            else -> {
                x = center.x - popupContentSize.width / 2
                y = top - popupContentSize.height - gap
            }
        }

        val result = IntOffset(
            x = x.coerceIn(0f, (windowSize.width - popupContentSize.width).toFloat()).roundToInt(),
            y = y.coerceIn(0f, (windowSize.height - popupContentSize.height).toFloat())
                .roundToInt(),
        )
        updateLastPosition(result)
        return result
    }
}

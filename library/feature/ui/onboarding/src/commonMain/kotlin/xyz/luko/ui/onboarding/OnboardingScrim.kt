package xyz.luko.ui.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import xyz.luko.ui.designsystem.onboarding.CutoutArea
import xyz.luko.ui.designsystem.onboarding.TooltipData
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun OnboardingScrim(
    current: TooltipData,
) {
    val scrimColor = Theme.materialColors.scrim.copy(alpha = .55f)

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val path = Path().apply {
            fillType = PathFillType.EvenOdd
            addRect(Rect(0f, 0f, size.width, size.height))
            when (val h = current.cutoutArea) {
                is CutoutArea.Circle -> addOval(Rect(h.center, h.radius))
                is CutoutArea.Rounded -> addRoundRect(RoundRect(h.rect, CornerRadius(h.radius)))
                is CutoutArea.Rectangular -> addRect(h.rect)
            }
        }
        drawPath(path, scrimColor)
    }
}

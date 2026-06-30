package xyz.luko.ui.designsystem.onboarding

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

sealed interface CutoutArea {
    data class Circle(val center: Offset, val radius: Float) : CutoutArea
    data class Rounded(val rect: Rect, val radius: Float) : CutoutArea
    data class Rectangular(val rect: Rect) : CutoutArea
}

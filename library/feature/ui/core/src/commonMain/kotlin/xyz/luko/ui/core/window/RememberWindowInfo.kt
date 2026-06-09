package xyz.luko.ui.core.window

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

@Composable
fun rememberWindowInfo(): WindowInfo {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    fun isAtLeast(breakPoint: Int) =
        windowSizeClass.isWidthAtLeastBreakpoint(breakPoint)

    return remember(windowSizeClass) {
        when {
            !isAtLeast(WIDTH_DP_MEDIUM_LOWER_BOUND) -> WindowInfo.COMPACT
            !isAtLeast(WIDTH_DP_EXPANDED_LOWER_BOUND) -> WindowInfo.MEDIUM
            else -> WindowInfo.EXPANDED
        }
    }
}

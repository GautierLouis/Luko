package xyz.luko.ui.core.window

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

@Composable
fun rememberWindowInfo(): WindowInfo {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    fun isWidthAtLeast(breakPoint: Int) =
        windowSizeClass.isWidthAtLeastBreakpoint(breakPoint)

    fun isHeightAtLeast(breakPoint: Int) =
        windowSizeClass.isHeightAtLeastBreakpoint(breakPoint)

    return remember(windowSizeClass) {
        when {
            !isHeightAtLeast(HEIGHT_DP_MEDIUM_LOWER_BOUND) -> WindowInfo.HEIGHT_COMPACT
            !isWidthAtLeast(WIDTH_DP_MEDIUM_LOWER_BOUND) -> WindowInfo.WIDTH_COMPACT
            !isWidthAtLeast(WIDTH_DP_EXPANDED_LOWER_BOUND) -> WindowInfo.MEDIUM
            else -> WindowInfo.EXPANDED
        }
    }
}

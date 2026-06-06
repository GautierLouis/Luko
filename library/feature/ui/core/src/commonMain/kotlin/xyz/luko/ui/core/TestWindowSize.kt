package xyz.luko.ui.core

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class TestWindowSize(
    val width: Dp,
    val height: Dp,
) {
    // Android phones portrait
    SMALL_PHONE_PORTRAIT(360.dp, 640.dp),
    MEDIUM_PHONE_PORTRAIT(390.dp, 844.dp),
    LARGE_PHONE_PORTRAIT(430.dp, 932.dp),

    // Android phones landscape
    SMALL_PHONE_LANDSCAPE(640.dp, 360.dp),
    MEDIUM_PHONE_LANDSCAPE(844.dp, 390.dp),
    LARGE_PHONE_LANDSCAPE(932.dp, 430.dp),

    // Android tablets
    TABLET_PORTRAIT(800.dp, 1280.dp),
    TABLET_LANDSCAPE(1280.dp, 800.dp),

    // iPhone
    IPHONE_SE_PORTRAIT(375.dp, 667.dp),
    IPHONE_15_PORTRAIT(393.dp, 852.dp),
    IPHONE_15_LANDSCAPE(852.dp, 393.dp),
    IPHONE_15_PRO_MAX_PORTRAIT(430.dp, 932.dp),
    IPHONE_15_PRO_MAX_LANDSCAPE(932.dp, 430.dp),

    DESKTOP(800.dp, 600.dp),
}

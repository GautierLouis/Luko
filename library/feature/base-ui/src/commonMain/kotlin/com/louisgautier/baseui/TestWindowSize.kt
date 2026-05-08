package com.louisgautier.baseui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ConsistentCopyVisibility
data class TestWindowSize internal constructor(val width: Dp, val height: Dp, val name: String)

object TestSizes {
    // Android phones portrait
    val SMALL_PHONE_PORTRAIT =
        TestWindowSize(360.dp, 640.dp, "small_phone_portrait")       // Galaxy A03
    val MEDIUM_PHONE_PORTRAIT =
        TestWindowSize(390.dp, 844.dp, "medium_phone_portrait")     // Pixel 7
    val LARGE_PHONE_PORTRAIT =
        TestWindowSize(430.dp, 932.dp, "large_phone_portrait")       // Pixel 7 Pro

    // Android phones landscape
    val SMALL_PHONE_LANDSCAPE = TestWindowSize(640.dp, 360.dp, "small_phone_landscape")
    val MEDIUM_PHONE_LANDSCAPE = TestWindowSize(844.dp, 390.dp, "medium_phone_landscape")
    val LARGE_PHONE_LANDSCAPE = TestWindowSize(932.dp, 430.dp, "large_phone_landscape")

    // Android tablets portrait
    val TABLET_PORTRAIT =
        TestWindowSize(800.dp, 1280.dp, "tablet_portrait")                // Pixel Tablet
    val TABLET_LANDSCAPE = TestWindowSize(1280.dp, 800.dp, "tablet_landscape")

    // iPhone
    val IPHONE_SE_PORTRAIT = TestWindowSize(375.dp, 667.dp, "iphone_se_portrait")
    val IPHONE_15_PORTRAIT = TestWindowSize(393.dp, 852.dp, "iphone_15_portrait")
    val IPHONE_15_LANDSCAPE = TestWindowSize(852.dp, 393.dp, "iphone_15_landscape")
    val IPHONE_15_PRO_MAX_PORTRAIT = TestWindowSize(430.dp, 932.dp, "iphone_15_pro_max_portrait")
    val IPHONE_15_PRO_MAX_LANDSCAPE = TestWindowSize(932.dp, 430.dp, "iphone_15_pro_max_landscape")

    val ALL = listOf(
        SMALL_PHONE_PORTRAIT, MEDIUM_PHONE_PORTRAIT, LARGE_PHONE_PORTRAIT,
        SMALL_PHONE_LANDSCAPE, MEDIUM_PHONE_LANDSCAPE, LARGE_PHONE_LANDSCAPE,
        TABLET_PORTRAIT, TABLET_LANDSCAPE,
        IPHONE_SE_PORTRAIT, IPHONE_15_PORTRAIT, IPHONE_15_LANDSCAPE,
        IPHONE_15_PRO_MAX_PORTRAIT, IPHONE_15_PRO_MAX_LANDSCAPE
    )
}
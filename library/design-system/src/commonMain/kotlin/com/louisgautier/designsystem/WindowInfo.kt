package com.louisgautier.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.theme.AdaptiveWindowInfo
import com.louisgautier.designsystem.theme.DeviceOrientation
import com.louisgautier.designsystem.theme.DeviceType


@Composable
fun rememberAdaptiveWindowInfo(): AdaptiveWindowInfo {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val screenWidth = with(density) { windowInfo.containerSize.width.toDp() }
    val screenHeight = with(density) { windowInfo.containerSize.height.toDp() }
    val smallestWidth = minOf(screenWidth, screenHeight)

    val deviceType = if (smallestWidth >= 600.dp) DeviceType.TABLET else DeviceType.PHONE
    val orientation =
        if (screenWidth > screenHeight) DeviceOrientation.LANDSCAPE else DeviceOrientation.PORTRAIT

    return remember(windowInfo.containerSize) {
        AdaptiveWindowInfo(deviceType, orientation)
    }
}
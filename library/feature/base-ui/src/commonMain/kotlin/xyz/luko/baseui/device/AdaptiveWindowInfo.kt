package xyz.luko.baseui.device

import androidx.compose.ui.unit.Dp

data class AdaptiveWindowInfo(
    val deviceType: DeviceType,
    val orientation: DeviceOrientation,
    val width: Dp,
    val height: Dp
) {
    val isPhone = deviceType == DeviceType.PHONE
    val isTablet = deviceType == DeviceType.TABLET

    val isLandscape = orientation == DeviceOrientation.LANDSCAPE
    val isPortrait = orientation == DeviceOrientation.PORTRAIT

    val isPhoneLandscape = isPhone && isLandscape
    val isPhonePortrait = isPhone && isPortrait
    val isTabletLandscape = isTablet && isLandscape
    val isTabletPortrait = isTablet && isPortrait
}

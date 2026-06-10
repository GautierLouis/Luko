package xyz.luko.designsystem.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@PreviewPhone
@PreviewPhoneLandscape
@PreviewTablet
@PreviewTabletPortrait
@PreviewFoldable
@PreviewFoldableLandscape
annotation class PreviewScreen

@Preview(device = Devices.PHONE, name = "Phone")
annotation class PreviewPhone

@Preview(device = "spec:width=891dp,height=411dp", name = "Phone landscape")
annotation class PreviewPhoneLandscape

@Preview(device = Devices.TABLET, name = "Tablet")
annotation class PreviewTablet

@Preview(device = "spec:width=800dp,height=1280dp,dpi=240", name = "Tablet portrait")
annotation class PreviewTabletPortrait

@Preview(device = Devices.FOLDABLE, name = "Foldable")
annotation class PreviewFoldable

@Preview(device = "spec:width=841dp,height=673dp", name = "Foldable landscape")
annotation class PreviewFoldableLandscape

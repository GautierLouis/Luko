package com.louisgautier.designsystem.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@PhonePreview
@PhoneLandscapePreview
@TabletPreview
@TabletPortraitPreview
@FoldablePreview
@FoldableLandscapePreview
annotation class ScreenPreview

@Preview(device = Devices.PHONE, name = "Phone")
annotation class PhonePreview

@Preview(device = "spec:width=891dp,height=411dp", name = "Phone landscape")
annotation class PhoneLandscapePreview

@Preview(device = Devices.TABLET, name = "Tablet")
annotation class TabletPreview

@Preview(device = "spec:width=800dp,height=1280dp,dpi=240", name = "Tablet portrait")
annotation class TabletPortraitPreview

@Preview(device = Devices.FOLDABLE, name = "Foldable")
annotation class FoldablePreview

@Preview(device = "spec:width=841dp,height=673dp", name = "Foldable landscape")
annotation class FoldableLandscapePreview




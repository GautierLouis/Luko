package com.louisgautier.designsystem.token.color.palette

import androidx.compose.ui.graphics.Color

internal object MaterialPalette {
    // --- Green (Primary) ---
    val Green50 = Color(0xFFBDF0B3) // primaryContainerLight / onPrimaryContainerDark
    val Green100 = Color(0xFFA2D398) // inversePrimaryLight / primaryDark
    val Green200 = Color(0xFF9ECF95) // primaryContainerDarkHighContrast
    val Green300 = Color(0xFF6E9C66) // primaryContainerDarkMediumContrast
    val Green400 = Color(0xFF4B7846) // primaryContainerLightMediumContrast
    val Green500 = Color(0xFF3D6838) // primaryLight / inversePrimaryDark
    val Green600 =
        Color(0xFF275225) // primaryContainerLightHighContrast / onPrimaryContainerDark... wait
    val Green700 =
        Color(0xFF265124) // inversePrimaryDarkMediumContrast / inversePrimaryDarkHighContrast
    val Green800 = Color(0xFF255023) // onPrimaryContainerLight / primaryContainerDark
    val Green900 = Color(0xFF133F13) // primaryLightMediumContrast
    val Green950 = Color(0xFF07340A) // primaryLightHighContrast
    val Green980 = Color(0xFF0C380E) // onPrimaryDark
    val Green990 = Color(0xFF002D04) // onPrimaryDarkMediumContrast
    val Green995 = Color(0xFF000F01) // onPrimaryContainerDarkHighContrast

    // --- Mint (Primary container / inverse surface tones) ---
    val Mint50 = Color(0xFFCBFEBF) // primaryDarkHighContrast
    val Mint100 = Color(0xFFB7EAAD) // primaryDarkMediumContrast

    // --- Sage (Secondary) ---
    val Sage50 = Color(0xFFE3F5DB) // secondaryDarkHighContrast
    val Sage100 = Color(0xFFD6E8CE) // secondaryContainerLight / onSecondaryContainerDark
    val Sage200 = Color(0xFFD0E2C8) // secondaryDarkMediumContrast
    val Sage300 = Color(0xFFBACCB3) // secondaryDark
    val Sage400 = Color(0xFFB6C8AF) // secondaryContainerDarkHighContrast
    val Sage500 = Color(0xFF85957F) // secondaryContainerDarkMediumContrast
    val Sage600 = Color(0xFF61725C) // secondaryContainerLightMediumContrast
    val Sage700 = Color(0xFF53634E) // secondaryLight
    val Sage800 = Color(0xFF3C4B38) // onSecondaryContainerLight / secondaryContainerDark
    val Sage850 = Color(0xFF3E4D3A) // secondaryContainerLightHighContrast
    val Sage900 = Color(0xFF2B3A28) // secondaryLightMediumContrast
    val Sage950 = Color(0xFF263423) // onSecondaryDark
    val Sage970 = Color(0xFF21301F) // secondaryLightHighContrast
    val Sage990 = Color(0xFF1B2919) // onSecondaryDarkMediumContrast

    // --- Teal (Tertiary) ---
    val Teal50 = Color(0xFFC9F9FD) // tertiaryDarkHighContrast
    val Teal100 = Color(0xFFBCEBF0) // tertiaryContainerLight / onTertiaryContainerDark
    val Teal200 = Color(0xFFB6E5E9) // tertiaryDarkMediumContrast
    val Teal300 = Color(0xFFA0CFD3) // tertiaryDark
    val Teal400 = Color(0xFF9CCBCF) // tertiaryContainerDarkHighContrast
    val Teal500 = Color(0xFF6B989D) // tertiaryContainerDarkMediumContrast
    val Teal600 = Color(0xFF477479) // tertiaryContainerLightMediumContrast
    val Teal700 = Color(0xFF38656A) // tertiaryLight
    val Teal800 = Color(0xFF1E4D52) // onTertiaryContainerLight / tertiaryContainerDark
    val Teal850 = Color(0xFF215054) // tertiaryContainerLightHighContrast
    val Teal900 = Color(0xFF073C41) // tertiaryLightMediumContrast
    val Teal950 = Color(0xFF003236) // tertiaryLightHighContrast
    val Teal970 = Color(0xFF00363B) // onTertiaryDark
    val Teal990 = Color(0xFF002B2E) // onTertiaryDarkMediumContrast

    // --- Red (Error) ---
    val Red50 = Color(0xFFFFECE9) // errorDarkHighContrast
    val Red100 = Color(0xFFFFDAD6) // errorContainerLight / onErrorContainerDark
    val Red200 = Color(0xFFFFD2CC) // errorDarkMediumContrast
    val Red300 = Color(0xFFFFB4AB) // errorDark
    val Red400 = Color(0xFFFFAEA4) // errorContainerDarkHighContrast
    val Red500 = Color(0xFFFF5449) // errorContainerDarkMediumContrast
    val Red600 = Color(0xFFCF2C27) // errorContainerLightMediumContrast
    val Red700 = Color(0xFFBA1A1A) // errorLight
    val Red800 = Color(0xFF98000A) // onErrorContainerLight / errorContainerDark
    val Red900 = Color(0xFF93000A) // onErrorContainerLight (alt)
    val Red950 = Color(0xFF740006) // errorLightMediumContrast
    val Red970 = Color(0xFF690005) // onErrorDark
    val Red980 = Color(0xFF600004) // errorLightHighContrast
    val Red990 = Color(0xFF540003) // onErrorDarkMediumContrast
    val Red995 = Color(0xFF220001) // onErrorContainerDarkHighContrast

    // --- Neutral (Surface / Background) ---
    val NeutralWhite = Color(0xFFFFFFFF)
    val Neutral50 = Color(0xFFF7FBF1) // backgroundLight / surfaceLight / surfaceBrightLight
    val Neutral100 = Color(0xFFF2F5EB) // surfaceContainerLowLight
    val Neutral150 = Color(0xFFEFF2E8) // inverseOnSurfaceLight / inverseOnSurfaceDark (alt)
    val Neutral200 = Color(0xFFECEFE6) // surfaceContainerLight
    val Neutral250 = Color(0xFFE6E9E0) // surfaceContainerHighLight
    val Neutral300 =
        Color(0xFFE0E4DA) // surfaceContainerHighestLight / onBackgroundDark / onSurfaceDark / inverseSurfaceDark
    val Neutral350 = Color(0xFFDBDED5) // surfaceContainerHighLightMediumContrast
    val Neutral400 = Color(0xFFD8DBD2) // surfaceDimLight / onSurfaceVariantDarkMediumContrast
    val Neutral450 = Color(0xFFD2D6CD) // surfaceContainerHighLightHighContrast
    val Neutral500 = Color(0xFFCFD3CA) // surfaceContainerHighestLightMediumContrast
    val Neutral550 =
        Color(0xFFC4C8BF) // surfaceDimLightMediumContrast / surfaceContainerHighestLightHighContrast
    val Neutral600 = Color(0xFFB6BAB1) // surfaceDimLightHighContrast
    val Neutral700 = Color(0xFF8C9388) // outlineDark
    val Neutral750 = Color(0xFF73796F) // outlineLight
    val Neutral800 =
        Color(0xFF42493F) // onSurfaceVariantLight / surfaceVariantDark / outlineVariantDark
    val Neutral850 = Color(0xFF363A34) // surfaceBrightDark
    val Neutral860 = Color(0xFF3B3F38) // surfaceContainerHighestDarkMediumContrast
    val Neutral870 = Color(0xFF383D36) // surfaceContainerHighDarkHighContrast
    val Neutral880 = Color(0xFF323630) // surfaceContainerHighestDark
    val Neutral890 = Color(0xFF30342E) // surfaceContainerHighDarkMediumContrast
    val Neutral900 = Color(0xFF2D322B) // inverseSurfaceLight / inverseOnSurfaceDark
    val Neutral910 =
        Color(0xFF272B25) // surfaceContainerHighDark / inverseOnSurfaceDarkMediumContrast
    val Neutral920 = Color(0xFF252923) // surfaceContainerDarkMediumContrast
    val Neutral930 =
        Color(0xFF1D211B) // surfaceContainerDark / surfaceContainerLowDarkHighContrast
    val Neutral940 =
        Color(0xFF191D17) // onBackgroundLight / onSurfaceLight / surfaceContainerLowDark
    val Neutral950 = Color(0xFF1B1F19) // surfaceContainerLowDarkMediumContrast
    val Neutral960 = Color(0xFF10140F) // backgroundDark / surfaceDark / surfaceDimDark
    val Neutral970 = Color(0xFF0B0F0A) // surfaceContainerLowestDark
    val Neutral980 = Color(0xFF050805) // surfaceContainerLowestDarkMediumContrast
    val NeutralBlack = Color(0xFF000000) // scrim

    // --- Neutral Variant (Outline / Surface Variant) ---
    val NeutralVariant100 = Color(0xFFECF2E5) // outlineDarkHighContrast
    val NeutralVariant200 = Color(0xFFDEE4D8) // surfaceVariantLight
    val NeutralVariant300 = Color(0xFFC2C8BD) // outlineVariantLight / onSurfaceVariantDark
    val NeutralVariant400 = Color(0xFFBEC5B9) // outlineVariantDarkHighContrast
    val NeutralVariant500 = Color(0xFFAEB4A8) // outlineDarkMediumContrast
    val NeutralVariant600 = Color(0xFF8C9287) // outlineVariantDarkMediumContrast
    val NeutralVariant700 = Color(0xFF696F65) // outlineVariantLightMediumContrast
    val NeutralVariant800 = Color(0xFF4D514A) // surfaceBrightDarkHighContrast
    val NeutralVariant850 = Color(0xFF454B42) // outlineVariantLightHighContrast
    val NeutralVariant900 = Color(0xFF41463F) // surfaceBrightDarkMediumContrast
    val NeutralVariant950 = Color(0xFF282E26) // outlineLightHighContrast
    val NeutralVariant970 = Color(0xFF32382F) // onSurfaceVariantLightMediumContrast
    val NeutralVariant980 = Color(0xFF4E544B) // outlineLightMediumContrast
}

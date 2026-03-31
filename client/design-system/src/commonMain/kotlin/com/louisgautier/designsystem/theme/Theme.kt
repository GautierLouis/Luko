package com.louisgautier.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.token.string.Strings
import com.louisgautier.designsystem.token.color.AppColors
import com.louisgautier.designsystem.token.color.v2.AppLevelColors

object Theme {
    @Deprecated("Use materialColors or appLevelColors instead")
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val materialColors: ColorScheme
        @Composable
        get() = LocalMaterialColors.current

    val appLevelColors: AppLevelColors
        @Composable
        get() = LocalAppLevelColors.current

    val strings: Strings
        @Composable
        get() = LocalAppStrings.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}


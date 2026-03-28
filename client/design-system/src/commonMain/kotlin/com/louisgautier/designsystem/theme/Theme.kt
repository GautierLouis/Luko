package com.louisgautier.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.token.string.Strings
import com.louisgautier.designsystem.token.color.AppColors

object Theme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val materialColors: ColorScheme
        @Composable
        get() = LocalMaterialColors.current

    val strings: Strings
        @Composable
        get() = LocalAppStrings.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}


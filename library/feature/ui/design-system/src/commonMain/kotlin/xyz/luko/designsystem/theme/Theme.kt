package xyz.luko.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import xyz.luko.designsystem.token.color.model.AppLevelColors
import xyz.luko.designsystem.token.string.Strings

object Theme {
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

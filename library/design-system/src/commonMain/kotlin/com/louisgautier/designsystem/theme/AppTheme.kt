package com.louisgautier.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.intl.Locale
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.token.color.AppLevelColorsDay
import com.louisgautier.designsystem.token.color.materialColorsDay
import com.louisgautier.designsystem.token.string.StringsLocale
import com.louisgautier.designsystem.token.string.provideStringsEN
import com.louisgautier.designsystem.token.typo.AppTypography

@Composable
fun AppTheme(
    themeMode: ThemeMode,
    forcedLocale: StringsLocale? = null,
    content: @Composable () -> Unit
) {

    val locale = forcedLocale ?: Locale.current.toStringsLocale()
    val strings = remember(locale) { locale.toStrings() }
    val materialColors = remember(themeMode) { themeMode.toMaterialColors() }
    val appLevelColors = remember(themeMode) { themeMode.toLevelColors() }

    CompositionLocalProvider(
        LocalMaterialColors provides materialColors,
        LocalAppLevelColors provides appLevelColors,
        LocalAppStrings provides strings,
        LocalTypography provides AppTypography,
    ) {
        content()
    }
}

internal val LocalMaterialColors = staticCompositionLocalOf { materialColorsDay() }
internal val LocalAppLevelColors = staticCompositionLocalOf { AppLevelColorsDay }
internal val LocalAppStrings = staticCompositionLocalOf { provideStringsEN() }
internal val LocalTypography = staticCompositionLocalOf { AppTypography }
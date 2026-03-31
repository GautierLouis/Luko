package com.louisgautier.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.intl.Locale
import com.louisgautier.designsystem.ai.lightScheme
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.token.color.provideDayColors
import com.louisgautier.designsystem.token.color.v2.DayAppLevelColors
import com.louisgautier.designsystem.token.string.StringsLocale
import com.louisgautier.designsystem.token.string.provideStringsEN
import com.louisgautier.designsystem.token.typo.AppTypography

@Composable
fun AppTheme(
    themeMode: ThemeMode = if (isSystemInDarkTheme()) ThemeMode.Night else ThemeMode.Day,
    forcedLocale: StringsLocale? = null,
    content: @Composable () -> Unit
) {
    val locale = forcedLocale ?: Locale.current.toStringsLocale()
    val strings = remember(locale) { locale.toStrings() }
    val appColors = remember(themeMode) { themeMode.toColors() }
    val materialColors = remember(themeMode) { themeMode.toMaterialColors() }
    val appLevelColors = remember(themeMode) { themeMode.toLevelColors() }

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalMaterialColors provides materialColors,
        LocalAppLevelColors provides appLevelColors,
        LocalAppStrings provides strings,
        LocalTypography provides AppTypography
    ) {
        content()
    }
}

@Deprecated("Use LocalAppLevelColors")
internal val LocalAppColors = staticCompositionLocalOf { provideDayColors() }
internal val LocalMaterialColors = staticCompositionLocalOf { lightScheme }
internal val LocalAppLevelColors = staticCompositionLocalOf { DayAppLevelColors }
internal val LocalAppStrings = staticCompositionLocalOf { provideStringsEN() }
internal val LocalTypography = staticCompositionLocalOf { AppTypography }
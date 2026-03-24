package com.louisgautier.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.Strings
import com.louisgautier.designsystem.StringsLocale
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.stringsEN
import com.louisgautier.designsystem.stringsFR
import com.louisgautier.designsystem.token.color.AppColors
import com.louisgautier.designsystem.token.color.provideDayColors
import com.louisgautier.designsystem.token.color.provideNightColors

@Composable
fun AppThemeWrapper(
    themeMode: ThemeMode,
    locale: StringsLocale,
    content: @Composable () -> Unit
) {
    AppTheme(themeMode, locale) {
        Box(Modifier.background(AppTheme.colors.bg)) {
            content()
        }
    }
}

@Composable
fun AppTheme(
    themeMode: ThemeMode = if (isSystemInDarkTheme()) ThemeMode.Night else ThemeMode.Day,
    locale: StringsLocale = StringsLocale.EN,
    content: @Composable () -> Unit
) {
    val currentColors = remember(themeMode) {
        when (themeMode) {
            ThemeMode.Night -> provideNightColors()
            ThemeMode.Day -> provideDayColors()
        }
    }

    val strings = remember(locale) {
        when (locale) {
            StringsLocale.FR -> stringsFR
            else -> stringsEN
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides currentColors,
        LocalAppStrings provides strings,
    ) {
        content()
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val strings: Strings
        @Composable
        get() = LocalAppStrings.current
}

internal val LocalAppColors = staticCompositionLocalOf { provideDayColors() }
internal val LocalAppStrings = staticCompositionLocalOf { stringsEN }
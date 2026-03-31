package com.louisgautier.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.text.intl.Locale
import com.louisgautier.designsystem.ai.darkScheme
import com.louisgautier.designsystem.ai.lightScheme
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.token.color.AppColors
import com.louisgautier.designsystem.token.color.provideDayColors
import com.louisgautier.designsystem.token.color.provideNightColors
import com.louisgautier.designsystem.token.color.v2.AppLevelColors
import com.louisgautier.designsystem.token.color.v2.DayAppLevelColors
import com.louisgautier.designsystem.token.color.v2.NightAppLevelColors
import com.louisgautier.designsystem.token.string.Strings
import com.louisgautier.designsystem.token.string.StringsLocale
import com.louisgautier.designsystem.token.string.provideStringsEN
import com.louisgautier.designsystem.token.string.provideStringsFR

internal fun Locale.toStringsLocale(): StringsLocale = when (language) {
    "fr" -> StringsLocale.FR
    else -> StringsLocale.EN
}

internal fun StringsLocale.toStrings(): Strings = when (this) {
    StringsLocale.FR -> provideStringsFR()
    else -> provideStringsEN()
}

internal fun ThemeMode.toColors(): AppColors = when (this) {
    ThemeMode.Night -> provideNightColors()
    ThemeMode.Day -> provideDayColors()
}
internal fun ThemeMode.toMaterialColors(): ColorScheme = when (this) {
    ThemeMode.Night -> darkScheme
    ThemeMode.Day -> lightScheme
}
internal fun ThemeMode.toLevelColors(): AppLevelColors = when (this) {
    ThemeMode.Day -> DayAppLevelColors
    ThemeMode.Night -> NightAppLevelColors
}
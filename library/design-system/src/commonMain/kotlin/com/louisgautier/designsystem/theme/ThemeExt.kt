package com.louisgautier.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.text.intl.Locale
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.token.color.AppLevelColorNight
import com.louisgautier.designsystem.token.color.AppLevelColorsDay
import com.louisgautier.designsystem.token.color.materialColorsDay
import com.louisgautier.designsystem.token.color.materialColorsNight
import com.louisgautier.designsystem.token.color.model.AppLevelColors
import com.louisgautier.designsystem.token.string.Strings
import com.louisgautier.designsystem.token.string.StringsLocale
import com.louisgautier.designsystem.token.string.provideStringsEN
import com.louisgautier.designsystem.token.string.provideStringsFR

internal fun Locale.toStringsLocale(): StringsLocale =
    when (language) {
        "fr" -> StringsLocale.FR
        else -> StringsLocale.EN
    }

internal fun StringsLocale.toStrings(): Strings =
    when (this) {
        StringsLocale.FR -> provideStringsFR()
        else -> provideStringsEN()
    }

internal fun ThemeMode.toMaterialColors(): ColorScheme =
    when (this) {
        ThemeMode.Night -> materialColorsNight()
        ThemeMode.Day -> materialColorsDay()
    }

internal fun ThemeMode.toLevelColors(): AppLevelColors =
    when (this) {
        ThemeMode.Day -> AppLevelColorsDay
        ThemeMode.Night -> AppLevelColorNight
    }

package xyz.luko.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.text.intl.Locale
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.token.color.AppLevelColorNight
import xyz.luko.designsystem.token.color.AppLevelColorsDay
import xyz.luko.designsystem.token.color.materialColorsDay
import xyz.luko.designsystem.token.color.materialColorsNight
import xyz.luko.designsystem.token.color.model.AppLevelColors
import xyz.luko.designsystem.token.string.Strings
import xyz.luko.designsystem.token.string.StringsLocale
import xyz.luko.designsystem.token.string.provideStringsEN
import xyz.luko.designsystem.token.string.provideStringsFR

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

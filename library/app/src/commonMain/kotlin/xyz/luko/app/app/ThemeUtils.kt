package xyz.luko.app.app

import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.domain.repository.SettingTheme

internal fun SettingTheme.toThemeMode(isSystemInDarkTheme: Boolean): ThemeMode =
    when (this) {
        SettingTheme.Default -> if (isSystemInDarkTheme) ThemeMode.Night else ThemeMode.Day
        SettingTheme.Night -> ThemeMode.Night
        SettingTheme.Day -> ThemeMode.Day
    }

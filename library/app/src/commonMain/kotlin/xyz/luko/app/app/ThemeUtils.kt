package xyz.luko.app.app

import xyz.luko.domain.repository.SettingTheme
import xyz.luko.ui.designsystem.preview.ThemeMode

internal fun SettingTheme.toThemeMode(isSystemInDarkTheme: Boolean): ThemeMode =
    when (this) {
        SettingTheme.Default -> if (isSystemInDarkTheme) ThemeMode.Night else ThemeMode.Day
        SettingTheme.Night -> ThemeMode.Night
        SettingTheme.Day -> ThemeMode.Day
    }

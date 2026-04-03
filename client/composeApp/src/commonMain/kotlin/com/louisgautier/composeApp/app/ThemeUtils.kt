package com.louisgautier.composeApp.app

import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.domain.repository.SettingTheme

fun SettingTheme.toThemeMode(isSystemInDarkTheme: Boolean): ThemeMode =
    when (this) {
        SettingTheme.Default -> if (isSystemInDarkTheme) ThemeMode.Night else ThemeMode.Day
        SettingTheme.Night -> ThemeMode.Night
        SettingTheme.Day -> ThemeMode.Day
    }
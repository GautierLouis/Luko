package com.louisgautier.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.string.StringsLocale

@Composable
fun AppThemeWrapper(
    themeMode: ThemeMode,
    locale: StringsLocale = StringsLocale.EN,
    content: @Composable () -> Unit
) {
    AppTheme(themeMode, locale) {
        Box(Modifier.background(Theme.colors.bg)) {
            content()
        }
    }
}
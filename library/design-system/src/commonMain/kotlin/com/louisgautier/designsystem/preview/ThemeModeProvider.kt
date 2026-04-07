package com.louisgautier.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ThemeModeProvider : PreviewParameterProvider<ThemeMode> {
    override val values: Sequence<ThemeMode>
        get() = sequenceOf(ThemeMode.Day, ThemeMode.Night)
}
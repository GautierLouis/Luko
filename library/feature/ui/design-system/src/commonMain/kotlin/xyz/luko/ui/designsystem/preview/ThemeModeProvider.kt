package xyz.luko.ui.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ThemeModeProvider : PreviewParameterProvider<ThemeMode> {
    private val themes = listOf(ThemeMode.Day, ThemeMode.Night)

    override val values: Sequence<ThemeMode>
        get() = themes.asSequence()

    override fun getDisplayName(index: Int): String = themes[index].name

}

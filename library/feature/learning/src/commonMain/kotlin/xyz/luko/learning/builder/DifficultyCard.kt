package xyz.luko.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.baseui.session.caption
import xyz.luko.baseui.session.colorFamily
import xyz.luko.baseui.session.icon
import xyz.luko.baseui.session.label
import xyz.luko.baseui.session.title
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme

@Composable
internal fun DifficultyCard(
    difficulty: DifficultyLevel,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    LevelCard(
        title = difficulty.title(),
        tagLabel = difficulty.label(),
        caption = difficulty.caption(),
        icon = difficulty.icon(),
        color = difficulty.colorFamily(),
        modifier = modifier,
        selected = selected,
        onClick = onClick,
    )
}

@Preview(device = Devices.TABLET)
@Preview(device = Devices.PHONE)
@Composable
private fun PreviewDifficultyCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        Column {
            DifficultyLevel.entries.forEach {
                DifficultyCard(difficulty = it)
                DifficultyCard(difficulty = it, selected = true)
            }
        }
    }
}

package xyz.luko.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.domain.model.DifficultyLevel

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
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
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
package xyz.luko.learning.builder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.domain.model.DifficultyLevel

@Composable
internal fun DifficultyPicker(
    difficulty: DifficultyLevel,
    modifier: Modifier = Modifier,
    onClick: (DifficultyLevel) -> Unit = {},
) {
    PickerLayout(
        label = Theme.strings.builderSelectDifficulty,
        modifier = modifier,
    ) {
        DifficultyLevel.entries.forEach { item ->
            DifficultyCard(
                difficulty = item,
                selected = difficulty == item,
                onClick = { onClick(item) },
            )
        }
    }
}

@Preview(device = Devices.TABLET)
@Preview(device = Devices.PHONE)
@Composable
private fun PreviewDifficultyPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        DifficultyPicker(
            difficulty = DifficultyLevel.EASY,
        )
    }
}

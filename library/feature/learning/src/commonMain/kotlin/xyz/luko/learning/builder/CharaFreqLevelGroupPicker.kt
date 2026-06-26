package xyz.luko.learning.builder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

@Composable
internal fun CharaFreqLevelGroupPicker(
    modifier: Modifier = Modifier,
    selectedLevels: List<FrequencyLevel> = listOf(),
    onClick: (FrequencyLevel) -> Unit = {},
) {
    PickerLayout(
        label = Theme.strings.builderSelectDifficulty,
        modifier = modifier,
    ) {
        OrientationGrid(
            data = FrequencyLevel.entries,
            modifier = modifier,
            key = { level -> level.ordinal }
        ) { level ->
            CharaFreqLevelCard(
                level = level,
                modifier = Modifier,
                selected = level in selectedLevels,
                onClick = { onClick(level) },
            )
        }
    }
}

@Preview(device = Devices.TABLET)
@Preview(device = Devices.PHONE)
@Composable
private fun PreviewCharaFreqLevelGroupPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        CharaFreqLevelGroupPicker(
            selectedLevels =
                listOf(
                    FrequencyLevel.COMMON,
                    FrequencyLevel.FREQUENT,
                    FrequencyLevel.STANDARD,
                ),
        )
    }
}

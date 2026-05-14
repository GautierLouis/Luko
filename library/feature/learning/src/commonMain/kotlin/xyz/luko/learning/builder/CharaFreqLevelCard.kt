package xyz.luko.learning.builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.designsystem.components.attrs.FrequencyLevel.Companion.caption
import xyz.luko.designsystem.components.attrs.FrequencyLevel.Companion.colorFamily
import xyz.luko.designsystem.components.attrs.FrequencyLevel.Companion.icon
import xyz.luko.designsystem.components.attrs.FrequencyLevel.Companion.label
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme

@Composable
internal fun CharaFreqLevelCard(
    level: FrequencyLevel,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    LevelCard(
        title = level.label(),
        caption = level.caption(),
        icon = level.icon(),
        color = level.colorFamily(),
        onClick = onClick,
        modifier = modifier,
        selected = selected
    )
}

@Preview
@Composable
private fun PreviewCharaFreqLevelCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FrequencyLevel.entries.forEach {
                CharaFreqLevelCard(level = it, selected = false)
                CharaFreqLevelCard(level = it, selected = true)
            }
        }
    }
}
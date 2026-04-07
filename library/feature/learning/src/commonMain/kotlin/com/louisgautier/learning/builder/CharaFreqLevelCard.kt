package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.Companion.caption
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.Companion.colorFamily
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.Companion.icon
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.Companion.label
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider

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
    AppThemeWrapper(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FrequencyLevel.entries.forEach {
                CharaFreqLevelCard(level = it, selected = false)
                CharaFreqLevelCard(level = it, selected = true)
            }
        }
    }
}
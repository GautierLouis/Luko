package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.token.dimens.Spacing

@Composable
internal fun CharaFreqLevelGroupPicker(
    modifier: Modifier = Modifier,
    selectedLevels: List<FrequencyLevel> = listOf(),
    onClick: (FrequencyLevel) -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Spacing.large
    ) {
        FrequencyLevel.entries.forEach {
            CharaFreqLevelCard(
                level = it,
                selected = it in selectedLevels,
                onClick = { onClick(it) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCharaFreqLevelGroupPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        CharaFreqLevelGroupPicker(
            selectedLevels = listOf(
                FrequencyLevel.COMMON,
                FrequencyLevel.FREQUENT,
                FrequencyLevel.STANDARD
            )
        )
    }
}
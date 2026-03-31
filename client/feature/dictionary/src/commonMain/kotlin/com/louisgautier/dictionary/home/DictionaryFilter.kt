package com.louisgautier.dictionary.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.CharacterFrequency
import com.louisgautier.designsystem.components.CharacterFrequency.Companion.colorFamily
import com.louisgautier.designsystem.components.CharacterFrequency.Companion.label
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.dictionary.home.ActiveFilter
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun DictionaryFilter(
    activeFilter: ActiveFilter,
    onEvent: (DictionaryScreenEvent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Character Groups",
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onPrimaryContainer,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Spacing.large
        ) {
            CharacterFrequency.entries.forEachIndexed { index, level ->
                FilterChip(
                    selected = when (level) {
                        CharacterFrequency.COMMON -> activeFilter.isCommonActivated
                        CharacterFrequency.FREQUENT -> activeFilter.isFrequentActivated
                        CharacterFrequency.STANDARD -> activeFilter.isStandardActivated
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Theme.materialColors.surfaceContainer,
                        selectedContainerColor = level.colorFamily().focusRing,

                        labelColor = level.colorFamily().fg,
                        selectedLabelColor = Theme.materialColors.onPrimary,

                        ),
                    onClick = {
                        val newFilter = when (level) {
                            CharacterFrequency.COMMON -> ActiveFilter(isCommonActivated = !activeFilter.isCommonActivated)
                            CharacterFrequency.FREQUENT -> ActiveFilter(isFrequentActivated = !activeFilter.isFrequentActivated)
                            CharacterFrequency.STANDARD -> ActiveFilter(isStandardActivated = !activeFilter.isStandardActivated)
                        }
                        onEvent(DictionaryScreenEvent.OnFilterChange(newFilter))
                    },
                    label = {
                        Text(
                            text = level.label(),
                            style = Theme.typography.labelMedium,
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDictionaryFilter(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        DictionaryFilter(
            activeFilter = ActiveFilter(),
        )
    }
}
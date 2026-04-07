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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.COMMON
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.Companion.colorFamily
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.Companion.label
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.FREQUENT
import com.louisgautier.designsystem.components.attrs.FrequencyLevel.STANDARD
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing

@Composable
internal fun DictionaryFilter(
    activeFilter: ActiveFilter,
    onEvent: (DictionaryScreenEvent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = Padding.large,
                vertical = Padding.medium
            )
            .fillMaxWidth()
    ) {
        Text(
            text = Theme.strings.filterFrequencyGroup,
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onPrimaryContainer,
            modifier = Modifier.padding(bottom = Padding.medium)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Spacing.large
        ) {
            FrequencyLevel.entries.forEachIndexed { index, level ->
                FilterChip(
                    selected = when (level) {
                        COMMON -> activeFilter.isCommonActivated
                        FREQUENT -> activeFilter.isFrequentActivated
                        STANDARD -> activeFilter.isStandardActivated
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Theme.materialColors.surfaceContainer,
                        labelColor = Theme.materialColors.onSecondaryContainer,
                        selectedContainerColor = level.colorFamily().primary,
                        selectedLabelColor = level.colorFamily().onPrimary,
                    ),
                    onClick = {
                        val newFilter = when (level) {
                            COMMON -> ActiveFilter(isCommonActivated = !activeFilter.isCommonActivated)
                            FREQUENT -> ActiveFilter(isFrequentActivated = !activeFilter.isFrequentActivated)
                            STANDARD -> ActiveFilter(isStandardActivated = !activeFilter.isStandardActivated)
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
        Column {
            DictionaryFilter(
                activeFilter = ActiveFilter(
                    isCommonActivated = false,
                    isFrequentActivated = false,
                    isStandardActivated = false,
                ),
            )
            DictionaryFilter(
                activeFilter = ActiveFilter(
                    isCommonActivated = true,
                    isFrequentActivated = true,
                    isStandardActivated = true,
                ),
            )
        }
    }
}
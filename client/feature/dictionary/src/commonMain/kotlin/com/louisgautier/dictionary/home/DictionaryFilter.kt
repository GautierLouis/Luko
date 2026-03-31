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
import com.louisgautier.designsystem.components.attrs.HSKLevel
import com.louisgautier.designsystem.components.attrs.HSKLevel.Companion.colorFamily
import com.louisgautier.designsystem.components.attrs.HSKLevel.Companion.label
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Spacing
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
            text = Theme.strings.filterHskGroup,
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onPrimaryContainer,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Spacing.large
        ) {
            HSKLevel.entries.forEachIndexed { index, level ->
                FilterChip(
                    selected = when (level) {
                        HSKLevel.COMMON -> activeFilter.isCommonActivated
                        HSKLevel.FREQUENT -> activeFilter.isFrequentActivated
                        HSKLevel.STANDARD -> activeFilter.isStandardActivated
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Theme.materialColors.surfaceContainer,
                        labelColor = Theme.materialColors.onSecondaryContainer,
                        selectedContainerColor = level.colorFamily().primary,
                        selectedLabelColor = level.colorFamily().onPrimary,
                    ),
                    onClick = {
                        val newFilter = when (level) {
                            HSKLevel.COMMON -> ActiveFilter(isCommonActivated = !activeFilter.isCommonActivated)
                            HSKLevel.FREQUENT -> ActiveFilter(isFrequentActivated = !activeFilter.isFrequentActivated)
                            HSKLevel.STANDARD -> ActiveFilter(isStandardActivated = !activeFilter.isStandardActivated)
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
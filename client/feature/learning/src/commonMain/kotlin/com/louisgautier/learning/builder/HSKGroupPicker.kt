package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.components.attrs.HSKLevel
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.token.dimens.Spacing
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun HSKGroupPicker(
    modifier: Modifier = Modifier,
    selectedLevels: List<HSKLevel> = listOf(),
    onClick: (HSKLevel) -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Spacing.large
    ) {
        HSKLevel.entries.forEach {
            HSKLevelCard(
                level = it,
                selected = it in selectedLevels,
                onClick = { onClick(it) }
            )
        }
    }
}

@Composable
@Preview
private fun PreviewHSKGroupPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        HSKGroupPicker(
            selectedLevels = listOf(
                HSKLevel.COMMON,
                HSKLevel.FREQUENT,
                HSKLevel.STANDARD
            )
        )
    }
}
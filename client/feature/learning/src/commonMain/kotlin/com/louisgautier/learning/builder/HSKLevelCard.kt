package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.attrs.HSKLevel
import com.louisgautier.designsystem.components.attrs.HSKLevel.Companion.caption
import com.louisgautier.designsystem.components.attrs.HSKLevel.Companion.colorFamily
import com.louisgautier.designsystem.components.attrs.HSKLevel.Companion.icon
import com.louisgautier.designsystem.components.attrs.HSKLevel.Companion.label
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun HSKLevelCard(
    level: HSKLevel,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    LevelCard(
        title = {
            LevelCardTitle(
                label = level.label(),
                color = Theme.materialColors.onSurface
            )
        },
        caption = level.caption(),
        icon = level.icon(),
        color = level.colorFamily(),
        onClick = onClick,
        modifier = modifier,
        selected = selected
    )
}

@Composable
@Preview
private fun PreviewHSKLevelCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            HSKLevel.entries.forEach {
                HSKLevelCard(level = it, selected = false)
                HSKLevelCard(level = it, selected = true)
            }
        }
    }
}
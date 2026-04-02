package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.components.attrs.DifficultyLevel
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.caption
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.colorFamily
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.icon
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.label
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.title
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun DifficultyCard(
    difficulty: DifficultyLevel,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    LevelCard(
        title = difficulty.title(),
        tagLabel = difficulty.label(),
        caption = difficulty.caption(),
        icon = difficulty.icon(),
        color = difficulty.colorFamily(),
        modifier = modifier,
        selected = selected,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun PreviewDifficultyCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Column {
            DifficultyLevel.entries.forEach {
                DifficultyCard(difficulty = it)
                DifficultyCard(difficulty = it, selected = true)
            }
        }
    }
}
package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.domain.model.DifficultyLevel

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
    AppTheme(themeMode) {
        Column {
            DifficultyLevel.entries.forEach {
                DifficultyCard(difficulty = it)
                DifficultyCard(difficulty = it, selected = true)
            }
        }
    }
}
package com.louisgautier.learning.builder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.components.attrs.DifficultyLevel
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun DifficultyPicker(
    difficulty: DifficultyLevel,
    modifier: Modifier = Modifier,
    onDifficultySelected: (DifficultyLevel) -> Unit = {}
) {

    PickerLayout(
        label = Theme.strings.builderSelectDifficulty,
        modifier = modifier
    ) {
        DifficultyLevel.entries.forEach { item ->
            DifficultyCard(
                difficulty = item,
                selected = difficulty == item,
                onClick = { onDifficultySelected(item) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDifficultyPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        DifficultyPicker(
            difficulty = DifficultyLevel.EASY
        )
    }
}

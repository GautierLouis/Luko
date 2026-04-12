package com.louisgautier.learning.builder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.domain.model.DifficultyLevel

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
    AppTheme(themeMode) {
        DifficultyPicker(
            difficulty = DifficultyLevel.EASY
        )
    }
}

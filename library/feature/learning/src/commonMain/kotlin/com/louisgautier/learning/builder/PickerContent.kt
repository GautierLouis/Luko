package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.model.DifficultyLevel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun PickerContent(
    difficulty: DifficultyLevel,
    questionCount: QuestionCount,
    modifier: Modifier = Modifier,
    onDifficultySelected: (DifficultyLevel) -> Unit = {},
    onQuestionCountSelected: (QuestionCount) -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Spacing.extraExtraLarge
    ) {
        DifficultyPicker(
            difficulty = difficulty,
            onDifficultySelected = onDifficultySelected
        )

        QuestionCountPicker(
            selectedCount = questionCount,
            onCountClicked = onQuestionCountSelected
        )
    }
}

@Preview
@Composable
private fun PreviewPickerContent(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        PickerContent(
            difficulty = DifficultyLevel.EASY,
            questionCount = QuestionCount.FIVE
        )
    }
}

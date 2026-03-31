package com.louisgautier.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.attrs.DifficultyLevel
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.learning.builder.QuestionCount
import com.louisgautier.learning.builder.QuestionCountPicker

@Composable
internal fun DifficultyAndQuantityContent(
    difficulty: DifficultyLevel,
    questionCount: QuestionCount,
    modifier: Modifier = Modifier,
    onDifficultySelected: (DifficultyLevel) -> Unit = {},
    onQuestionCountSelected: (QuestionCount) -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        DifficultyPickerContent(
            difficulty = difficulty,
            onDifficultySelected = onDifficultySelected
        )

        QuestionCountPicker(
            questionCount = questionCount,
            onQuestionCountSelected = onQuestionCountSelected
        )
    }
}
package com.louisgautier.learning.builder

import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.domain.model.DifficultyLevel

internal sealed class SessionBuilderScreenEvent {
    data class OnNextPage(val currentPage: Int, val pageCount: Int) : SessionBuilderScreenEvent()
    data class OnPreviousPage(val currentPage: Int, val pageCount: Int) :
        SessionBuilderScreenEvent()

    data class OnLevelSelected(val level: FrequencyLevel) : SessionBuilderScreenEvent()
    data class OnDifficultySelected(val difficulty: DifficultyLevel) : SessionBuilderScreenEvent()
    data class OnQuestionCountSelected(val questionCount: QuestionCount) :
        SessionBuilderScreenEvent()
}
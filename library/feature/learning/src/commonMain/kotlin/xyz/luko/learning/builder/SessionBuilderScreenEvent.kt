package xyz.luko.learning.builder

import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.ui.designsystem.components.attrs.FrequencyLevel

internal sealed class SessionBuilderScreenEvent {
    data class OnNextPage(
        val currentPage: Int,
        val pageCount: Int,
    ) : SessionBuilderScreenEvent()

    data class OnPreviousPage(
        val currentPage: Int,
        val pageCount: Int,
    ) : SessionBuilderScreenEvent()

    data class OnFrequencySelected(
        val level: FrequencyLevel,
    ) : SessionBuilderScreenEvent()

    data class OnDifficultySelected(
        val difficulty: DifficultyLevel,
    ) : SessionBuilderScreenEvent()

    data class OnQuestionCountSelected(
        val questionCount: QuestionCount,
    ) : SessionBuilderScreenEvent()

    data object QuestionCountIncrease : SessionBuilderScreenEvent()

    data object QuestionCountDecrease : SessionBuilderScreenEvent()
}

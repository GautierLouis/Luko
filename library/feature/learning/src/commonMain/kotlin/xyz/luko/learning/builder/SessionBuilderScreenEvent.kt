package xyz.luko.learning.builder

import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.DifficultyLevel

internal sealed class SessionBuilderScreenEvent {
    data class OnNextPage(
        val currentPage: Int,
        val pageCount: Int,
    ) : SessionBuilderScreenEvent()

    data class OnPreviousPage(
        val currentPage: Int,
        val pageCount: Int,
    ) : SessionBuilderScreenEvent()

    data class OnLevelSelected(
        val level: FrequencyLevel,
    ) : SessionBuilderScreenEvent()

    data class OnDifficultySelected(
        val difficulty: DifficultyLevel,
    ) : SessionBuilderScreenEvent()

    data class OnQuestionCountSelected(
        val questionCount: QuestionCount,
    ) : SessionBuilderScreenEvent()
}

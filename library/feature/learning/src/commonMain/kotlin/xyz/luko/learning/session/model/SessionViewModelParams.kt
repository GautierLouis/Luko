package xyz.luko.learning.session.model

import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.learning.builder.QuestionCount

internal data class SessionViewModelParams(
    val levels: List<FrequencyLevel>,
    val difficulty: DifficultyLevel,
    val limit: QuestionCount
)

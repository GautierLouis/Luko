package xyz.luko.learning.session.model

import androidx.lifecycle.SavedStateHandle
import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.learning.builder.QuestionCount
import xyz.luko.learning.routing.LearningInternalRoute

internal fun SavedStateHandle.getParams(): SessionViewModelParams {
    val descriptor = LearningInternalRoute.SessionRoute.serializer().descriptor
    val levels = (this[descriptor.getElementName(0)] as? String)
        ?.split(",")
        ?.map { FrequencyLevel.valueOf(it.trim()) }
        ?: FrequencyLevel.entries
    val difficulty: DifficultyLevel =
        (this[descriptor.getElementName(1)] as? String)
            ?.let { DifficultyLevel.valueOf(it) }
            ?: DifficultyLevel.EASY

    val limit: QuestionCount =
        (this[descriptor.getElementName(2)] as? String)
            ?.let { QuestionCount.valueOf(it) }
            ?: QuestionCount.FIVE

    return SessionViewModelParams(levels, difficulty, limit)
}

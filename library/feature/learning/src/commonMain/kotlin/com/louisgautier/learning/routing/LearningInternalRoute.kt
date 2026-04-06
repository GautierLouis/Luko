package com.louisgautier.learning.routing

import androidx.navigation3.runtime.NavKey
import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.domain.model.DifficultyLevel
import com.louisgautier.learning.builder.QuestionCount
import kotlinx.serialization.Serializable


@Serializable
internal sealed interface LearningInternalRoute : NavKey {
    @Serializable
    data class SessionRoute(
        val levels: List<FrequencyLevel>,
        val difficulty: DifficultyLevel,
        val limit: QuestionCount,
    ) : LearningInternalRoute

    @Serializable
    data object CongratulationRoute : LearningInternalRoute
}


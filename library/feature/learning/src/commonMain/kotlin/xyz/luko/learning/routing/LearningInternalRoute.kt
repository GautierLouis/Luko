package xyz.luko.learning.routing

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.learning.builder.QuestionCount

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

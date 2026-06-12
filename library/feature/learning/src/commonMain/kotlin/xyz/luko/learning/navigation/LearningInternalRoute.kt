package xyz.luko.learning.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
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
    data class StreakRoute(val newValue: Int, val lastSession: Session) : LearningInternalRoute

    @Serializable
    data class CongratulationRoute(val lastSession: Session) : LearningInternalRoute
}

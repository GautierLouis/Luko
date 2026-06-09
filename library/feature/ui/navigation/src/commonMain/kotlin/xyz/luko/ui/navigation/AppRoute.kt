package xyz.luko.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Define route across features
 */
@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object MainRoute : AppRoute

    @Serializable
    sealed interface LearningRoute : AppRoute {
        @Serializable
        data object NewSessionRoute : LearningRoute

        @Serializable
        data class PracticeCharacterRoute(
            val characterCode: Int,
        ) : LearningRoute
    }

    @Serializable
    sealed interface SessionsRoute : AppRoute {

        @Serializable
        data class SessionListRoute(val id: Long? = null) : SessionsRoute
    }
}


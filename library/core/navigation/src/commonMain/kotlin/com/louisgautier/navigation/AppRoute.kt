package com.louisgautier.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Define route across features
 */
@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data class MainRoute(
        val tab: MainTab = MainTab.Home,
    ) : AppRoute

    @Serializable
    sealed interface LearningRoute : AppRoute {
        @Serializable
        data object NewSessionRoute : LearningRoute

        @Serializable
        data class PracticeCharacterRoute(
            val characterCode: Int,
        ) : LearningRoute
    }
}

@Serializable
enum class MainTab {
    Home,
    Dictionary,
    Feed,
    Profile,
}

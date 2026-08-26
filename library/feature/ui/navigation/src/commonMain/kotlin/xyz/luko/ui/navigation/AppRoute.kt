package xyz.luko.ui.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionSettings

/**
 * Define route across features
 */
@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    sealed interface Home : AppRoute {
        @Serializable
        data object Main : AppRoute

        @Serializable
        data object DebugMenu : AppRoute
    }

    @Serializable
    sealed interface Onboarding : AppRoute {
        @Serializable
        data object Home : Onboarding

        @Serializable
        data object LastPage : Onboarding
    }


    @Serializable
    sealed interface Learning : AppRoute {
        @Serializable
        data object BuildSession : Learning

        @Serializable
        data class PracticeCharacter(val characterCode: Int) : Learning

        @Serializable
        data class StartSession(val settings: SessionSettings) : Learning

        @Serializable
        data class StreakUp(val newValue: Int) : Learning

        @Serializable
        data class Congratulation(val session: Session) : Learning

        @Serializable
        data class LevelUp(val levels: Map<Int, List<Int>>) : Learning
    }


    @Serializable
    sealed interface Sessions : AppRoute {
        @Serializable
        data class List(val id: Long? = null) : Sessions
    }


    @Serializable
    sealed interface Dictionary : AppRoute {
        @Serializable
        data object Home : Dictionary

        @Serializable
        data class Detail(val code: Int) : Dictionary
    }


}

@OptIn(ExperimentalSerializationApi::class)
val savedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclassesOfSealed<AppRoute.Home>()
            subclassesOfSealed<AppRoute.Learning>()
            subclassesOfSealed<AppRoute.Sessions>()
            subclassesOfSealed<AppRoute.Dictionary>()
            subclassesOfSealed<AppRoute.Onboarding>()
        }
    }
}


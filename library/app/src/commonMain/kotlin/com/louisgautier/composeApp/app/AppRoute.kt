package com.louisgautier.composeApp.app

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.louisgautier.dictionary.DictionaryRoute
import com.louisgautier.learning.LearningRoute
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@OptIn(ExperimentalSerializationApi::class)
internal val savedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclassesOfSealed<AppRoute>()
            subclassesOfSealed<DictionaryRoute>()
            subclassesOfSealed<LearningRoute>()
        }
    }
}

@Serializable
internal sealed interface AppRoute : NavKey

@Serializable
internal data class MainRoute(val defaultSelectedItemIndex: Int = 0) : AppRoute

@Serializable
internal data object SplashRoute : AppRoute
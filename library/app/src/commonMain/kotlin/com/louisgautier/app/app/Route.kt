package com.louisgautier.app.app

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.louisgautier.learning.learningRouteSerializer
import com.louisgautier.navigation.publicRouteSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic

@Serializable
internal data object SplashRoute : NavKey

private val appRouteSerializer = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(SplashRoute::class, SplashRoute.serializer())
    }
}

@OptIn(ExperimentalSerializationApi::class)
val savedStateConfiguration = SavedStateConfiguration {
    serializersModule = learningRouteSerializer + publicRouteSerializer + appRouteSerializer
}
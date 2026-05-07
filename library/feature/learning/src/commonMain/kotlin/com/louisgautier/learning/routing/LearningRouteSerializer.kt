package com.louisgautier.learning.routing

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@OptIn(ExperimentalSerializationApi::class)
val learningInternalRouteSerializer = SerializersModule {
    polymorphic(NavKey::class) {
        subclassesOfSealed<LearningInternalRoute>()
    }
}
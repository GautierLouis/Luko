package xyz.luko.app.app

import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.plus
import xyz.luko.learning.routing.learningInternalRouteSerializer
import xyz.luko.navigation.appRouteSerializers

val navigationConfiguration =
    SavedStateConfiguration {
        serializersModule = learningInternalRouteSerializer + appRouteSerializers
    }

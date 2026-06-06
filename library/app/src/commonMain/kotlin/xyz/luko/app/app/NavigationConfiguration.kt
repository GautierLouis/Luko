package xyz.luko.app.app

import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.plus
import xyz.luko.learning.navigation.learningInternalRouteSerializer
import xyz.luko.ui.navigation.appRouteSerializers

val navigationConfiguration =
    SavedStateConfiguration {
        serializersModule = learningInternalRouteSerializer + appRouteSerializers
    }

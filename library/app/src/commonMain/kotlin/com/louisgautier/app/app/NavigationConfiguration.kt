package com.louisgautier.app.app

import androidx.savedstate.serialization.SavedStateConfiguration
import com.louisgautier.learning.routing.learningInternalRouteSerializer
import com.louisgautier.navigation.appRouteSerializers
import kotlinx.serialization.modules.plus

val navigationConfiguration =
    SavedStateConfiguration {
        serializersModule = learningInternalRouteSerializer + appRouteSerializers
    }

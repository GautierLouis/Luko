package com.louisgautier.server.plugin

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val pluginModule = module {
    singleOf(::BasePlugin)
    singleOf(::AuthenticationPlugin)
    singleOf(::MetricsPlugin)
    singleOf(::ErrorPlugin)
    single { RouterPlugin(getAll()) }
}
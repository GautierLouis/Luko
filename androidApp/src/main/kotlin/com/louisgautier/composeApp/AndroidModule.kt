package com.louisgautier.composeApp

import org.koin.dsl.bind
import org.koin.dsl.module

val androidModules = module {
    single { AndroidAppBuilder(get()) } bind AppConfigBuilder::class
}
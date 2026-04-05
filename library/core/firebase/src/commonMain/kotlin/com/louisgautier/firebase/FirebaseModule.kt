package com.louisgautier.firebase

import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val firebasePlatformModule: Module

val firebaseModule = module {
    includes(firebasePlatformModule)
    single { RemoteConfigManager() }
}
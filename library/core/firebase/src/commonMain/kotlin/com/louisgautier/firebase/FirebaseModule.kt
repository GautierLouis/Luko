package com.louisgautier.firebase

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect val firebasePlatformModule: Module

val firebaseModule =
    module {
        includes(firebasePlatformModule)
        singleOf(::DefaultRemoteConfigManager) bind RemoteConfigManager::class
    }

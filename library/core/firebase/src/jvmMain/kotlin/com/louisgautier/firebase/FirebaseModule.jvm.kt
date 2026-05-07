package com.louisgautier.firebase

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val firebasePlatformModule: Module =
    module {
        singleOf(::DesktopFirebaseManager) bind FirebaseManager::class
    }

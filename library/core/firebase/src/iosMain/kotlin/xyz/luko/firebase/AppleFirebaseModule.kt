package xyz.luko.firebase

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val firebasePlatformModule =
    module {
        singleOf(::AppleFirebaseManager) bind FirebaseManager::class
    }

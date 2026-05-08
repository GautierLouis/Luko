package com.louisgautier.firebase

import com.louisgautier.utils.AppConfig
import org.koin.dsl.bind
import org.koin.dsl.module

actual val firebasePlatformModule =
    module {
        single {
            if (get<AppConfig>().isProduction) {
                AppleFirebaseManager()
            } else {
                DebugFirebaseManager()
            }
        } bind FirebaseManager::class
    }

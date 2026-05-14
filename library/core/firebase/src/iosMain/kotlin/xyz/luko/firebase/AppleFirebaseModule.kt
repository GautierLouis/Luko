package xyz.luko.firebase

import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.utils.AppConfig

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

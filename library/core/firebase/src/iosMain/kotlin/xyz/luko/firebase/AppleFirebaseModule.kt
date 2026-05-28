package xyz.luko.firebase

import org.koin.dsl.bind
import org.koin.dsl.module

actual val firebasePlatformModule =
    module {
        single {
//            if (get<AppConfig>().isProduction) {
                AppleFirebaseManager()
//            } else {
//                DebugFirebaseManager()
//            }
        } bind FirebaseManager::class
    }

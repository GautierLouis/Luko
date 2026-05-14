package xyz.luko.firebase

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.firebase.notification.AndroidPushNotificationManager
import xyz.luko.firebase.notification.PushNotificationManager

actual val firebasePlatformModule =
    module {
        singleOf(::AndroidPushNotificationManager) bind PushNotificationManager::class
        singleOf(::AndroidFirebaseManager) bind FirebaseManager::class
    }

package com.louisgautier.firebase

import com.louisgautier.firebase.notification.AndroidPushNotificationManager
import com.louisgautier.firebase.notification.PushNotificationManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val firebasePlatformModule =
    module {
        singleOf(::AndroidPushNotificationManager) bind PushNotificationManager::class
        singleOf(::AndroidFirebaseManager) bind FirebaseManager::class
    }

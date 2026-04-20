package com.louisgautier.firebase

import com.louisgautier.utils.AppConfig
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val firebasePlatformModule = module {
    single {
        if (get<AppConfig>().isProduction) AppleFirebaseManager()
        else DebugFirebaseManager()
    } bind FirebaseManager::class
}
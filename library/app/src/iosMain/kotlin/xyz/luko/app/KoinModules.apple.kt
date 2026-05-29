package xyz.luko.app

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSBundle

actual val libraryPlatformModule: Module =
    module {
        //Managed by MainViewController
        single(named(Environment.FLAVOR)) {
            val flavorStr = NSBundle.mainBundle.infoDictionary?.get("APP_FLAVOR") as? String
            when (flavorStr) {
                "dev" -> Environment.DEV
                "staging" -> Environment.STAGING
                else -> Environment.PROD
            }
        }

        single(named(Environment.PLATFORM)) {
            Environment.IOS
        }
        single(named(Environment.VERSION_CODE)) {
            NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
                ?: "unknown"
        }
        single(named(Environment.VERSION_NAME)) {
            NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String ?: "unknown"
        }
    }

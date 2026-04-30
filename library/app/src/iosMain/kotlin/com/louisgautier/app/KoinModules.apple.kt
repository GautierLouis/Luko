package com.louisgautier.app

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSBundle

actual val libraryPlatformModule: Module =
    module {
        single(named(Environment.FLAVOR)) {
            Environment.PROD
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

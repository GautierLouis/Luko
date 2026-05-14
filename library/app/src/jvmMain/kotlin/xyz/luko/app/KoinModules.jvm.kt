package xyz.luko.app

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val libraryPlatformModule: Module =
    module {
        single(named(Environment.FLAVOR)) {
            Environment.PROD
        }

        single(named(Environment.PLATFORM)) {
            Environment.DESKTOP
        }
        single(named(Environment.VERSION_CODE)) {
            "1.0.0"
        }
        single(named(Environment.VERSION_NAME)) {
            "1.0.0"
        }
    }

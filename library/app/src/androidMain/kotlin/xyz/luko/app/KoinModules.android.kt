package xyz.luko.app

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val libraryPlatformModule: Module =
    module {
        singleOf(::DefaultActivityObserver) bind ActivityObserver::class
    }

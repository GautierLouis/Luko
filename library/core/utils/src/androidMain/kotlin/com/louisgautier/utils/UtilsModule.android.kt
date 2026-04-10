package com.louisgautier.utils

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal actual val utilsPlatformModule: Module = module {
    singleOf(::IntentActivityResultObserver)
}
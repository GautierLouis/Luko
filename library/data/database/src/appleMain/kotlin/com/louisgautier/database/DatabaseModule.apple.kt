package com.louisgautier.database

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val databasePlatformModule: Module = module {
    singleOf(::AppleAppDatabaseBuilder) bind AppDatabaseBuilder::class
}
package xyz.luko.database

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val databasePlatformModule: Module =
    module {
        singleOf(::AndroidAppDataBaseBuilder) bind AppDatabaseBuilder::class
    }

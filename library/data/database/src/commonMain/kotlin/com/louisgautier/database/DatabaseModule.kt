package com.louisgautier.database

import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule =
    module {
        includes(databasePlatformModule)
        single { DatabaseProvider(get()).getDatabase() }
        single { get<AppDatabase>().getSessionDao() }
    }

internal expect val databasePlatformModule: Module

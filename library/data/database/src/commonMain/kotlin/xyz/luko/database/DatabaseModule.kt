package xyz.luko.database

import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule =
    module {
        includes(databasePlatformModule)
        single { DatabaseProvider(get()).getDatabase() }
        single { get<AppDatabase>().getSessionDao() }
        single { get<AppDatabase>().getSessionResponseDao() }
        single { get<AppDatabase>().getSyncSession() }
        single { get<AppDatabase>().getCharacterLevelDao() }
    }

internal expect val databasePlatformModule: Module

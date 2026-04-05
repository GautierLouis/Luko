package com.louisgautier.preferences

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val preferencesPlatformModule: Module = module {
    singleOf(::AndroidDataStore) bind DataStore::class
}
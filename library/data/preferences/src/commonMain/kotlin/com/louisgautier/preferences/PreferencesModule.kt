package com.louisgautier.preferences

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val preferencesModule =
    module {
        includes(preferencesPlatformModule)
        single {
            PreferenceDataStoreFactory.createWithPath(
                produceFile = { get<DataStore>().getPath() },
            )
        }
        singleOf(::DefaultAppPreferences) bind AppPreferences::class
    }

internal expect val preferencesPlatformModule: Module
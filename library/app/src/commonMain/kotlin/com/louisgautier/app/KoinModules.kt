package com.louisgautier.app

import com.louisgautier.app.app.AppViewModel
import com.louisgautier.app.main.MainViewModel
import com.louisgautier.dictionary.dictionaryModule
import com.louisgautier.domain.domainModule
import com.louisgautier.feed.feedModule
import com.louisgautier.firebase.firebaseModule
import com.louisgautier.home.homeModule
import com.louisgautier.learning.learningModule
import com.louisgautier.permission.permissionModule
import com.louisgautier.profile.profileModule
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.Flavor
import com.louisgautier.utils.utilsModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val libraryModule =
    module {
        includes(libraryPlatformModule)
        includeCoreModule()
        includeFeatureModule()
        includes(domainModule)

        single {
            val flavor = Flavor.valueOf(get<String>(named(Environment.FLAVOR)).uppercase())

            AppConfig(
                platform = get(named(Environment.PLATFORM)),
                flavor = flavor,
                versionName = get(named(Environment.VERSION_NAME)),
                versionCode = get(named(Environment.VERSION_CODE)),
            )
        }
        viewModelOf(::AppViewModel)
        viewModelOf(::MainViewModel)
    }

private fun Module.includeCoreModule() {
    includes(
        utilsModule,
        permissionModule,
        firebaseModule,
    )
}

private fun Module.includeFeatureModule() {
    includes(
        homeModule,
        feedModule,
        profileModule,
        learningModule,
        dictionaryModule,
    )
}

internal expect val libraryPlatformModule: Module

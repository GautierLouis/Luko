package com.louisgautier.composeApp

import com.louisgautier.composeApp.app.AppViewModel
import com.louisgautier.composeApp.home.HomeViewModel
import com.louisgautier.composeApp.main.MainViewModel
import com.louisgautier.core.coreModule
import com.louisgautier.domain.domainModule
import com.louisgautier.feature.featureModule
import com.louisgautier.utils.AppConfig
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val libraryModule = module {
    includes(
        libraryPlatformModule,
        featureModule,
        domainModule,
        coreModule,
    )

    single<AppConfig> {
        get<AppConfigBuilder>()()
    }

    viewModelOf(::AppViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)
}

internal expect val libraryPlatformModule: Module
package com.louisgautier.app

import com.louisgautier.app.app.AppViewModel
import com.louisgautier.app.main.MainViewModel
import com.louisgautier.core.coreModule
import com.louisgautier.domain.domainModule
import com.louisgautier.feature.featureModule
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.Flavor
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val libraryModule = module {
    includes(
        libraryPlatformModule,
        featureModule,
        domainModule,
        coreModule,
    )

    single {
        val flavor = Flavor.valueOf(get<String>(named(Environment.FLAVOR)))

        AppConfig(
            platform = get(named(Environment.PLATFORM)),
            flavor = flavor,
            isProduction = get<String>(named(Environment.FLAVOR)) == Environment.PROD,
            versionName = get(named(Environment.VERSION_NAME)),
            versionCode = get(named(Environment.VERSION_CODE))
        )
    }
    viewModelOf(::AppViewModel)
    viewModelOf(::MainViewModel)
}

internal expect val libraryPlatformModule: Module
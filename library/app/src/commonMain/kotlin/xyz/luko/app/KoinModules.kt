package xyz.luko.app

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.luko.app.app.AppViewModel
import xyz.luko.app.main.MainViewModel
import xyz.luko.dictionary.dictionaryModule
import xyz.luko.domain.domainModule
import xyz.luko.feed.feedModule
import xyz.luko.firebase.firebaseModule
import xyz.luko.home.homeModule
import xyz.luko.learning.learningDebugModule
import xyz.luko.learning.learningModule
import xyz.luko.onboarding.onboardingModule
import xyz.luko.permission.permissionModule
import xyz.luko.profile.profileModule
import xyz.luko.recognition.recognitionModule
import xyz.luko.sessions.sessionsModule
import xyz.luko.utils.AppConfig
import xyz.luko.utils.DebugAction
import xyz.luko.utils.Flavor
import xyz.luko.utils.utilsModule

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

        /**
         * Include only debug actions in debug builds
         */
        includeDebugModule()
        single<List<DebugAction>> {
            if (get<AppConfig>().isProduction) emptyList() else getAll<DebugAction>()
        }
    }

private fun Module.includeCoreModule() {
    includes(
        utilsModule,
        permissionModule,
        firebaseModule,
        recognitionModule
    )
}

private fun Module.includeFeatureModule() {
    includes(
        homeModule,
        feedModule,
        profileModule,
        learningModule,
        sessionsModule,
        dictionaryModule,
        onboardingModule,
    )
}

private fun Module.includeDebugModule() {
    includes(
        learningDebugModule,
    )
}

internal expect val libraryPlatformModule: Module

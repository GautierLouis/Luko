package xyz.luko.network

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.network.NamedNetworkModule.AUTHED_CLIENT
import xyz.luko.network.NamedNetworkModule.UNAUTHED_CLIENT
import xyz.luko.network.interfaces.AuthService
import xyz.luko.network.interfaces.CharacterService
import xyz.luko.network.interfaces.UserService
import xyz.luko.utils.AppConfig
import xyz.luko.utils.Flavor

private object NamedNetworkModule {
    const val UNAUTHED_CLIENT = "UNAUTHED_CLIENT"
    const val AUTHED_CLIENT = "AUTHED_CLIENT"
}

val networkModule: Module = module {
    single {
        val baseUrl = when (get<AppConfig>().flavor) {
            Flavor.DEV -> "10.0.2.2"
            Flavor.STAGING -> "https://learn-chinese-staging.up.railway.app"
            Flavor.PROD -> "https://learn-chinese-production.up.railway.app"
        }

        DefaultService(
            tokenAccessor = get(),
            baseUrl = baseUrl,
            appConfig = get()
        )
    }
    single(named(UNAUTHED_CLIENT)) { get<DefaultService>().unauthedClient }
    single(named(AUTHED_CLIENT)) { get<DefaultService>().authedClient }

    single { DefaultAuthService(get(named(UNAUTHED_CLIENT))) } bind AuthService::class
    single { DefaultUserService(get(named(AUTHED_CLIENT))) } bind UserService::class
    single { DefaultCharacterService(get(named(UNAUTHED_CLIENT))) } bind CharacterService::class
}

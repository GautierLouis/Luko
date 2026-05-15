package xyz.luko.network

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.network.NamedNetworkModule.AUTHED_CLIENT
import xyz.luko.network.NamedNetworkModule.UNAUTHED_CLIENT
import xyz.luko.network.interfaces.AuthService
import xyz.luko.network.interfaces.CharacterService
import xyz.luko.network.interfaces.UserService

private object NamedNetworkModule {
    const val UNAUTHED_CLIENT = "UNAUTHED_CLIENT"
    const val AUTHED_CLIENT = "AUTHED_CLIENT"
}

val networkModule: Module = module {
    singleOf(::DefaultService)

    single(named(UNAUTHED_CLIENT)) { get<DefaultService>().unauthedClient }
    single(named(AUTHED_CLIENT)) { get<DefaultService>().authedClient }

    single { DefaultAuthService(get(named(UNAUTHED_CLIENT))) } bind AuthService::class
    single { DefaultUserService(get(named(AUTHED_CLIENT))) } bind UserService::class
    single { DefaultCharacterService(get(named(UNAUTHED_CLIENT))) } bind CharacterService::class
}

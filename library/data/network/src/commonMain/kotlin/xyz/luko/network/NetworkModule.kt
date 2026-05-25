package xyz.luko.network

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.network.interfaces.AuthService
import xyz.luko.network.interfaces.CharacterService
import xyz.luko.network.interfaces.UserService


val networkModule: Module =
    module {
        single { engineFactory.create() }
        single { buildHttpClient(get(), get(), get()) }

        singleOf(::DefaultAuthService) bind AuthService::class
        singleOf(::DefaultUserService) bind UserService::class
        singleOf(::DefaultCharacterService) bind CharacterService::class
    }

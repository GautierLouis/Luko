package xyz.luko.domain

import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.auth.authModule
import xyz.luko.database.databaseModule
import xyz.luko.domain.repository.DefaultDictionaryRepository
import xyz.luko.domain.repository.DefaultSessionRepository
import xyz.luko.domain.repository.DefaultUserRepository
import xyz.luko.domain.repository.DictionaryRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.UserRepository
import xyz.luko.network.networkModule
import xyz.luko.preferences.preferencesModule

val domainModule =
    module {
        includes(networkModule, databaseModule, preferencesModule, authModule)

        single { DefaultDictionaryRepository(get()) } bind DictionaryRepository::class
        single { DefaultSessionRepository(get()) } bind SessionRepository::class

        single { DefaultUserRepository(get()) } bind UserRepository::class
    }

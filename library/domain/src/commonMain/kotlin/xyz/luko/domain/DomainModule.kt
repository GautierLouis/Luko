package xyz.luko.domain

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.database.databaseModule
import xyz.luko.domain.provider.DefaultFcmProvider
import xyz.luko.domain.provider.DefaultTokenProvider
import xyz.luko.domain.repository.AuthRepository
import xyz.luko.domain.repository.DefaultAuthRepository
import xyz.luko.domain.repository.DefaultDictionaryRepository
import xyz.luko.domain.repository.DefaultSessionRepository
import xyz.luko.domain.repository.DefaultUserRepository
import xyz.luko.domain.repository.DictionaryRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.UserRepository
import xyz.luko.firebase.notification.FcmProvider
import xyz.luko.network.interfaces.TokenProvider
import xyz.luko.network.networkModule
import xyz.luko.preferences.preferencesModule

val domainModule =
    module {
        includes(networkModule, databaseModule, preferencesModule)

        singleOf(::DefaultTokenProvider) bind TokenProvider::class
        singleOf(::DefaultFcmProvider) bind FcmProvider::class
        singleOf(::DefaultAuthRepository) bind AuthRepository::class
        singleOf(::DefaultDictionaryRepository) bind DictionaryRepository::class
        singleOf(::DefaultSessionRepository) bind SessionRepository::class
        singleOf(::DefaultUserRepository) bind UserRepository::class
    }

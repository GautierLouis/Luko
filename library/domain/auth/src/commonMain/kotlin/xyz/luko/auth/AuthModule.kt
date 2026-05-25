package xyz.luko.auth

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.firebase.notification.FcmAccessor
import xyz.luko.network.interfaces.TokenProvider

val authModule =
    module {
        singleOf(::DefaultTokenProvider) bind TokenProvider::class
        singleOf(::DefaultFcmAccessor) bind FcmAccessor::class
        singleOf(::DefaultAuthRepository) bind AuthRepository::class
    }

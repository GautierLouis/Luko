package xyz.luko.auth

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.firebase.notification.FcmAccessor
import xyz.luko.network.interfaces.TokenAccessor

val authModule =
    module {
        singleOf(::DefaultTokenAccessor) bind TokenAccessor::class
        singleOf(::DefaultFcmAccessor) bind FcmAccessor::class
        singleOf(::DefaultAuthRepository) bind AuthRepository::class
    }

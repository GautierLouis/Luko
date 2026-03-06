package com.louisgautier.auth

import com.louisgautier.network.interfaces.TokenAccessor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::DefaultTokenAccessor) bind TokenAccessor::class
    singleOf(::DefaultAuthRepository) bind AuthRepository::class
}
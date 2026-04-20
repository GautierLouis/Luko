package com.louisgautier.server.auth

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {
    singleOf(::JwtProvider)
}
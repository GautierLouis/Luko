package com.louisgautier.domain

import com.louisgautier.auth.authModule
import com.louisgautier.data.dataModule
import com.louisgautier.domain.repository.CharacterRepository
import com.louisgautier.domain.repository.DefaultCharacterRepository
import com.louisgautier.domain.repository.DefaultSessionRepository
import com.louisgautier.domain.repository.DefaultUserRepository
import com.louisgautier.domain.repository.SessionRepository
import com.louisgautier.domain.repository.UserRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    includes(authModule, dataModule)

    single { DefaultCharacterRepository(get()) } bind CharacterRepository::class
    single { DefaultSessionRepository(get()) } bind SessionRepository::class

    single { DefaultUserRepository(get()) } bind UserRepository::class
}
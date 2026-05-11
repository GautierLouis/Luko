package com.louisgautier.domain

import com.louisgautier.auth.authModule
import com.louisgautier.database.databaseModule
import com.louisgautier.domain.repository.CharacterRepository
import com.louisgautier.domain.repository.DefaultCharacterRepository
import com.louisgautier.domain.repository.DefaultSessionRepository
import com.louisgautier.domain.repository.DefaultUserRepository
import com.louisgautier.domain.repository.SessionRepository
import com.louisgautier.domain.repository.UserRepository
import com.louisgautier.network.networkModule
import com.louisgautier.preferences.preferencesModule
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule =
    module {
        includes(networkModule, databaseModule, preferencesModule, authModule)

        single { DefaultCharacterRepository(get()) } bind CharacterRepository::class
        single { DefaultSessionRepository(get()) } bind SessionRepository::class

        single { DefaultUserRepository(get()) } bind UserRepository::class
    }

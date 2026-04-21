package com.louisgautier.server.domain

import com.louisgautier.server.domain.repo.AuthenticationRepository
import com.louisgautier.server.domain.repo.DictionaryRepository
import com.louisgautier.server.domain.repo.GraphicRepository
import com.louisgautier.server.domain.repo.UserRepository
import com.louisgautier.server.domain.repo.implem.DefaultAuthenticationRepository
import com.louisgautier.server.domain.repo.implem.DefaultDictionaryRepository
import com.louisgautier.server.domain.repo.implem.DefaultGraphicRepository
import com.louisgautier.server.domain.repo.implem.DefaultUserRepository
import com.louisgautier.server.domain.usecase.GetFullDictionary
import com.louisgautier.server.parser.FileParser
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    singleOf(::DefaultAuthenticationRepository) bind AuthenticationRepository::class
    singleOf(::DefaultDictionaryRepository) bind DictionaryRepository::class
    singleOf(::DefaultGraphicRepository) bind GraphicRepository::class
    singleOf(::DefaultUserRepository) bind UserRepository::class
    singleOf(::FileParser)

    factoryOf(::GetFullDictionary)
}
package xyz.luko.server.domain

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.domain.repo.DefaultDictionaryRepository
import xyz.luko.server.domain.repo.DefaultSessionRepository
import xyz.luko.server.domain.repo.DefaultUserRepository
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.domain.repo.SessionRepository
import xyz.luko.server.domain.repo.UserRepository
import xyz.luko.server.domain.usecase.CompositionUseCase
import xyz.luko.server.domain.usecase.PrepopulateDatabaseUseCase
import xyz.luko.server.domain.usecase.StrokeRenderingUseCase

val domainModule = module {
    singleOf(::DefaultUserRepository) bind UserRepository::class
    singleOf(::DefaultSessionRepository) bind SessionRepository::class
    singleOf(::DefaultDictionaryRepository) bind DictionaryRepository::class

    factoryOf(::PrepopulateDatabaseUseCase)
    factoryOf(::CompositionUseCase)
    factoryOf(::StrokeRenderingUseCase)
}

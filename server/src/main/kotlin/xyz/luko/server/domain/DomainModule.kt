package xyz.luko.server.domain

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.fsrscore.AnalyseResultUseCase
import xyz.luko.server.domain.auth.DefaultTokenVerifier
import xyz.luko.server.domain.auth.TokenVerifier
import xyz.luko.server.domain.repo.DefaultDictionaryRepository
import xyz.luko.server.domain.repo.DefaultProgressionRepository
import xyz.luko.server.domain.repo.DefaultSessionRepository
import xyz.luko.server.domain.repo.DefaultUserRepository
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.domain.repo.ProgressionRepository
import xyz.luko.server.domain.repo.SessionRepository
import xyz.luko.server.domain.repo.UserRepository
import xyz.luko.server.domain.usecase.CharacterComplexityUseCase
import xyz.luko.server.domain.usecase.DecompositionParser
import xyz.luko.server.domain.usecase.LevelUseCase
import xyz.luko.server.domain.usecase.PrepopulateDatabaseUseCase
import xyz.luko.server.domain.usecase.ReviewUseCase
import xyz.luko.server.domain.usecase.StreakUseCase
import xyz.luko.server.domain.usecase.StrokeRenderingUseCase

val domainModule = module {
    singleOf(::DefaultUserRepository) bind UserRepository::class
    singleOf(::DefaultSessionRepository) bind SessionRepository::class
    singleOf(::DefaultDictionaryRepository) bind DictionaryRepository::class
    singleOf(::DefaultProgressionRepository) bind ProgressionRepository::class

    singleOf(::DefaultTokenVerifier) bind TokenVerifier::class

    factoryOf(::PrepopulateDatabaseUseCase)
    factoryOf(::DecompositionParser)
    factoryOf(::CharacterComplexityUseCase)
    factoryOf(::StrokeRenderingUseCase)
    factoryOf(::ReviewUseCase)
    factoryOf(::LevelUseCase)
    factoryOf(::StreakUseCase)

    factoryOf(::AnalyseResultUseCase)
}

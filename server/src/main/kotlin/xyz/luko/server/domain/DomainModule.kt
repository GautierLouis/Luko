package xyz.luko.server.domain

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.domain.repo.AuthenticationRepository
import xyz.luko.server.domain.repo.DefaultAuthenticationRepository
import xyz.luko.server.domain.repo.DefaultDictionaryRepository
import xyz.luko.server.domain.repo.DefaultSessionRepository
import xyz.luko.server.domain.repo.DefaultUserRepository
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.domain.repo.SessionRepository
import xyz.luko.server.domain.repo.UserRepository
import xyz.luko.server.domain.usecase.CompositionUseCase
import xyz.luko.server.domain.usecase.PrepopulateDatabaseUseCase
import xyz.luko.server.domain.usecase.StrokeRenderingUseCase
import xyz.luko.server.supabase.SupabaseClientMode

val domainModule = module {
    single {
        DefaultAuthenticationRepository(
            client = get(named(SupabaseClientMode.USER)),
            userRepository = get(),
            config = get(),
        )
    } bind AuthenticationRepository::class
    singleOf(::DefaultSessionRepository) bind SessionRepository::class
    singleOf(::DefaultDictionaryRepository) bind DictionaryRepository::class
    singleOf(::DefaultUserRepository) bind UserRepository::class

    factoryOf(::PrepopulateDatabaseUseCase)
    factoryOf(::CompositionUseCase)
    factoryOf(::StrokeRenderingUseCase)
}

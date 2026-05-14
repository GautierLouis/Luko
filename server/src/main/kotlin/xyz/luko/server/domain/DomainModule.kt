package xyz.luko.server.domain

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.domain.repo.AuthenticationRepository
import xyz.luko.server.domain.repo.CompositionUseCase
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.domain.repo.FileRepository
import xyz.luko.server.domain.repo.GraphicRepository
import xyz.luko.server.domain.repo.UserRepository
import xyz.luko.server.domain.repo.implem.DefaultAuthenticationRepository
import xyz.luko.server.domain.repo.implem.DefaultDictionaryRepository
import xyz.luko.server.domain.repo.implem.DefaultFileRepository
import xyz.luko.server.domain.repo.implem.DefaultGraphicRepository
import xyz.luko.server.domain.repo.implem.DefaultUserRepository
import xyz.luko.server.domain.usecase.GetFullDictionaryUseCase
import xyz.luko.server.domain.usecase.PrepopulateDatabaseUseCase
import xyz.luko.server.supabase.SupabaseClientMode

val domainModule = module {
    single {
        DefaultAuthenticationRepository(
            client = get(named(SupabaseClientMode.USER)),
            userRepository = get(),
            config = get(),
        )
    } bind AuthenticationRepository::class
    singleOf(::DefaultDictionaryRepository) bind DictionaryRepository::class
    singleOf(::DefaultGraphicRepository) bind GraphicRepository::class
    singleOf(::DefaultUserRepository) bind UserRepository::class
    singleOf(::DefaultFileRepository) bind FileRepository::class

    factoryOf(::PrepopulateDatabaseUseCase)
    factoryOf(::GetFullDictionaryUseCase)
    factoryOf(::CompositionUseCase)
}
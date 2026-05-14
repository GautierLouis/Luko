package xyz.luko.server.mock

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.database.Database
import xyz.luko.server.database.source.DefaultDictionaryDataSource
import xyz.luko.server.database.source.DefaultGraphicDataSource
import xyz.luko.server.database.source.DictionaryDataSource
import xyz.luko.server.database.source.FileDataSource
import xyz.luko.server.database.source.GraphicDataSource
import xyz.luko.server.di.serverModule
import xyz.luko.server.domain.repo.AuthenticationRepository
import xyz.luko.server.supabase.SupabaseClientMode

private val testDatabaseModule = module {
    single<Database> { FakeDatabase() }
    singleOf(::DefaultDictionaryDataSource) bind DictionaryDataSource::class
    singleOf(::DefaultGraphicDataSource) bind GraphicDataSource::class
    single<FileDataSource> { FakeFileDataSource() }
}

private val testSupabaseModule = module {
    single<SupabaseClient>(named(SupabaseClientMode.USER)) {
        createSupabaseClient("http://localhost", "fake-key") { install(Auth) }
    }
    single<SupabaseClient>(named(SupabaseClientMode.ADMIN)) {
        createSupabaseClient("http://localhost", "fake-key") { install(Storage) }
    }
}

val testModule = module {
    includes(serverModule, testSupabaseModule, testDatabaseModule)
    single<AuthenticationRepository> { FakeAuthRepo() }
}

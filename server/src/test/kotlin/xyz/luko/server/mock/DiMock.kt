package xyz.luko.server.mock

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.data.database.Database
import xyz.luko.server.data.database.dao.DefaultDictionaryDao
import xyz.luko.server.data.database.dao.DictionaryDao
import xyz.luko.server.data.storage.StorageSource
import xyz.luko.server.di.serverModule
import xyz.luko.server.domain.repo.UserRepository

private val testDatabaseModule = module {
    single<Database> { FakeDatabase() }
    singleOf(::DefaultDictionaryDao) bind DictionaryDao::class
    single<StorageSource> { FakeStorageSource() }
}

private val testSupabaseModule = module {
    single {
        createSupabaseClient("http://localhost", "fake-key") { install(Auth) }
    }
}

val testModule = module {
    includes(serverModule, testSupabaseModule, testDatabaseModule)
    single<UserRepository> { FakeAuthRepo() }
}

package com.louisgautier.server.mock

import com.louisgautier.server.ServerRegistry
import com.louisgautier.server.auth.authModule
import com.louisgautier.server.database.Database
import com.louisgautier.server.database.source.DefaultDictionaryDataSource
import com.louisgautier.server.database.source.DefaultGraphicDataSource
import com.louisgautier.server.database.source.DictionaryDataSource
import com.louisgautier.server.database.source.FileDataSource
import com.louisgautier.server.database.source.GraphicDataSource
import com.louisgautier.server.domain.domainModule
import com.louisgautier.server.domain.repo.AuthenticationRepository
import com.louisgautier.server.plugin.pluginModule
import com.louisgautier.server.router.routerModule
import com.louisgautier.server.supabase.SupabaseClientMode
import com.louisgautier.server.supabase.supabaseModule
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private val testDatabaseModule = module {
    single<Database> { FakeDatabase() }
    singleOf(::DefaultDictionaryDataSource) bind DictionaryDataSource::class
    singleOf(::DefaultGraphicDataSource) bind GraphicDataSource::class
    single<FileDataSource> { FakeFileDataSource() }
}

private val testSupabaseModule = module {
    // Satisfy Koin graph without real connections
    single<SupabaseClient>(named(SupabaseClientMode.USER)) {
        createSupabaseClient(
            supabaseUrl = "http://localhost",
            supabaseKey = "fake-key"
        ) { install(Auth) }
    }
    single<SupabaseClient>(named(SupabaseClientMode.ADMIN)) {
        createSupabaseClient(
            supabaseUrl = "http://localhost",
            supabaseKey = "fake-key"
        ) { install(Storage) }
    }
}

val testModule = module {
    includes(
        domainModule,
        supabaseModule,
        routerModule,
        authModule,
        pluginModule,
        testSupabaseModule,
        testDatabaseModule
    )
    singleOf(::ServerRegistry)
    single<AuthenticationRepository> { FakeAuthRepo() }

}

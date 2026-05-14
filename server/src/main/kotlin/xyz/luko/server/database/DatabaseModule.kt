package xyz.luko.server.database

import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.database.source.DefaultDictionaryDataSource
import xyz.luko.server.database.source.DefaultFileDataSource
import xyz.luko.server.database.source.DefaultGraphicDataSource
import xyz.luko.server.database.source.DictionaryDataSource
import xyz.luko.server.database.source.FileDataSource
import xyz.luko.server.database.source.GraphicDataSource
import xyz.luko.server.supabase.SupabaseClientMode

val databaseModule = module {
    singleOf(::DefaultDatabase) bind Database::class
    singleOf(::DefaultDictionaryDataSource) bind DictionaryDataSource::class
    singleOf(::DefaultGraphicDataSource) bind GraphicDataSource::class
    single {
        DefaultFileDataSource(
            get(named(SupabaseClientMode.ADMIN))
        )
    } bind FileDataSource::class
}
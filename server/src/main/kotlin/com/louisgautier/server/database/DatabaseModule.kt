package com.louisgautier.server.database

import com.louisgautier.server.database.source.DefaultDictionaryDataSource
import com.louisgautier.server.database.source.DefaultFileDataSource
import com.louisgautier.server.database.source.DefaultGraphicDataSource
import com.louisgautier.server.database.source.DictionaryDataSource
import com.louisgautier.server.database.source.FileDataSource
import com.louisgautier.server.database.source.GraphicDataSource
import com.louisgautier.server.supabase.SupabaseClientMode
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

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
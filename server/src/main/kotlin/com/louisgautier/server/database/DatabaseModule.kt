package com.louisgautier.server.database

import com.louisgautier.server.database.source.DefaultDictionaryDataSource
import com.louisgautier.server.database.source.DefaultGraphicDataSource
import com.louisgautier.server.database.source.DictionaryDataSource
import com.louisgautier.server.database.source.GraphicDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::Database)
    singleOf(::DefaultDictionaryDataSource) bind DictionaryDataSource::class
    singleOf(::DefaultGraphicDataSource) bind GraphicDataSource::class
}
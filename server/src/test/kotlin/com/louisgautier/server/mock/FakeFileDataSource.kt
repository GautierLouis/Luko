package com.louisgautier.server.mock

import com.louisgautier.server.database.source.FileDataSource
import com.louisgautier.server.domain.usecase.parser.CsvRow
import com.louisgautier.server.domain.usecase.parser.DictionaryParsed
import com.louisgautier.server.domain.usecase.parser.GraphicParser

class FakeFileDataSource : FileDataSource {
    override suspend fun loadGraphicFile(): List<GraphicParser> {
        return emptyList()
    }

    override suspend fun loadDictionaryFile(): List<DictionaryParsed> {
        return emptyList()
    }

    override suspend fun loadHanziFile(): List<CsvRow> {
        return emptyList()
    }
}
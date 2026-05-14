package xyz.luko.server.mock

import xyz.luko.server.database.source.FileDataSource
import xyz.luko.server.domain.usecase.parser.CsvRow
import xyz.luko.server.domain.usecase.parser.DictionaryParsed
import xyz.luko.server.domain.usecase.parser.GraphicParser

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
package xyz.luko.server.database.source

import xyz.luko.server.domain.usecase.parser.CsvRow
import xyz.luko.server.domain.usecase.parser.DictionaryParsed
import xyz.luko.server.domain.usecase.parser.GraphicParser

interface FileDataSource {
    suspend fun loadGraphicFile(): List<GraphicParser>
    suspend fun loadDictionaryFile(): List<DictionaryParsed>
    suspend fun loadHanziFile(): List<CsvRow>
}
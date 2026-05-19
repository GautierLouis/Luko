package xyz.luko.server.database.source

import xyz.luko.server.domain.mapper.parsing.CsvRow
import xyz.luko.server.domain.mapper.parsing.DictionaryParsed
import xyz.luko.server.domain.mapper.parsing.GraphicParser

interface FileDataSource {
    suspend fun loadGraphicFile(): List<GraphicParser>
    suspend fun loadDictionaryFile(): List<DictionaryParsed>
    suspend fun loadHanziFile(): List<CsvRow>
    suspend fun loadFont(): ByteArray
}

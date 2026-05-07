package com.louisgautier.server.database.source

import com.louisgautier.server.domain.usecase.parser.CsvRow
import com.louisgautier.server.domain.usecase.parser.DictionaryParsed
import com.louisgautier.server.domain.usecase.parser.GraphicParser

interface FileDataSource {
    suspend fun loadGraphicFile(): List<GraphicParser>
    suspend fun loadDictionaryFile(): List<DictionaryParsed>
    suspend fun loadHanziFile(): List<CsvRow>
}
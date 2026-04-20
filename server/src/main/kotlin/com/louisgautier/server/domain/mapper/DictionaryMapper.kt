package com.louisgautier.server.domain.mapper

import com.louisgautier.apicontracts.dto.DecompositionDto
import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.EtymologyDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto
import com.louisgautier.server.database.entity.DictionaryTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toDictionary() = DictionaryDto(
    code = this[DictionaryTable.code],
    definition = this[DictionaryTable.definition].orEmpty(),
    pinyin = this[DictionaryTable.pinyin].orEmpty().split(","),
    decomposition = this[DictionaryTable.decomposition],
    decompositionList = Json.decodeFromString<List<DecompositionDto>>(this[DictionaryTable.decompositionList].orEmpty()),
    level = this[DictionaryTable.level],
    etymology = EtymologyDto(
        type = this[DictionaryTable.etymologyType],
        phonetic = this[DictionaryTable.etymologyPhonetic],
        semantic = this[DictionaryTable.etymologySemantic],
        hint = this[DictionaryTable.etymologyHint],
    ),
    radical = this[DictionaryTable.radical],
    matches = Json.decodeFromString<List<List<Int>?>>(this[DictionaryTable.matches].orEmpty()),
)

fun ResultRow.toSimpleDictionary() = SimpleDictionaryDto(
    code = this[DictionaryTable.code],
    pinyin = this[DictionaryTable.pinyin].orEmpty().split(","),
    level = this[DictionaryTable.level],
)

fun ResultRow.toDictionaryWithGraphic() = DictionaryWithGraphicDto(
    dictionary = this.toDictionary(),
    graphics = this.toGraphic()
)
package com.louisgautier.server.domain.mapper

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DecompositionDto
import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.EtymologyDto
import com.louisgautier.apicontracts.dto.EtymologyTypeDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto
import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.domain.model.CharacterFrequencyLevelEntity
import com.louisgautier.server.domain.model.DictionaryEntity
import com.louisgautier.server.domain.model.EtymologyTypeEntity
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toDictionary() = DictionaryDto(
    code = this[DictionaryTable.code],
    definition = this[DictionaryTable.definition].orEmpty(),
    pinyin = this[DictionaryTable.pinyin].orEmpty().split(","),
    decomposition = this[DictionaryTable.decomposition],
    decompositionList = Json.decodeFromString<List<DecompositionDto>>(this[DictionaryTable.decompositionList].orEmpty()),
    level = this[DictionaryTable.level].toDto(),
    etymology = EtymologyDto(
        type = this[DictionaryTable.etymologyType]?.toDto(),
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
    level = this[DictionaryTable.level].toDto(),
)

fun ResultRow.toDictionaryWithGraphic() = DictionaryWithGraphicDto(
    dictionary = this.toDictionary(),
    graphics = this.toGraphic()
)


fun DictionaryDto.toDictionaryEntity() = DictionaryEntity(
    code = code,
    definition = definition,
    pinyin = pinyin.joinToString(),
    decomposition = decomposition,
    decompositionList = Json.encodeToString(decompositionList),
    level = level.toEntity(),
    etymologyType = etymology?.type?.toEntity(),
    etymologyPhonetic = etymology?.phonetic,
    etymologySemantic = etymology?.semantic,
    etymologyHint = etymology?.hint,
    radical = radical,
    matches = Json.encodeToString(matches)
)

fun CharacterFrequencyLevelDto.toEntity() = when (this) {
    CharacterFrequencyLevelDto.UNKNOWN -> CharacterFrequencyLevelEntity.UNKNOWN
    CharacterFrequencyLevelDto.COMMON -> CharacterFrequencyLevelEntity.COMMON
    CharacterFrequencyLevelDto.FREQUENT -> CharacterFrequencyLevelEntity.FREQUENT
    CharacterFrequencyLevelDto.STANDARD -> CharacterFrequencyLevelEntity.STANDARD
    CharacterFrequencyLevelDto.EXTENDED -> CharacterFrequencyLevelEntity.EXTENDED
    CharacterFrequencyLevelDto.RARE -> CharacterFrequencyLevelEntity.RARE
    CharacterFrequencyLevelDto.OBSOLETE -> CharacterFrequencyLevelEntity.OBSOLETE
}

fun CharacterFrequencyLevelEntity.toDto() = when (this) {
    CharacterFrequencyLevelEntity.UNKNOWN -> CharacterFrequencyLevelDto.UNKNOWN
    CharacterFrequencyLevelEntity.COMMON -> CharacterFrequencyLevelDto.COMMON
    CharacterFrequencyLevelEntity.FREQUENT -> CharacterFrequencyLevelDto.FREQUENT
    CharacterFrequencyLevelEntity.STANDARD -> CharacterFrequencyLevelDto.STANDARD
    CharacterFrequencyLevelEntity.EXTENDED -> CharacterFrequencyLevelDto.EXTENDED
    CharacterFrequencyLevelEntity.RARE -> CharacterFrequencyLevelDto.RARE
    CharacterFrequencyLevelEntity.OBSOLETE -> CharacterFrequencyLevelDto.OBSOLETE
}

fun EtymologyTypeEntity.toDto() = when (this) {
    EtymologyTypeEntity.IDEOGRAPHIC -> EtymologyTypeDto.IDEOGRAPHIC
    EtymologyTypeEntity.PICTOGRAPHIC -> EtymologyTypeDto.PICTOGRAPHIC
    EtymologyTypeEntity.PICTOPHONETIC -> EtymologyTypeDto.PICTOPHONETIC
}

fun EtymologyTypeDto.toEntity() = when (this) {
    EtymologyTypeDto.IDEOGRAPHIC -> EtymologyTypeEntity.IDEOGRAPHIC
    EtymologyTypeDto.PICTOGRAPHIC -> EtymologyTypeEntity.PICTOGRAPHIC
    EtymologyTypeDto.PICTOPHONETIC -> EtymologyTypeEntity.PICTOPHONETIC
}
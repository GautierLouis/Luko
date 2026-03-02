package com.louisgautier.domain.mapper

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DecompositionDto
import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.EtymologyDto
import com.louisgautier.apicontracts.dto.EtymologyTypeDto
import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.apicontracts.dto.LevelCountDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto
import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.Decomposition
import com.louisgautier.domain.model.Dictionary
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Etymology
import com.louisgautier.domain.model.EtymologyType
import com.louisgautier.domain.model.Graphic
import com.louisgautier.domain.model.Point
import com.louisgautier.domain.model.ResponseList
import com.louisgautier.domain.model.SimpleDictionary
import com.louisgautier.domain.model.Stroke

fun LevelCountDto.toDomain() = LevelCount(
    level = level.toDomain(),
    count = count
)

fun LevelCount.toDto() = LevelCountDto(
    level = level.toDto(),
    count = count
)

fun SimpleDictionaryDto.toDomain() = SimpleDictionary(
    code = code,
    pinyin = pinyin,
    level = level.toDomain()
)

fun CharacterFrequencyLevelDto.toDomain() =
    CharacterFrequencyLevel.valueOf(this.name)

fun CharacterFrequencyLevel.toDto() =
    CharacterFrequencyLevelDto.valueOf(this.name)

fun GraphicDto.toDomain() = Graphic(
    code = code,
    strokes = strokes,
    medians = medians.map { s -> Stroke(s.map { p -> Point(p[0], p[1]) }) }
)

fun <T, U> ResponseListDto<T>.toDomain(converter : (T) -> U) =
    ResponseList(hasNextPage, data.map { converter(it) })

fun DecompositionDto.toDomain() =
    Decomposition(symbolCode, glyphsCode)

fun EtymologyDto.toDomain() =
    Etymology(
        type = type?.toDomain(),
        phonetic = phonetic,
        semantic = semantic,
        hint = hint
    )

fun EtymologyTypeDto.toDomain() =
    EtymologyType.valueOf(this.name)

fun DictionaryDto.toDomain() =
    Dictionary(
        code = code,
        definition = definition,
        pinyin = pinyin,
        decomposition = decomposition,
        decompositionList = decompositionList.map { it.toDomain() },
        level = level.toDomain(),
        etymology = etymology?.toDomain(),
        radical = radical,
        matches = matches
    )

fun DictionaryWithGraphicDto.toDomain() =
    DictionaryWithGraphic(dictionary.toDomain(), graphics.toDomain())


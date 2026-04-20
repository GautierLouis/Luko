package com.louisgautier.domain.mapper

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DecompositionDto
import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.EtymologyDto
import com.louisgautier.apicontracts.dto.EtymologyTypeDto
import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.apicontracts.dto.LevelCountDto
import com.louisgautier.apicontracts.dto.PointDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto
import com.louisgautier.apicontracts.dto.StrokeDto
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

internal fun LevelCountDto.toDomain() = LevelCount(
    level = level.toDomain(),
    count = count
)

internal fun LevelCount.toDto() = LevelCountDto(
    level = level.toDto(),
    count = count
)

internal fun SimpleDictionaryDto.toDomain() = SimpleDictionary(
    code = code,
    pinyin = pinyin,
    level = level.toDomain()
)

internal fun CharacterFrequencyLevelDto.toDomain() =
    CharacterFrequencyLevel.valueOf(this.name)

internal fun CharacterFrequencyLevel.toDto() =
    CharacterFrequencyLevelDto.valueOf(this.name)

internal fun GraphicDto.toDomain() = Graphic(
    code = code,
    strokes = strokes,
    medians = medians.map { it.toDomain() }
)

internal fun StrokeDto.toDomain() = Stroke(
    points = this.points.map { it.toDomain() }
)

internal fun PointDto.toDomain() = Point(
    x = this.x,
    y = this.y
)

internal fun <T, U> ResponseListDto<T>.toDomain(converter: (T) -> U) =
    ResponseList(hasNextPage, data.map { converter(it) })

internal fun DecompositionDto.toDomain() =
    Decomposition(symbolCode, glyphsCode)

internal fun EtymologyDto.toDomain() =
    Etymology(
        type = type?.toDomain(),
        phonetic = phonetic,
        semantic = semantic,
        hint = hint
    )

internal fun EtymologyTypeDto.toDomain() =
    EtymologyType.valueOf(this.name)

internal fun DictionaryDto.toDomain() =
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

internal fun DictionaryWithGraphicDto.toDomain() =
    DictionaryWithGraphic(dictionary.toDomain(), graphics.toDomain())


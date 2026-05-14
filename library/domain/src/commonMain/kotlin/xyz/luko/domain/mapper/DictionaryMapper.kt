package xyz.luko.domain.mapper

import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DecompositionDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.DictionaryWithGraphicDto
import xyz.luko.apicontracts.dto.EtymologyDto
import xyz.luko.apicontracts.dto.EtymologyTypeDto
import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.apicontracts.dto.LevelCountDto
import xyz.luko.apicontracts.dto.PointDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Decomposition
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.DictionaryWithGraphic
import xyz.luko.domain.model.Etymology
import xyz.luko.domain.model.EtymologyType
import xyz.luko.domain.model.Graphic
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.ResponseList
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.model.Stroke

internal fun LevelCountDto.toDomain() =
    LevelCount(
        level = level.toDomain(),
        count = count,
    )

internal fun LevelCount.toDto() =
    LevelCountDto(
        level = level.toDto(),
        count = count,
    )

internal fun SimpleDictionaryDto.toDomain() =
    SimpleDictionary(
        code = code,
        pinyin = pinyin,
        level = level.toDomain(),
    )

internal fun CharacterFrequencyLevelDto.toDomain() = CharacterFrequencyLevel.valueOf(this.name)

internal fun CharacterFrequencyLevel.toDto() = CharacterFrequencyLevelDto.valueOf(this.name)

internal fun GraphicDto.toDomain() =
    Graphic(
        code = code,
        strokes = strokes,
        medians = medians.map { it.toDomain() },
    )

internal fun StrokeDto.toDomain() =
    Stroke(
        points = this.points.map { it.toDomain() },
    )

internal fun PointDto.toDomain() =
    Point(
        x = this.x,
        y = this.y,
    )

internal fun <T, U> ResponseListDto<T>.toDomain(converter: (T) -> U) =
    ResponseList(hasNextPage, data.map { converter(it) })

internal fun DecompositionDto.toDomain() = Decomposition(symbolCode, glyphsCode)

internal fun EtymologyDto.toDomain() =
    Etymology(
        type = type?.toDomain(),
        phonetic = phonetic,
        semantic = semantic,
        hint = hint,
    )

internal fun EtymologyTypeDto.toDomain() = EtymologyType.valueOf(this.name)

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
        matches = matches,
    )

internal fun DictionaryWithGraphicDto.toDomain() =
    DictionaryWithGraphic(dictionary.toDomain(), graphics.toDomain())

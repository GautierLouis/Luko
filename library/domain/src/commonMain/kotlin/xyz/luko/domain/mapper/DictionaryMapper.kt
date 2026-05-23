package xyz.luko.domain.mapper

import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DecompositionDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.PointDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Decomposition
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.ResponseList
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.model.Stroke

internal fun CharacterFrequencyLevelDto.toDomain() = CharacterFrequencyLevel.valueOf(this.name)
internal fun CharacterFrequencyLevel.toDto() = CharacterFrequencyLevelDto.valueOf(this.name)


internal fun <T, U> ResponseListDto<T>.toDomain(converter: (T) -> U) =
    ResponseList(hasNextPage, data.map { converter(it) })


internal fun DictionaryDto.toDomain() = Dictionary(
    code = code,
    pinyin = pinyin,
    decomposition = decomposition.map { it.toDomain() },
    level = level.toDomain(),
    strokes = strokes,
    medians = medians.map { it.toDomain() },
)

internal fun SimpleDictionaryDto.toDomain() =
    SimpleDictionary(
        code = code,
        pinyin = pinyin,
        level = level.toDomain(),
    )

internal fun DecompositionDto.toDomain() = Decomposition(symbolCode, glyphsCode)

internal fun StrokeDto.toDomain() =
    Stroke(
        points = this.points.map { it.toDomain() },
    )

internal fun PointDto.toDomain() = when (this) {
    is PointDto.Curved -> Point.Curved(x, y, cp1x, cp1y, cp2x, cp2y)
    is PointDto.Straight -> Point.Straight(x, y)
}

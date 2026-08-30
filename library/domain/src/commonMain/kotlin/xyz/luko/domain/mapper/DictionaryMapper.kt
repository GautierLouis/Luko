package xyz.luko.domain.mapper

import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.IdeographicCharDto
import xyz.luko.apicontracts.dto.IdeographicNodeDto
import xyz.luko.apicontracts.dto.PointDto
import xyz.luko.apicontracts.dto.PracticeMode
import xyz.luko.apicontracts.dto.RecognitionResult
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.ReviewResponseRequestDto
import xyz.luko.apicontracts.dto.ReviewResultDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.ComparisonDetails
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.IdeographicChar
import xyz.luko.domain.model.IdeographicNode
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.ResponseList
import xyz.luko.domain.model.ReviewResponse
import xyz.luko.domain.model.ReviewResult
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.model.Stroke
import xyz.luko.domain.model.StrokeComparisonResult
import xyz.luko.domain.model.TemporaryResponse

internal fun CharacterFrequencyLevelDto.toDomain() = CharacterFrequencyLevel.valueOf(this.name)
internal fun CharacterFrequencyLevel.toDto() = CharacterFrequencyLevelDto.valueOf(this.name)

internal fun <T, U> ResponseListDto<T>.toDomain(converter: (T) -> U) =
    ResponseList(hasNextPage, data.map { converter(it) })


internal fun DictionaryDto.toDomain() = Dictionary(
    code = code,
    pinyin = pinyin,
    decomposition = decomposition.toDomain(),
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

internal fun StrokeDto.toDomain() = Stroke(points.map { it.toDomain() })

internal fun PointDto.toDomain() = when (this) {
    is PointDto.Curved -> Point.Curved(x, y, cp1x, cp1y, cp2x, cp2y)
    is PointDto.Straight -> Point.Straight(x, y)
}

internal fun Stroke.toDto() = StrokeDto(points.map { it.toDto() })

internal fun Point.toDto() = when (this) {
    is Point.Curved -> PointDto.Curved(x, y, cp1x, cp1y, cp2x, cp2y)
    is Point.Straight -> PointDto.Straight(x, y)
}

internal fun IdeographicNodeDto.toDomain(): IdeographicNode = when (this) {
    is IdeographicNodeDto.Glyph -> IdeographicNode.Glyph(code)
    is IdeographicNodeDto.Operator -> IdeographicNode.Operator(
        op.toDomain(),
        children.map { it.toDomain() })

    IdeographicNodeDto.Unknown -> IdeographicNode.Unknown
}

internal fun IdeographicCharDto.toDomain() = when (this) {
    IdeographicCharDto.LEFT_TO_RIGHT -> IdeographicChar.LEFT_TO_RIGHT
    IdeographicCharDto.ABOVE_TO_BELOW -> IdeographicChar.ABOVE_TO_BELOW
    IdeographicCharDto.LEFT_TO_MIDDLE_AND_RIGHT -> IdeographicChar.LEFT_TO_MIDDLE_AND_RIGHT
    IdeographicCharDto.ABOVE_TO_MIDDLE_AND_BELOW -> IdeographicChar.ABOVE_TO_MIDDLE_AND_BELOW
    IdeographicCharDto.SURROUND -> IdeographicChar.SURROUND
    IdeographicCharDto.SURROUND_FROM_ABOVE -> IdeographicChar.SURROUND_FROM_ABOVE
    IdeographicCharDto.SURROUND_FROM_BELOW -> IdeographicChar.SURROUND_FROM_BELOW
    IdeographicCharDto.SURROUND_FROM_LEFT -> IdeographicChar.SURROUND_FROM_LEFT
    IdeographicCharDto.SURROUND_FROM_UPPER_LEFT -> IdeographicChar.SURROUND_FROM_UPPER_LEFT
    IdeographicCharDto.SURROUND_FROM_UPPER_RIGHT -> IdeographicChar.SURROUND_FROM_UPPER_RIGHT
    IdeographicCharDto.SURROUND_FROM_LOWER_LEFT -> IdeographicChar.SURROUND_FROM_LOWER_LEFT
    IdeographicCharDto.OVERLAID -> IdeographicChar.OVERLAID
}

internal fun List<TemporaryResponse>.toDto() = map { sessionResponse ->
    ReviewResponseRequestDto(
        characterCode = sessionResponse.code,
        strokes = sessionResponse.strokes.map { it.toDto() },
        recognitionResult = RecognitionResult.valueOf(sessionResponse.recognitionResult),
        resetCount = 0,
        durationMs = sessionResponse.strokes.flatMap { it.points }
            .map { it.timestamp }
            .let { timestamps ->
                if (timestamps.isEmpty()) 0L else timestamps.max() - timestamps.min()
            },
        practiceMode = when (sessionResponse.difficultyLevel) {
            DifficultyLevel.EASY -> PracticeMode.EASY
            DifficultyLevel.MEDIUM -> PracticeMode.MEDIUM
            DifficultyLevel.HARD -> PracticeMode.HARD
        }
    )
}

internal fun ReviewResultDto.toDomain(associate: (Int) -> TemporaryResponse): ReviewResult {
    return ReviewResult(
        isStreakUpdated = isStreakUpdated,
        newStreak = newStreak,
        hasLevelUp = hasLevelUp,
        levels = levels,
        sessionResponse = strokeComparison.map { (key, value) ->
            val tmp = associate(key)
            ReviewResponse(
                code = tmp.code,
                pinyin = tmp.pinyin,
                strokes = tmp.strokes,
                references = tmp.references,
                recognitionResult = tmp.recognitionResult,
                difficultyLevel = tmp.difficultyLevel,
                comparisonResult = StrokeComparisonResult(
                    overallAccuracy = value.overallAccuracy,
                    strokeAccuracies = value.strokeAccuracies,
                    orderAccuracy = value.orderAccuracy,
                    details = ComparisonDetails(
                        pathSimilarity = value.details.pathSimilarity,
                        startPointAccuracy = value.details.startPointAccuracy,
                        endPointAccuracy = value.details.endPointAccuracy,
                        directionAccuracy = value.details.directionAccuracy,
                    )
                )
            )
        }
    )
}

package xyz.luko.domain.mapper

import xyz.luko.database.entity.EmbeddedComparisonDetails
import xyz.luko.database.entity.EmbeddedCoordinates
import xyz.luko.database.entity.EmbeddedResponse
import xyz.luko.database.entity.EmbeddedStroke
import xyz.luko.database.entity.EmbeddedStrokeComparisonResult
import xyz.luko.database.entity.SessionEntity
import xyz.luko.domain.model.ComparisonDetails
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Stroke
import xyz.luko.domain.model.StrokeComparisonResult
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

object SessionMapper {
    fun Session.toEntity(): SessionEntity =
        SessionEntity(
            date = date.toString(),
            duration = duration.inWholeMilliseconds,
            difficulty = difficulty.name,
            questionsCount = questionsCount,
            score = score,
        )

    fun SessionEntity.toDto(): Session =
        Session(
            id = id,
            date = Instant.parse(date),
            duration = duration.milliseconds,
            difficulty = DifficultyLevel.valueOf(difficulty),
            questionsCount = questionsCount,
            score = score,
        )

    private fun EmbeddedResponse.toDto(): SessionResponse =
        SessionResponse(this.code, this.statistics.toDto(), this.strokes.map { it.toDto() })

    private fun EmbeddedStroke.toDto(): Stroke = Stroke(this.points.map { it.toDto() })

    private fun EmbeddedCoordinates.toDto(): Point = Point(this.x, this.y)

    private fun EmbeddedStrokeComparisonResult.toDto(): StrokeComparisonResult =
        StrokeComparisonResult(
            overallAccuracy = this.overallAccuracy,
            strokeAccuracies = this.strokeAccuracies,
            orderAccuracy = this.orderAccuracy,
            details = this.details.toDto(),
        )

    private fun EmbeddedComparisonDetails.toDto(): ComparisonDetails =
        ComparisonDetails(
            pathSimilarity = this.pathSimilarity,
            startPointAccuracy = this.startPointAccuracy,
            endPointAccuracy = this.endPointAccuracy,
            directionAccuracy = this.directionAccuracy,
            orderPenalty = this.orderPenalty,
        )

    fun SessionResponse.toEntity(): EmbeddedResponse =
        EmbeddedResponse(
            this.code,
            this.statistics.toEntity(),
            this.strokes.map { it.toEntity() },
        )

    private fun Stroke.toEntity(): EmbeddedStroke =
        EmbeddedStroke(this.points.map { it.toEntity() })

    private fun Point.toEntity(): EmbeddedCoordinates = EmbeddedCoordinates(this.x, this.y)

    private fun StrokeComparisonResult.toEntity(): EmbeddedStrokeComparisonResult =
        EmbeddedStrokeComparisonResult(
            overallAccuracy = this.overallAccuracy,
            strokeAccuracies = this.strokeAccuracies,
            orderAccuracy = this.orderAccuracy,
            details = this.details.toEntity(),
        )

    private fun ComparisonDetails.toEntity(): EmbeddedComparisonDetails =
        EmbeddedComparisonDetails(
            pathSimilarity = this.pathSimilarity,
            startPointAccuracy = this.startPointAccuracy,
            endPointAccuracy = this.endPointAccuracy,
            directionAccuracy = this.directionAccuracy,
            orderPenalty = this.orderPenalty,
        )
}

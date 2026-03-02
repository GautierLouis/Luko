package com.louisgautier.domain.mapper

import com.louisgautier.database.entity.EmbeddedComparisonDetails
import com.louisgautier.database.entity.EmbeddedCoordinates
import com.louisgautier.database.entity.EmbeddedResponse
import com.louisgautier.database.entity.EmbeddedStroke
import com.louisgautier.database.entity.EmbeddedStrokeComparisonResult
import com.louisgautier.database.entity.SessionEntity
import com.louisgautier.domain.model.ComparisonDetails
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.model.Point
import com.louisgautier.domain.model.Response
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.model.Stroke
import com.louisgautier.domain.model.StrokeComparisonResult
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object SessionMapper {

    @OptIn(ExperimentalTime::class)
    fun Session.toEntity(): SessionEntity {
        return SessionEntity(
            date = date.toString(),
            duration = duration.inWholeMilliseconds,
            difficulty = difficulty.name,
            responses = responses.map { it.toEntity() },
            score = score,
        )
    }

    @OptIn(ExperimentalTime::class)
    fun SessionEntity.toDto(): Session {
        return Session(
            id = id,
            date = Instant.parse(date),
            duration = duration.milliseconds,
            difficulty = Difficulty.valueOf(difficulty),
            responses = responses.map { it.toDto() },
            score = score,
        )
    }

    private fun EmbeddedResponse.toDto(): Response {
        return Response(this.code, this.statistics.toDto(), this.strokes.map { it.toDto() })
    }

    private fun EmbeddedStroke.toDto(): Stroke {
        return Stroke(this.points.map { it.toDto() })
    }

    private fun EmbeddedCoordinates.toDto(): Point {
        return Point(this.x, this.y)
    }

    private fun EmbeddedStrokeComparisonResult.toDto(): StrokeComparisonResult {
        return StrokeComparisonResult(
            overallAccuracy = this.overallAccuracy,
            strokeAccuracies = this.strokeAccuracies,
            orderAccuracy = this.orderAccuracy,
            details = this.details.toDto()
        )
    }

    private fun EmbeddedComparisonDetails.toDto(): ComparisonDetails {
        return ComparisonDetails(
            pathSimilarity = this.pathSimilarity,
            startPointAccuracy = this.startPointAccuracy,
            endPointAccuracy = this.endPointAccuracy,
            directionAccuracy = this.directionAccuracy,
            orderPenalty = this.orderPenalty,
        )
    }

    private fun Response.toEntity(): EmbeddedResponse {
        return EmbeddedResponse(
            this.code,
            this.statistics.toEntity(),
            this.strokes.map { it.toEntity() })
    }

    private fun Stroke.toEntity(): EmbeddedStroke {
        return EmbeddedStroke(this.points.map { it.toEntity() })
    }

    private fun Point.toEntity(): EmbeddedCoordinates {
        return EmbeddedCoordinates(this.x, this.y)
    }

    private fun StrokeComparisonResult.toEntity(): EmbeddedStrokeComparisonResult {
        return EmbeddedStrokeComparisonResult(
            overallAccuracy = this.overallAccuracy,
            strokeAccuracies = this.strokeAccuracies,
            orderAccuracy = this.orderAccuracy,
            details = this.details.toEntity()
        )
    }

    private fun ComparisonDetails.toEntity(): EmbeddedComparisonDetails {
        return EmbeddedComparisonDetails(
            pathSimilarity = this.pathSimilarity,
            startPointAccuracy = this.startPointAccuracy,
            endPointAccuracy = this.endPointAccuracy,
            directionAccuracy = this.directionAccuracy,
            orderPenalty = this.orderPenalty,
        )
    }

}
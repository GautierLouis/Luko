package xyz.luko.domain.mapper

import kotlinx.serialization.json.Json
import xyz.luko.database.entity.SessionEntity
import xyz.luko.database.entity.SessionResponseEntity
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
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

    fun SessionResponse.toEntity(): SessionResponseEntity =
        SessionResponseEntity(
            sessionId = 0L,
            code = code,
            overallAccuracy = statistics.overallAccuracy,
            response = Json.encodeToString(this),
        )

    fun SessionResponseEntity.toDto(): SessionResponse =
        Json.decodeFromString(response)
}

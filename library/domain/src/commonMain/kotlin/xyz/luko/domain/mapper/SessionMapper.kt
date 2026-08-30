package xyz.luko.domain.mapper

import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlinx.serialization.json.Json
import xyz.luko.database.entity.SessionEntity
import xyz.luko.database.entity.SessionResponseEntity
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.TemporaryResponse
import xyz.luko.domain.model.TemporarySession
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

object SessionMapper {
    fun TemporarySession.toEntity(): SessionEntity =
        SessionEntity(
            date = date.toString(),
            offset = TimeZone.currentSystemDefault().offsetAt(Clock.System.now()).toString(),
            duration = duration.inWholeMilliseconds,
            difficulty = difficulty.name,
            questionsCount = questionsCount,
            accuracy = 0.0,

        )

    fun SessionEntity.toDto(): Session =
        Session(
            id = id,
            date = Instant.parse(date),
            duration = duration.milliseconds,
            difficulty = DifficultyLevel.valueOf(difficulty),
            questionsCount = questionsCount,
            accuracy = accuracy,
        )

    fun TemporaryResponse.toEntity(): SessionResponseEntity =
        SessionResponseEntity(
            sessionId = 0L,
            code = code,
            overallAccuracy = 0f,
            response = Json.encodeToString(this),
        )

    fun SessionResponseEntity.toDto(): SessionResponse =
        Json.decodeFromString(response)
}

package xyz.luko.domain.usecase

import kotlinx.serialization.json.Json
import xyz.luko.database.dao.CharacterLevelDao
import xyz.luko.database.dao.SyncSessionDao
import xyz.luko.database.entity.CharacterLevelEntity
import xyz.luko.database.entity.SessionResponseEntity
import xyz.luko.domain.model.ReviewResponse
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.TemporarySession
import xyz.luko.domain.repository.SessionRepository

class SyncSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val levelDao: CharacterLevelDao,
    private val syncSessionDao: SyncSessionDao,
) {

    suspend fun execute(
        temporarySession: TemporarySession,
        responses: List<ReviewResponse>,
        newLevels: Map<Int, List<Int>>,
    ): Session {
        val oldResponses = sessionRepository.getUnsyncedResponses(temporarySession.id)
        val responsesByCode = responses.associateBy { it.code }

        val mergedResponses = oldResponses.map { entity ->
            val response = responsesByCode.getValue(entity.code)
            SessionResponseEntity(
                id = entity.id,
                sessionId = temporarySession.id,
                code = response.code,
                response = Json.encodeToString(response),
                overallAccuracy = response.comparisonResult.overallAccuracy
            )
        }

        val averageAccuracy = responses.map { it.comparisonResult.overallAccuracy }.average()

        // flatten level -> codes into code -> level
        val levelByCode: Map<Int, Int> = newLevels.entries
            .flatMap { (level, codes) -> codes.map { code -> code to level } }
            .associate { it }


        val existingLevels = levelDao.getByCodes(levelByCode.keys.toList()).associateBy { it.code }

        val levelsToInsert = mutableListOf<CharacterLevelEntity>()
        val levelsToUpdate = mutableListOf<CharacterLevelEntity>()

        for ((code, newLevel) in levelByCode) {
            val existing = existingLevels[code]
            when {
                existing == null -> levelsToInsert += CharacterLevelEntity(
                    code = code,
                    level = newLevel,
                    hasBeenPromoted = false,
                )

                newLevel > existing.level -> levelsToUpdate += existing.copy(
                    level = newLevel,
                    hasBeenPromoted = false,
                )
            }
        }

        syncSessionDao.sync(
            sessionId = temporarySession.id,
            accuracy = averageAccuracy,
            responses = mergedResponses,
            levelsToInsert = levelsToInsert,
            levelsToUpdate = levelsToUpdate
        )

        return Session(
            id = temporarySession.id,
            date = temporarySession.date,
            duration = temporarySession.duration,
            difficulty = temporarySession.difficulty,
            questionsCount = temporarySession.questionsCount,
            accuracy = averageAccuracy,
        )
    }
}

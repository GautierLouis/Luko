package xyz.luko.server.domain.repo

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.AttemptSignal
import xyz.luko.apicontracts.dto.FsrsState
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.apicontracts.dto.ReviewResponseRequestDto
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.server.data.database.dao.CharacterDao
import xyz.luko.server.data.database.dao.CharacterFsrsDao
import xyz.luko.server.data.database.dao.SessionDao
import xyz.luko.server.data.database.dao.UserDao
import xyz.luko.server.data.database.table.CharacterComplexityTable
import xyz.luko.server.data.database.table.CharacterFsrsStateTable
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.domain.mapper.DomainMapping.toRow
import xyz.luko.server.domain.model.ProgressionRow
import xyz.luko.server.domain.usecase.StreakUpdater
import kotlin.time.Instant

interface ProgressionRepository {

    suspend fun getSignals(
        id: EntityID<Int>,
        responses: List<ReviewResponseRequestDto>
    ): List<AttemptSignal>

    suspend fun saveProgression(
        id: EntityID<Int>,
        attemptRequest: ReviewAttemptRequest,
        progressions: List<ProgressionRow>,
        streakUpdater: StreakUpdater,
    )
}

internal class DefaultProgressionRepository(
    private val characterDao: CharacterDao,
    private val characterFsrsDao: CharacterFsrsDao,
    private val sessionDao: SessionDao,
    private val userRepository: UserDao,
) : ProgressionRepository {

    override suspend fun getSignals(
        id: EntityID<Int>,
        responses: List<ReviewResponseRequestDto>
    ): List<AttemptSignal> {
        val codes = responses.map { it.characterCode }

        val characterContexts: List<CharacterReviewContext> = getCharacterReviewContexts(codes)
        val fsrsStates: Map<Int, FsrsState> = getExistingFsrsStates(id, codes)

        return responses.map { attempt ->
            val context = characterContexts.first { it.code == attempt.characterCode }
            val existingState = fsrsStates[attempt.characterCode]

            AttemptSignal(
                characterCode = attempt.characterCode,
                strokes = attempt.strokes,
                referenceMedians = context.medians,
                recognitionResult = attempt.recognitionResult,
                resetCount = attempt.resetCount,
                durationMs = attempt.durationMs,
                complexityFactor = context.complexityFactor,
                practiceMode = attempt.practiceMode,
                fsrsState = existingState,
            )
        }
    }

    override suspend fun saveProgression(
        id: EntityID<Int>,
        attemptRequest: ReviewAttemptRequest,
        progressions: List<ProgressionRow>,
        streakUpdater: StreakUpdater,
    ) {

        val epochSecond = Instant.parse(attemptRequest.doneAt).epochSeconds

        val responses = attemptRequest.session.responses.map { response ->
            val associatedComparison = progressions
                .first { it.code == response.characterCode }
                .strokeComparison
                .toRow()
            response.toRow(associatedComparison)
        }

        val sessionRow = attemptRequest.session.toRow(id, responses)

        characterFsrsDao.batchedInsertOrUpdate(
            id = id,
            progression = progressions,
            lastReviewedAt = epochSecond,
        )

        sessionDao.insertSession(sessionRow)

        if (streakUpdater.shouldUpdate) {
            userRepository.updateStreak(
                id,
                streakUpdater.newStreak,
                epochSecond
            )
        }
    }

    private suspend fun getExistingFsrsStates(
        id: EntityID<Int>,
        codes: List<Int>
    ): Map<Int, FsrsState> = characterFsrsDao.get(id, codes)
        .associate {
            it[CharacterFsrsStateTable.characterCode] to FsrsState(
                difficulty = it[CharacterFsrsStateTable.difficulty],
                stability = it[CharacterFsrsStateTable.stability],
                level = it[CharacterFsrsStateTable.level],
                lastReviewedAt = it[CharacterFsrsStateTable.lastReviewedAt]
            )
        }

    private suspend fun getCharacterReviewContexts(codes: List<Int>) = characterDao.get(codes)
        .map { row ->
            CharacterReviewContext(
                code = row[DictionaryTable.code],
                medians = Json.decodeFromString(row[DictionaryTable.medians]),
                complexityFactor = row[CharacterComplexityTable.complexityFactor]
            )
        }

    /** Reference data needed to analyze a single character's attempt. */
    private data class CharacterReviewContext(
        val code: Int,
        val medians: List<StrokeDto>,
        val complexityFactor: Double,
    )
}

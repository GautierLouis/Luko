package xyz.luko.server.domain.repo

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.AttemptSignal
import xyz.luko.apicontracts.dto.FsrsState
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.server.data.database.dao.CharacterDao
import xyz.luko.server.data.database.dao.CharacterFsrsDao
import xyz.luko.server.data.database.table.CharacterComplexityTable
import xyz.luko.server.data.database.table.CharacterFsrsStateTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.domain.model.ProgressionRow

interface ProgressionRepository {

    suspend fun getSignals(
        id: EntityID<Int>,
        attemptRequest: ReviewAttemptRequest,
    ): List<AttemptSignal>

    suspend fun saveProgression(
        id: EntityID<Int>,
        progress: List<ProgressionRow>,
        doneAt: LocalDateTime,
    )
}

internal class DefaultProgressionRepository(
    private val characterDao: CharacterDao,
    private val characterFsrsDao: CharacterFsrsDao,
) : ProgressionRepository {

    override suspend fun getSignals(
        id: EntityID<Int>,
        attemptRequest: ReviewAttemptRequest
    ): List<AttemptSignal> {
        val codes = attemptRequest.responses.map { it.characterCode }

        val characterContexts: List<CharacterReviewContext> = getCharacterReviewContexts(codes)
        val fsrsStates: Map<Int, FsrsState> = getExistingFsrsStates(id, codes)

        return attemptRequest.responses.map { attempt ->
            val context = characterContexts.first { it.code == attempt.characterCode }
            val existingState = fsrsStates[attempt.characterCode]

            AttemptSignal(
                characterCode = attempt.characterCode,
                strokes = attempt.strokes,
                rawReferenceMedians = context.rawMedians,
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
        progress: List<ProgressionRow>,
        doneAt: LocalDateTime
    ) {
        characterFsrsDao.batchedInsertOrUpdate(
            id = id,
            progression = progress,
            lastReviewedAt = doneAt.toInstant(TimeZone.UTC).epochSeconds,
        )
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
        .mapIndexed { index, row ->
            CharacterReviewContext(
                code = codes[index],
                rawMedians = Json.decodeFromString(row[GraphicTable.medians]),
                complexityFactor = row[CharacterComplexityTable.complexityFactor]
            )
        }

    /** Reference data needed to analyze a single character's attempt. */
    private data class CharacterReviewContext(
        val code: Int,
        val rawMedians: List<List<List<Float>>>,
        val complexityFactor: Double,
    )
}

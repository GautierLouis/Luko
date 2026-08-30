package xyz.luko.server.domain.repo

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.ResponseSessionDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.data.database.dao.SeedDao
import xyz.luko.server.data.database.dao.SessionDao
import xyz.luko.server.domain.mapper.ResultRowMapping.toSessionResponseRow
import xyz.luko.server.domain.model.SeedRow
import xyz.luko.server.domain.model.SessionResponseRow
import xyz.luko.server.error.missingParameter
import kotlin.random.Random

interface SessionRepository {

    suspend fun createNewSession(
        id: EntityID<Int>,
        params: Destination.Session.New
    ): ResponseSessionDto<DictionaryDto>

    suspend fun replaySession(
        id: EntityID<Int>,
        params: Destination.Session.Replay
    ): ResponseSessionDto<DictionaryDto>

    suspend fun getSessionResponse(sessionId: EntityID<Int>, code: Int): SessionResponseRow?
}

// --- Implementation ---
internal class DefaultSessionRepository(
    private val sessionDao: SessionDao,
    private val seedDao: SeedDao,
    private val buildSessionUseCase: BuildSessionUseCase
) : SessionRepository {

    override suspend fun createNewSession(
        id: EntityID<Int>,
        params: Destination.Session.New
    ): ResponseSessionDto<DictionaryDto> {
        val levels = params.levels ?: throw missingParameter("levels")

        val seed = Random.nextLong()

        seedDao.insertSeed(
            SeedRow(
                seed = seed,
                levels = levels.joinToString(),
                limit = params.limit
            )
        )

        return buildSessionUseCase.execute(id = id, seed = seed)
    }

    override suspend fun replaySession(
        id: EntityID<Int>,
        params: Destination.Session.Replay
    ): ResponseSessionDto<DictionaryDto> {
        return buildSessionUseCase.execute(id = id, seed = params.seed)
    }

    override suspend fun getSessionResponse(
        sessionId: EntityID<Int>,
        code: Int
    ): SessionResponseRow? {
        return sessionDao.getResponse(sessionId, code)?.toSessionResponseRow()
    }
}

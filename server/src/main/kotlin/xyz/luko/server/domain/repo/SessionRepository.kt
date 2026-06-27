package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.ResponseSessionDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.data.database.dao.DictionaryDao
import xyz.luko.server.data.database.dao.SeedDao
import xyz.luko.server.domain.mapper.ResultRowMapping.toDictionary
import xyz.luko.server.domain.mapper.ResultRowMapping.toSeedDto
import xyz.luko.server.domain.model.SeedRow
import xyz.luko.server.error.missingParameter
import kotlin.random.Random

interface SessionRepository {

    suspend fun createNewSession(
        params: Destination.Session.New
    ): ResponseSessionDto<DictionaryDto>

    suspend fun replaySession(
        params: Destination.Session.Replay
    ): ResponseSessionDto<DictionaryDto>
}

// --- Implementation ---

internal class DefaultSessionRepository(
    private val dictionaryDao: DictionaryDao,
    private val seedDao: SeedDao
) : SessionRepository {

    override suspend fun createNewSession(
        params: Destination.Session.New
    ): ResponseSessionDto<DictionaryDto> {

        val levels = params.levels?.map { it.value } ?: throw missingParameter("levels")
        val seed = Random.nextLong()

        seedDao.insertSeed(
            SeedRow(
                seed = seed,
                levels = levels.joinToString(),
                limit = params.limit
            )
        )

        val result = dictionaryDao.createSession(
            levels = levels,
            limit = params.limit,
            seed = seed
        )

        return ResponseSessionDto(seed, result.map { it.toDictionary() })
    }

    override suspend fun replaySession(
        params: Destination.Session.Replay
    ): ResponseSessionDto<DictionaryDto> {
        val seedRow = seedDao.getSeed(params.seed)
            ?.toSeedDto()
            ?: throw IllegalArgumentException("seed not found")

        val result = dictionaryDao.createSession(
            levels = seedRow.levels.split(",").map { it.toInt() },
            limit = seedRow.limit,
            seed = seedRow.seed
        )
        return ResponseSessionDto(params.seed, result.map { it.toDictionary() })
    }
}


package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.data.database.dao.DictionaryDao
import xyz.luko.server.domain.mapper.ResultRowMapping.toDictionary
import xyz.luko.server.error.missingParameter

interface SessionRepository {

    suspend fun generateSession(
        params: Destination.GenerateSession
    ): List<DictionaryDto>
}

// --- Implementation ---

internal class DefaultSessionRepository(
    private val source: DictionaryDao,
) : SessionRepository {

    override suspend fun generateSession(
        params: Destination.GenerateSession
    ): List<DictionaryDto> {

        return source.generateSession(
            levels = params.levels?.map { it.value } ?: throw missingParameter("levels"),
            limit = params.limit
        ).map { it.toDictionary() }
    }
}


package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.DictionaryWithGraphicDto
import xyz.luko.apicontracts.routing.EndPoint

interface SessionRepository {

    suspend fun generateSession(
        params: EndPoint.GenerateSession
    ): List<DictionaryWithGraphicDto>
}

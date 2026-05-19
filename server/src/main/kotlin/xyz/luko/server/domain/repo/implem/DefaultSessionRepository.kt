package xyz.luko.server.domain.repo.implem

import xyz.luko.apicontracts.dto.DictionaryWithGraphicDto
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.server.database.source.DictionaryDataSource
import xyz.luko.server.domain.mapper.toDictionaryWithGraphic
import xyz.luko.server.domain.mapper.toEntity
import xyz.luko.server.domain.repo.SessionRepository
import xyz.luko.server.error.missingParameter

class DefaultSessionRepository(
    private val source: DictionaryDataSource,
) : SessionRepository {

    override suspend fun generateSession(
        params: EndPoint.GenerateSession
    ): List<DictionaryWithGraphicDto> {

        return source.getRandomCharacters(
            levels = params.levels?.map { it.toEntity() } ?: throw missingParameter("levels"),
            limit = params.limit
        ).map { it.toDictionaryWithGraphic() }
    }
}

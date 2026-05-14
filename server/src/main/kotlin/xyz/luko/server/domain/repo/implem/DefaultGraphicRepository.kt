package xyz.luko.server.domain.repo.implem

import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.server.database.source.GraphicDataSource
import xyz.luko.server.domain.mapper.toEntity
import xyz.luko.server.domain.mapper.toGraphic
import xyz.luko.server.domain.repo.GraphicRepository

class DefaultGraphicRepository(
    private val source: GraphicDataSource
) : GraphicRepository {

    override suspend fun exist(): Boolean {
        return source.exist()
    }

    override suspend fun get(
        params: EndPoint.Characters.ByName
    ): GraphicDto? {
        return source.get(params.code)?.toGraphic()
    }

    override suspend fun batchCreate(graphic: List<GraphicDto>) {
        source.batchCreate(graphic.map { it.toEntity() })
    }
}
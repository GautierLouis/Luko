package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.apicontracts.routing.EndPoint

interface GraphicRepository {
    suspend fun exist(): Boolean

    suspend fun get(params: EndPoint.Characters.ByName): GraphicDto?
    suspend fun batchCreate(graphic: List<GraphicDto>)
}
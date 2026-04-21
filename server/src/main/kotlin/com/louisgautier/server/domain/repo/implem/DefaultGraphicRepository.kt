package com.louisgautier.server.domain.repo.implem

import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.database.source.GraphicDataSource
import com.louisgautier.server.domain.mapper.toEntity
import com.louisgautier.server.domain.mapper.toGraphic
import com.louisgautier.server.domain.repo.GraphicRepository

class DefaultGraphicRepository(
    private val source: GraphicDataSource
) : GraphicRepository {

    override suspend fun get(
        params: EndPoint.Characters.ByName
    ): GraphicDto? {
        return source.get(params.code)?.toGraphic()
    }

    override suspend fun batchCreate(graphic: List<GraphicDto>) {
        source.batchCreate(graphic.map { it.toEntity() })
    }
}
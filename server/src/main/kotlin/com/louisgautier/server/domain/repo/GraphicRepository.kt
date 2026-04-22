package com.louisgautier.server.domain.repo

import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.apicontracts.routing.EndPoint

interface GraphicRepository {
    suspend fun exist(): Boolean

    suspend fun get(params: EndPoint.Characters.ByName): GraphicDto?
    suspend fun batchCreate(graphic: List<GraphicDto>)
}
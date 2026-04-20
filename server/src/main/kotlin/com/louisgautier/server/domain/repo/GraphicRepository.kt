package com.louisgautier.server.domain.repo

import com.louisgautier.apicontracts.dto.GraphicDto

interface GraphicRepository {
    suspend fun get(code: Int): GraphicDto?
    suspend fun batchCreate(graphic: List<GraphicDto>)
}
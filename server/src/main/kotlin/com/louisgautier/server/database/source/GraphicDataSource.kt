package com.louisgautier.server.database.source

import com.louisgautier.server.domain.model.GraphicEntity
import org.jetbrains.exposed.sql.ResultRow

interface GraphicDataSource {
    suspend fun get(code: Int): ResultRow?
    suspend fun batchCreate(data: List<GraphicEntity>)
}
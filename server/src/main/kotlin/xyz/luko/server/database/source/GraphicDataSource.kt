package xyz.luko.server.database.source

import org.jetbrains.exposed.sql.ResultRow
import xyz.luko.server.domain.model.GraphicEntity

interface GraphicDataSource {
    suspend fun exist(): Boolean
    suspend fun get(code: Int): ResultRow?
    suspend fun batchCreate(data: List<GraphicEntity>)
}
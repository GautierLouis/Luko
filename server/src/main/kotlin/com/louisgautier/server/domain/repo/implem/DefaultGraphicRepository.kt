package com.louisgautier.server.domain.repo.implem

import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.server.database.entity.GraphicTable
import com.louisgautier.server.domain.mapper.toGraphic
import com.louisgautier.server.domain.repo.GraphicRepository
import com.louisgautier.server.domain.suspendTransaction
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class DefaultGraphicRepository : GraphicRepository {

    override suspend fun get(code: Int): GraphicDto? = suspendTransaction {
        GraphicTable.selectAll().where { GraphicTable.code eq code }.limit(1)
            .map { it.toGraphic() }.firstOrNull()
    }

    override suspend fun batchCreate(graphic: List<GraphicDto>) = suspendTransaction {
        GraphicTable.batchInsert(
            graphic,
            ignore = true,
            shouldReturnGeneratedValues = false
        ) {
            this[GraphicTable.code] = it.code
            this[GraphicTable.strokes] = it.strokes.joinToString()
            this[GraphicTable.medians] = Json.encodeToString(it.medians)
        }
        return@suspendTransaction
    }
}
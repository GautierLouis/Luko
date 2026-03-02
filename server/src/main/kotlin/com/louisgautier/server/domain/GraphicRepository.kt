package com.louisgautier.server.domain

import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.server.database.entity.GraphicTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class GraphicRepository {

    suspend fun get(code: Int): GraphicDto? = suspendTransaction {
        GraphicTable.selectAll().where { GraphicTable.code eq code }.limit(1)
            .map { it.toGraphic() }.firstOrNull()
    }

    suspend fun batchCreate(graphic: List<GraphicDto>) = suspendTransaction {
        GraphicTable.batchInsert(
            graphic,
            ignore = true,
            shouldReturnGeneratedValues = false
        ) {
            this[GraphicTable.code] = it.code
            this[GraphicTable.strokes] = it.strokes.joinToString()
            this[GraphicTable.medians] = Json.encodeToString(it.medians)
        }
    }
}
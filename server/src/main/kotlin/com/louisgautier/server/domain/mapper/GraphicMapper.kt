package com.louisgautier.server.domain.mapper

import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.server.database.entity.GraphicTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toGraphic() = GraphicDto(
    code = this[GraphicTable.code],
    strokes = this[GraphicTable.strokes].split(","),
    medians = Json.decodeFromString(this[GraphicTable.medians]),
)
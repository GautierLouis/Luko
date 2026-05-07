package com.louisgautier.server.domain.mapper

import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.server.database.entity.GraphicTable
import com.louisgautier.server.domain.model.GraphicEntity
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toGraphic() = GraphicDto(
    code = this[GraphicTable.code],
    strokes = this[GraphicTable.strokes].split(","),
    medians = Json.decodeFromString(this[GraphicTable.medians]),
)

fun GraphicDto.toEntity() = GraphicEntity(
    code = this.code,
    strokes = this.strokes.joinToString(),
    medians = Json.encodeToString(this.medians)
)
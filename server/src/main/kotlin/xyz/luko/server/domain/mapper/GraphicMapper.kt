package xyz.luko.server.domain.mapper

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.server.database.entity.GraphicTable
import xyz.luko.server.domain.model.GraphicEntity

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
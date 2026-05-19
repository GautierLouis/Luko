package xyz.luko.server.domain.mapper

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.apicontracts.dto.PointDto
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.server.database.table.GraphicTable
import xyz.luko.server.domain.mapper.parsing.GraphicParser
import xyz.luko.server.domain.model.GraphicEntity

fun ResultRow.toGraphic() = GraphicDto(
    code = this[GraphicTable.code],
    strokes = this[GraphicTable.strokes].split(","),
    medians = Json.decodeFromString(this[GraphicTable.medians]),
    smootherMedians = Json.decodeFromString(this[GraphicTable.smootherMedians])
)

fun GraphicDto.toEntity() = GraphicEntity(
    code = this.code,
    strokes = this.strokes.joinToString(),
    medians = Json.encodeToString(this.medians),
    smootherMedians = Json.encodeToString(this.smootherMedians)
)

fun GraphicParser.toDomain(smootherMedian: List<String>) = GraphicDto(
    code = character.toString().codePointAt(0),
    strokes = strokes,
    medians = medians.toStroke(),
    smootherMedians = smootherMedian
)

fun List<List<List<Float>>>.toStroke() = map { s ->
    StrokeDto(s.map { p ->
        PointDto(p[0], p[1])
    })
}

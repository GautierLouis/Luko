package xyz.luko.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object CharacterComplexityTable : IntIdTable("character_complexity") {
    val code = integer("code").uniqueIndex()
    val strokeCount = integer("stroke_count")
    val pathLength = double("path_length")
    val componentCount = integer("component_count")
    val complexityFactor = double("complexity_factor")
    val computedAt = long("computed_at")
}

package xyz.luko.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object GraphicTable : IntIdTable("graphic") {
    val code = integer("code").uniqueIndex()
    val strokes = text("strokes")
    val medians = text("medians")
}

package xyz.luko.server.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object GraphicTable : IntIdTable("graphic") {
    val code = integer("code").uniqueIndex()
    val strokes = text("strokes")
    val medians = text("medians")
    val smootherMedians = text("smoother_medians")
}

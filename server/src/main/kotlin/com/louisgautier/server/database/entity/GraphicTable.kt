package com.louisgautier.server.database.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object GraphicTable : IntIdTable("graphic") {
    val code = integer("code").uniqueIndex()
    val strokes = text("strokes")
    val medians = text("medians")
}
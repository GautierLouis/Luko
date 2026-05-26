package xyz.luko.server.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object SeedTable : IntIdTable("session_seed") {
    val seed = long("seed").uniqueIndex()
    val levels = varchar("levels", 20)
    val limit = integer("limit")
}

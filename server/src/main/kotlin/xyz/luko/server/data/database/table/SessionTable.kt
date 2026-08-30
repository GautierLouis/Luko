package xyz.luko.server.data.database.table

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object SessionTable : IntIdTable("session") {
    val userId = reference("user_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val date = varchar("date", 64) // Instant, stored as ISO-8601 string
    val offset = varchar("offset", 16)
    val duration = long("duration") // Duration
    val difficulty = varchar("difficulty", 32)
    val questionsCount = integer("questions_count")
    val accuracy = double("accuracy")

    init {
        index(isUnique = false, userId)
    }
}


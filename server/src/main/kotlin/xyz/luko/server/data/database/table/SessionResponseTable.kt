package xyz.luko.server.data.database.table

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object SessionResponseTable : IntIdTable("session_response") {
    val sessionId = reference("session_id", SessionTable, onDelete = ReferenceOption.CASCADE)
    val code = integer("code")
    val overallAccuracy = float("overall_accuracy")
    val response = text("response")

    init {
        index(isUnique = false, sessionId)
        index(isUnique = false, code)
    }
}

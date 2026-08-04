package xyz.luko.server.data.database.table

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object CharacterFsrsStateTable : IntIdTable("character_fsrs_state") {
    val userId = reference("user_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val characterCode = integer("character_code")
    val difficulty = double("difficulty")
    val stability = double("stability")
    val lastReviewedAt = long("last_reviewed_at")
    val nextReviewDueAt = long("next_review_due_at") // lastReviewedAt + intervalDays, precomputed for queue queries
    val updatedAt = long("updated_at")


    init {
        uniqueIndex(userId, characterCode)
    }
}

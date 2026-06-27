package xyz.luko.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object UserTable : IntIdTable("user") {
    val firebaseUid = varchar("firebase_uid", 128).uniqueIndex()
    val fcmToken = varchar("fcm_token", 255).nullable()
    val platform = varchar("platform", 64)
    val isAnonymous = bool("is_anonymous").default(true)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

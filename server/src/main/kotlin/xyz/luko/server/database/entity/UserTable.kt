package xyz.luko.server.database.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("user") {
    val installationId = varchar("installation_id", 255).uniqueIndex()
    val supabaseUserId =
        varchar("supabase_user_id", 36).uniqueIndex()
    val supabaseRefreshToken = varchar("supabase_refresh_token", 255)
    val fcmToken = varchar("fcm_token", 255)
    val createdAt = varchar("created_at", 50)
    val updatedAt = varchar("updated_at", 50)
}
package xyz.luko.server.domain.mapper

import org.jetbrains.exposed.sql.ResultRow
import xyz.luko.server.database.entity.UserTable
import xyz.luko.server.domain.model.UserEntity

fun ResultRow.toUserEntity() = UserEntity(
    installationId = this[UserTable.installationId],
    supabaseUserId = this[UserTable.supabaseUserId],
    supabaseRefreshToken = this[UserTable.supabaseRefreshToken],
    fcmToken = this[UserTable.fcmToken],
    createdAt = this[UserTable.createdAt],
    updatedAt = this[UserTable.updatedAt]
)
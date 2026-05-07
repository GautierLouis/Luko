package com.louisgautier.server.domain.mapper

import com.louisgautier.server.database.entity.UserTable
import com.louisgautier.server.domain.model.UserEntity
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUserEntity() = UserEntity(
    installationId = this[UserTable.installationId],
    supabaseUserId = this[UserTable.supabaseUserId],
    supabaseRefreshToken = this[UserTable.supabaseRefreshToken],
    fcmToken = this[UserTable.fcmToken],
    createdAt = this[UserTable.createdAt],
    updatedAt = this[UserTable.updatedAt]
)
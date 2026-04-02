package com.louisgautier.server.domain

import com.louisgautier.server.database.entity.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import kotlin.time.Clock

class UserRepository {

    private fun ResultRow.toUserEntity() = UserEntity(
        installationId = this[UserTable.installationId],
        supabaseUserId = this[UserTable.supabaseUserId],
        supabaseRefreshToken = this[UserTable.supabaseRefreshToken],
        fcmToken = this[UserTable.fcmToken],
        createdAt = this[UserTable.createdAt],
        updatedAt = this[UserTable.updatedAt]
    )

    suspend fun getUserByInstallationId(id: String): UserEntity? = suspendTransaction {
        UserTable.selectAll()
            .where { UserTable.installationId eq id }
            .firstOrNull()?.toUserEntity()
    }

    suspend fun getUserBySupabaseId(id: String): UserEntity? = suspendTransaction {
        UserTable.selectAll()
            .where { UserTable.supabaseUserId eq id }
            .firstOrNull()?.toUserEntity()
    }

    suspend fun create(
        installationId: String,
        supabaseUserId: String,
        supabaseUserRefreshToken: String,
        fcmToken: String,
    ) = suspendTransaction {
        UserTable.insert {
            it[UserTable.installationId] = installationId
            it[UserTable.supabaseUserId] = supabaseUserId
            it[UserTable.supabaseRefreshToken] = supabaseUserRefreshToken
            it[UserTable.fcmToken] = fcmToken
            it[createdAt] = Clock.System.now().toString()
            it[updatedAt] = Clock.System.now().toString()
        }
    }

    suspend fun updateFcm(
        installationId: String,
        fcmToken: String,
    ) = suspendTransaction {
        UserTable.update({ UserTable.installationId eq installationId }) {
            it[UserTable.fcmToken] = fcmToken
        }
    }

    suspend fun updateSession(
        supabaseUserId: String,
        refreshToken: String,
    ) = suspendTransaction {
        UserTable.update({ UserTable.supabaseUserId eq supabaseUserId }) {
            it[UserTable.supabaseRefreshToken] = refreshToken
        }
    }
}
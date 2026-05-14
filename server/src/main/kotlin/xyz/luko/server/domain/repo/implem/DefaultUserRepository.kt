package xyz.luko.server.domain.repo.implem

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import xyz.luko.server.database.entity.UserTable
import xyz.luko.server.database.suspendTransaction
import xyz.luko.server.domain.mapper.toUserEntity
import xyz.luko.server.domain.model.UserEntity
import xyz.luko.server.domain.repo.UserRepository
import kotlin.time.Clock

class DefaultUserRepository : UserRepository {

    override suspend fun getUserByInstallationId(id: String): UserEntity? = suspendTransaction {
        UserTable.selectAll()
            .where { UserTable.installationId eq id }
            .firstOrNull()?.toUserEntity()
    }

    override suspend fun getUserBySupabaseId(id: String): UserEntity? = suspendTransaction {
        UserTable.selectAll()
            .where { UserTable.supabaseUserId eq id }
            .firstOrNull()?.toUserEntity()
    }

    override suspend fun create(
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
        return@suspendTransaction
    }

    override suspend fun updateFcm(
        installationId: String,
        fcmToken: String,
    ) = suspendTransaction {
        UserTable.update({ UserTable.installationId eq installationId }) {
            it[UserTable.fcmToken] = fcmToken
        }
        return@suspendTransaction
    }

    override suspend fun updateSession(
        supabaseUserId: String,
        refreshToken: String,
    ) = suspendTransaction {
        UserTable.update({ UserTable.supabaseUserId eq supabaseUserId }) {
            it[UserTable.supabaseRefreshToken] = refreshToken
        }
        return@suspendTransaction
    }
}
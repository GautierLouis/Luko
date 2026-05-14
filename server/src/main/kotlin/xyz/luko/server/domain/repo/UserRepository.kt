package xyz.luko.server.domain.repo

import xyz.luko.server.domain.model.UserEntity

interface UserRepository {
    suspend fun getUserByInstallationId(id: String): UserEntity?
    suspend fun getUserBySupabaseId(id: String): UserEntity?
    suspend fun create(
        installationId: String,
        supabaseUserId: String,
        supabaseUserRefreshToken: String,
        fcmToken: String,
    )

    suspend fun updateFcm(
        installationId: String,
        fcmToken: String,
    )

    suspend fun updateSession(
        supabaseUserId: String,
        refreshToken: String,
    )
}
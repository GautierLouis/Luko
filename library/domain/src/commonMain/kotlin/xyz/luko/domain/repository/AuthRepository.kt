package xyz.luko.domain.repository

interface AuthRepository {
    suspend fun registerAnonymously(): Result<Unit>

    suspend fun onNewFcmToken(token: String): Result<Unit>
}

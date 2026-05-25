package xyz.luko.auth

interface AuthRepository {
    suspend fun registerAnonymously(): Result<Unit>

    suspend fun onNewFcmToken(token: String): Result<Unit>
}

package xyz.luko.auth

interface AuthRepository {
    suspend fun registerAnonymously(): Result<Unit>

    suspend fun registerNewToken(token: String): Result<Unit>
}

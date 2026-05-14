package xyz.luko.firebase.notification

interface FcmAccessor {
    suspend fun registerNewToken(token: String): Result<Unit>
}

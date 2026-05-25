package xyz.luko.firebase.notification

interface FcmAccessor {
    suspend fun onNewFcmToken(token: String): Result<Unit>
}

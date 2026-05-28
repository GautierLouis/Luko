package xyz.luko.firebase.notification

interface FcmProvider {
    suspend fun onNewFcmToken(token: String): Result<Unit>
}

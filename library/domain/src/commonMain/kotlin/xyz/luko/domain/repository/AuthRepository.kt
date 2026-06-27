package xyz.luko.domain.repository

interface AuthRepository {
    suspend fun getUserId(): String?

    suspend fun registerAnonymously(
        id: String,
        fcmToken: String?
    )

    suspend fun updateFcm(fcmToken: String?)

    suspend fun onNewFcmToken(token: String): Result<Unit>
}

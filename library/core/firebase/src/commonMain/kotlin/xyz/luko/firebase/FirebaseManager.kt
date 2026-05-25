package xyz.luko.firebase

import xyz.luko.tracking.TrackingEvent

interface FirebaseManager {
    fun initialize()
    suspend fun registerAnonymously(): Result<String>
    suspend fun getIdToken(forceRefresh: Boolean = false): Result<String>
    suspend fun getFCMToken(): Result<String>
    fun logEvent(event: TrackingEvent)
    fun setUserId(userId: String)
    fun setUserProperty(name: String, value: String)
    fun fetchRemoteConfig()
}

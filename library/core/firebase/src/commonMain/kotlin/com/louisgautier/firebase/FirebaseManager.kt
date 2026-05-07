package com.louisgautier.firebase

import com.louisgautier.tracking.TrackingEvent

interface FirebaseManager {
    fun initialize()

    suspend fun getFCMToken(): String

    suspend fun getInstallationId(): String

    fun logEvent(event: TrackingEvent)

    fun setUserId(userId: String)

    fun setUserProperty(
        name: String,
        value: String,
    )

    fun fetchRemoteConfig()
}

const val DEFAULT_MIN_FETCH_INTERVAL: Long = 3600L // 1 hour default

package xyz.luko.firebase

import xyz.luko.tracking.TrackingEvent

class DesktopFirebaseManager : FirebaseManager {
    override fun initialize() {
    }

    override suspend fun getFCMToken(): String = ""

    override suspend fun getInstallationId(): String = ""

    override fun logEvent(event: TrackingEvent) {
    }

    override fun setUserId(userId: String) {
    }

    override fun setUserProperty(
        name: String,
        value: String,
    ) {
    }

    override fun fetchRemoteConfig() {
    }
}

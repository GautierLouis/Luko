package xyz.luko.firebase

import xyz.luko.tracking.TrackingEvent

class DesktopFirebaseManager : FirebaseManager {
    override fun initialize() {

    }

    override suspend fun getFCMToken(): String {
        return ""
    }

    override suspend fun getInstallationId(): String {
        return ""
    }

    override fun logEvent(event: TrackingEvent) {

    }

    override fun setUserId(userId: String) {

    }

    override fun setUserProperty(name: String, value: String) {

    }

    override fun fetchRemoteConfig() {

    }
}
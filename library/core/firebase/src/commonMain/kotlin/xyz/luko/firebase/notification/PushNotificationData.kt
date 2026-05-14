package xyz.luko.firebase.notification

data class PushNotificationData(
    val title: String?,
    val body: String?,
    val data: Map<String, String> = emptyMap(),
)

package com.louisgautier.firebase.notification

interface PushNotificationManager {
    /** Push a notification to notification (from service). */
    fun sendNotification(data: PushNotificationData)
}
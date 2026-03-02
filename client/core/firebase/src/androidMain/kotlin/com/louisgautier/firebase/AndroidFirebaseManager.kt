package com.louisgautier.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.louisgautier.firebase.event.TrackingEvent
import com.louisgautier.firebase.remoteconfig.FeatureFlagKey
import com.louisgautier.logger.AppLogger
import kotlinx.coroutines.tasks.await

actual class FirebaseManager(
    private val context: Context,
    private val remoteConfigManager: RemoteConfigManager
) {

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var messaging: FirebaseMessaging
    private lateinit var auth: FirebaseAuth


    actual fun initialize() {
        FirebaseApp.initializeApp(context)

        auth = Firebase.auth
        analytics = Firebase.analytics
        messaging = Firebase.messaging

        auth.signInAnonymously()

        remoteConfig = Firebase.remoteConfig.apply {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
            setConfigSettingsAsync(configSettings)
            // setDefaultsAsync(R.xml.remote_config_defaults)
        }

        messaging = Firebase.messaging

        setupMessaging()
        fetchRemoteConfig()
    }

    private fun setupMessaging() {
        messaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                println("FCM Token: $token")
                // Send token to your server
            }
        }
    }

    actual fun logEvent(
        event: TrackingEvent
    ) {
        val bundle = Bundle().apply {
            event.params.forEach {
                when (it.value) {
                    is String -> putString(it.key, it.value as String)
                    is Int -> putInt(it.key, it.value as Int)
                    is Double -> putDouble(it.key, it.value as Double)
                    is Boolean -> putBoolean(it.key, it.value as Boolean)
                    else -> putString(it.key, it.value.toString())
                }
            }
        }

        analytics.logEvent(event.key, bundle)
    }

    actual fun setUserId(userId: String) {
        analytics.setUserId(userId)
    }

    actual fun setUserProperty(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }

    actual fun fetchRemoteConfig() {
        remoteConfig.fetchAndActivate().addOnSuccessListener { success ->
            AppLogger.d(
                tag = "FirebaseManager",
                message = "Remote config fetched and activated with success: $success"
            )

            if (success) {
                val flags = RemoteConfigFlags(
                    isDictionaryEnabled = remoteConfig.getBoolean(FeatureFlagKey.ENABLE_DICTIONARY)
                )
                remoteConfigManager.register(flags)
            }
        }
    }
}
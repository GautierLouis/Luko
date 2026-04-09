package com.louisgautier.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.louisgautier.firebase.remoteconfig.FeatureFlagKey
import com.louisgautier.logger.AppLogger
import com.louisgautier.tracking.TrackingEvent
import kotlinx.coroutines.tasks.await

class AndroidFirebaseManager(
    private val context: Context,
    private val remoteConfigManager: RemoteConfigManager
) : FirebaseManager {

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var messaging: FirebaseMessaging
    private lateinit var auth: FirebaseAuth


    override fun initialize() {
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
        }

        messaging = Firebase.messaging

        fetchRemoteConfig()
    }

    override suspend fun getFCMToken(): String {
        return messaging.token.await()
    }

    override suspend fun getInstallationId(): String {
        return FirebaseInstallations.getInstance().id.await()
    }

    override fun logEvent(
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

    override fun setUserId(userId: String) {
        analytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }

    override fun fetchRemoteConfig() {
        val default = mapOf(
            FeatureFlagKey.ENABLE_DICTIONARY to false,
            FeatureFlagKey.ENABLE_BOTTOM_NAV to false,
        )
        remoteConfig
            .setDefaultsAsync(default)
            .continueWithTask { remoteConfig.fetchAndActivate() }
            .addOnSuccessListener {
                AppLogger.d(
                    tag = "FirebaseManager",
                    message = "Remote config fetched"
                )
            }
            .addOnFailureListener {
                AppLogger.d(
                    tag = "FirebaseManager",
                    message = "Remote config fetch failed: ${it.message}"
                )
            }
            .addOnCompleteListener {
                val flags = RemoteConfigFlags(
                    isDictionaryEnabled = remoteConfig.getBoolean(FeatureFlagKey.ENABLE_DICTIONARY),
                    isBottomBarEnabled = remoteConfig.getBoolean(FeatureFlagKey.ENABLE_BOTTOM_NAV)
                )
                remoteConfigManager.register(flags)
            }
    }
}
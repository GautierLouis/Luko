package xyz.luko.firebase

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.UIKit.UIApplication
import platform.UIKit.registerForRemoteNotifications
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter
import swiftPMImport.Luko.library.core.library.core.firebase.FIRAnalytics
import swiftPMImport.Luko.library.core.library.core.firebase.FIRApp
import swiftPMImport.Luko.library.core.library.core.firebase.FIRInstallations
import swiftPMImport.Luko.library.core.library.core.firebase.FIRMessaging
import swiftPMImport.Luko.library.core.library.core.firebase.FIRRemoteConfig
import swiftPMImport.Luko.library.core.library.core.firebase.FIRRemoteConfigFetchAndActivateStatus
import xyz.luko.logger.AppLogger
import xyz.luko.tracking.TrackingEvent
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalForeignApi::class)
class AppleFirebaseManager : FirebaseManager {
    private val apnsTokenFlow = MutableStateFlow<NSData?>(null)

    override fun initialize() {
        FIRApp.configure()
        requestNotificationPermissionAndRegister()
    }

    fun onApnsTokenReceived(token: NSData) {
        FIRMessaging.messaging().APNSToken = token
        apnsTokenFlow.value = token
    }

    private fun requestNotificationPermissionAndRegister() {
        UNUserNotificationCenter
            .currentNotificationCenter()
            .requestAuthorizationWithOptions(
                UNAuthorizationOptionAlert or
                        UNAuthorizationOptionBadge or
                        UNAuthorizationOptionSound,
            ) { granted, error ->
                if (error != null) {
                    println("Notification permission error: ${error.localizedDescription}")
                    return@requestAuthorizationWithOptions
                }
                if (granted) {
                    // Must be called on main thread
                    GlobalScope.launch(Dispatchers.Main) {
                        UIApplication.sharedApplication
                            .registerForRemoteNotifications()
                    }
                }
            }
    }

    override suspend fun getFCMToken(): String {
        apnsTokenFlow.first { it != null }

        return suspendCancellableCoroutine { continuation ->
            FIRMessaging.messaging().tokenWithCompletion { token, error ->
                when {
                    error != null -> {
                        println("error is not null")
                        continuation.resumeWithException(
                            Exception(error.localizedDescription),
                        )
                    }

                    token != null -> {
                        println("has token")
                        continuation.resume(token) { cause, _, _ ->
                            continuation.resumeWithException(cause)
                        }
                    }

                    else -> {
                        println("error else")
                        continuation.resumeWithException(
                            Exception("FCM token is null"),
                        )
                    }
                }
            }
        }
    }

    override suspend fun getInstallationId(): String =
        suspendCancellableCoroutine { continuation ->
            FIRInstallations.installations().installationIDWithCompletion { id, error ->
                when {
                    error != null ->
                        continuation.resumeWithException(
                            Exception(error.localizedDescription),
                        )

                    id != null ->
                        continuation.resume(id) { cause, _, _ ->
                            continuation.resumeWithException(cause)
                        }

                    else ->
                        continuation.resumeWithException(
                            Exception("Installation ID is null"),
                        )
                }
            }
        }

    override fun logEvent(event: TrackingEvent) {
        FIRAnalytics.logEventWithName(
            name = event.key,
            parameters = event.params.toMap(),
        )
    }

    override fun setUserId(userId: String) {
        FIRAnalytics.setUserID(userId)
    }

    override fun setUserProperty(
        name: String,
        value: String,
    ) {
        FIRAnalytics.setUserPropertyString(value, forName = name)
    }

    override fun fetchRemoteConfig() {
        val remoteConfig = FIRRemoteConfig.remoteConfig()
        remoteConfig.fetchAndActivateWithCompletionHandler { status, error ->
            if (error != null) {
                AppLogger.e(
                    tag = "RemoteConfig",
                    message = "fetched error: ${error.localizedDescription}",
                )
                return@fetchAndActivateWithCompletionHandler
            }
            when (status) {
                FIRRemoteConfigFetchAndActivateStatus
                    .FIRRemoteConfigFetchAndActivateStatusSuccessFetchedFromRemote,
                    ->
                    AppLogger.i(tag = "RemoteConfig", message = "fetched and activated from remote")

                FIRRemoteConfigFetchAndActivateStatus
                    .FIRRemoteConfigFetchAndActivateStatusSuccessUsingPreFetchedData,
                    ->
                    AppLogger.i(tag = "RemoteConfig", message = "activated using pre-fetched data")

                else ->
                    AppLogger.i(tag = "RemoteConfig", message = "fetched failed or no change")
            }
        }
    }
}

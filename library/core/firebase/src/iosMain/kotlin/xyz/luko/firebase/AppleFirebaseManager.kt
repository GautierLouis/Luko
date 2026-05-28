package xyz.luko.firebase

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import platform.Foundation.NSData
import platform.UIKit.UIApplication
import platform.UIKit.registerForRemoteNotifications
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter
import swiftPMImport.Luko.library.core.library.core.firebase.FIRAnalytics
import swiftPMImport.Luko.library.core.library.core.firebase.FIRApp
import swiftPMImport.Luko.library.core.library.core.firebase.FIRAuth
import swiftPMImport.Luko.library.core.library.core.firebase.FIRMessaging
import swiftPMImport.Luko.library.core.library.core.firebase.FIRRemoteConfig
import swiftPMImport.Luko.library.core.library.core.firebase.FIRRemoteConfigFetchAndActivateStatus
import xyz.luko.logger.AppLogger
import xyz.luko.tracking.TrackingEvent
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalForeignApi::class)
class AppleFirebaseManager : FirebaseManager {
    private val apnsTokenFlow = MutableStateFlow<NSData?>(null)

    override fun initialize() {
        AppLogger.d(message = "Initializing Firebase")
        FIRApp.configure()
        requestNotificationPermissionAndRegister()
    }

    override suspend fun registerAnonymously(): Result<String> {
        val user = suspendCancellableCoroutine { continuation ->
            FIRAuth.auth().signInAnonymouslyWithCompletion { result, error ->
                when {
                    error != null -> continuation.resumeWithException(
                        Exception(error.localizedDescription)
                    )

                    result?.user == null -> continuation.resumeWithException(
                        IllegalStateException("Firebase user null after signIn")
                    )

                    else -> continuation.resume(result.user)
                }
            }
        }
        return Result.success(user.uid)
    }

    override suspend fun getIdToken(forceRefresh: Boolean): Result<String> {
        val token = suspendCancellableCoroutine { continuation ->
            val user = FIRAuth.auth().currentUser
                ?: return@suspendCancellableCoroutine continuation.resumeWithException(
                    IllegalStateException("No Firebase user signed in")
                )
            user.getIDTokenForcingRefresh(false) { token, error ->
                when {
                    error != null -> continuation.resumeWithException(Exception(error.localizedDescription))
                    token == null -> continuation.resumeWithException(IllegalStateException("idToken null"))
                    else -> continuation.resume(token)
                }
            }
        }

        return Result.success(token)
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

    override suspend fun getFCMToken(): Result<String> {
        withTimeoutOrNull(3.seconds) {
            apnsTokenFlow.first { it != null }
        } ?: return Result.failure(IllegalStateException("APNs token timeout — simulator?"))


        val token = suspendCancellableCoroutine { continuation ->
            FIRMessaging.messaging().tokenWithCompletion { token, error ->
                when {
                    error != null -> {
                        continuation.resumeWithException(
                            Exception(error.localizedDescription),
                        )
                    }

                    token != null -> {
                        continuation.resume(token)
                    }

                    else -> {
                        continuation.resumeWithException(
                            Exception("FCM token is null"),
                        )
                    }
                }
            }
        }

        return Result.success(token)
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

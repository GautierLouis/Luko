package com.louisgautier.firebase

//import cocoapods.FirebaseCore.FIRApp
//import cocoapods.FirebaseMessaging.FIRMessaging
//import cocoapods.FirebaseMessaging.FIRMessagingDelegateProtocol
//import cocoapods.FirebaseRemoteConfig.FIRRemoteConfig
//import cocoapods.FirebaseRemoteConfig.FIRRemoteConfigSettings
import com.louisgautier.firebase.event.TrackingEvent
//import kotlinx.cinterop.ExperimentalForeignApi
//import platform.darwin.NSObject

//@OptIn(ExperimentalForeignApi::class)
class AppleFirebaseManager(): FirebaseManager {

//    private val rc: FIRRemoteConfig
//        get() = FIRRemoteConfig.remoteConfig()

    override fun initialize() {
//        FIRApp.configure()
//
//        val settings = FIRRemoteConfigSettings().apply {
//            minimumFetchInterval = DEFAULT_MIN_FETCH_INTERVAL.toDouble()
//        }
//        rc.configSettings = settings
//
//        FIRMessaging.messaging().delegate = object : NSObject(), FIRMessagingDelegateProtocol {
//            override fun messaging(messaging: FIRMessaging, didReceiveRegistrationToken: String?) {
//                AppLogger.d("FCM Token: $didReceiveRegistrationToken")
//            }
//        }
    }

    override suspend fun getFCMToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun getInstallationId(): String {
        TODO("Not yet implemented")
    }

    override fun logEvent(event: TrackingEvent) {
        TODO("Not yet implemented")
    }

    override fun setUserId(userId: String) {
        TODO("Not yet implemented")
    }

    override fun setUserProperty(name: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun fetchRemoteConfig() {
//        rc.fetchWithCompletionHandler { status, error ->
//            if (error != null) {
//                //cont.resume(false)
//            } else {
//                // activate
//                rc.fetchAndActivateWithCompletionHandler { changed, actError ->
//                    if (actError != null) {
//                        //cont.resume(false)
//                    } else {
//                        //cont.resume(true)
//                    }
//                }
//            }
//        }
    }


}
//@OptIn(ExperimentalForeignApi::class)
//class iOSFirebaseManager: FirebaseManager {
//
//    private val rc: FIRRemoteConfig
//        get() = FIRRemoteConfig.remoteConfig()
//
//    private val fm: FIRMessaging
//        get() = FIRMessaging.messaging()
//
//    override fun initialize() {
//
//        val settings = FIRRemoteConfigSettings().apply {
//            minimumFetchInterval = DEFAULT_MIN_FETCH_INTERVAL.toDouble()
//        }
//        rc.configSettings = settings
//
//        fm.delegate = object : NSObject(), FIRMessagingDelegateProtocol {
//            override fun messaging(messaging: FIRMessaging, didReceiveRegistrationToken: String?) {
//                AppLogger.d("FCM Token: $didReceiveRegistrationToken")
//            }
//        }
//    }
//
//    override fun logEvent(event: TrackingEvent) {
//    }
//
//    override fun setUserId(userId: String) {
//    }
//
//    override fun setUserProperty(name: String, value: String) {
//    }
//
//    override fun fetchRemoteConfig() {
//        rc.fetchWithCompletionHandler { status, error ->
//            if (error != null) {
//                //cont.resume(false)
//            } else {
//                // activate
//                rc.fetchAndActivateWithCompletionHandler { changed, actError ->
//                    if (actError != null) {
//                        //cont.resume(false)
//                    } else {
//                        //cont.resume(true)
//                    }
//                }
//            }
//        }
//    }
//
//}
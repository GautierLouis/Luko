package com.louisgautier.firebase

import com.louisgautier.logger.AppLogger
import com.louisgautier.tracking.TrackingEvent
import kotlinx.cinterop.ExperimentalForeignApi
import swiftPMImport.Learn.Chinese.library.core.library.core.firebase.FIRApp
import swiftPMImport.Learn.Chinese.library.core.library.core.firebase.FIRRemoteConfig
import swiftPMImport.Learn.Chinese.library.core.library.core.firebase.FIRRemoteConfigFetchAndActivateStatus

@OptIn(ExperimentalForeignApi::class)
class DebugFirebaseManager : FirebaseManager {
    override fun initialize() {
        FIRApp.configure()
    }

    override suspend fun getFCMToken(): String = "mock-token"

    override suspend fun getInstallationId(): String = "mock-installation-id"

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

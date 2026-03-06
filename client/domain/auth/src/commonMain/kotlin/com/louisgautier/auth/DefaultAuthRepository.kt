package com.louisgautier.auth

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.network.interfaces.AuthService
import com.louisgautier.preferences.AppPreferences

internal class DefaultAuthRepository(
    private val client: AuthService,
    private val preferences: AppPreferences,
    private val firebaseManager: FirebaseManager
) : AuthRepository {

    override suspend fun registerAnonymously(): Result<Unit> {
        val installationId = firebaseManager.getInstallationId()
        val fcmToken = firebaseManager.getFCMToken()

        preferences.setInstallationId(installationId)
        preferences.setFcmToken(fcmToken)

        return client.registerDevice(RegisterDeviceRequestDto(installationId, fcmToken))
    }
}


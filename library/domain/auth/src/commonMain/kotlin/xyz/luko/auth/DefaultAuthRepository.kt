package xyz.luko.auth

import xyz.luko.apicontracts.dto.RegisterDeviceRequestDto
import xyz.luko.firebase.FirebaseManager
import xyz.luko.logger.AppLogger
import xyz.luko.network.interfaces.AuthService
import xyz.luko.preferences.AppPreferences

internal class DefaultAuthRepository(
    private val client: AuthService,
    private val preferences: AppPreferences,
    private val firebaseManager: FirebaseManager,
) : AuthRepository {
    override suspend fun registerAnonymously(): Result<Unit> {
        val installationId = firebaseManager.getInstallationId()
        val fcmToken = firebaseManager.getFCMToken()

        AppLogger.i(
            tag = "Registration",
            message = "Register anonymously with FCM $fcmToken for installation $installationId",
        )

        preferences.setInstallationId(installationId)
        preferences.setFcmToken(fcmToken)

        return client.registerDevice(RegisterDeviceRequestDto(installationId, fcmToken))
    }

    override suspend fun registerNewToken(token: String): Result<Unit> {
        val installationId = firebaseManager.getInstallationId()

        preferences.setFcmToken(token)

        return client.updateFcm(RegisterDeviceRequestDto(installationId, token))
    }
}

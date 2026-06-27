package xyz.luko.domain.repository

import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.network.interfaces.AuthService
import xyz.luko.preferences.AppPreferences
import xyz.luko.utils.AppLogger

internal class DefaultAuthRepository(
    private val client: AuthService,
    private val preferences: AppPreferences,
) : AuthRepository {

    override suspend fun getUserId(): String? {
        return preferences.getUserId()
    }

    override suspend fun registerAnonymously(id: String, fcmToken: String?) {
        preferences.setUserId(id)
        client.registerAnonymously(AuthRegistrationDto(fcmToken = fcmToken))
            .onFailure { AppLogger.w(tag = "Auth", message = "Failed to register anonymously") }
    }

    override suspend fun updateFcm(fcmToken: String?) {
        val cachedFcm = preferences.getFcmToken()
        if (fcmToken != null && fcmToken != cachedFcm) {
            client.updateFcm(FcmUpdateDto(fcmToken = fcmToken))
            preferences.setFcmToken(fcmToken)
        }
    }

    // As of now, called only from Android Worker if token change.
    // TODO Test it and do the same of iOS
    override suspend fun onNewFcmToken(token: String): Result<Unit> = runCatching {
        preferences.setFcmToken(token)

        if (!preferences.hasUserId()) {
            return@runCatching
        }

        client.updateFcm(FcmUpdateDto(fcmToken = token)).getOrThrow()
    }
}

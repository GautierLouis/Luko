package xyz.luko.domain.repository

import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.firebase.FirebaseManager
import xyz.luko.utils.AppLogger
import xyz.luko.network.interfaces.AuthService
import xyz.luko.preferences.AppPreferences

internal class DefaultAuthRepository(
    private val client: AuthService,
    private val preferences: AppPreferences,
    private val firebaseManager: FirebaseManager,
) : AuthRepository {

    override suspend fun registerAnonymously(): Result<Unit> = runCatching {
        val fcmToken = firebaseManager.getFCMToken().getOrNull()
        val cached = preferences.getUserId()

        if (cached == null) {
            // First launch — Firebase + create user
            val uid = firebaseManager.registerAnonymously().getOrThrow()
            client.registerAnonymously(AuthRegistrationDto(fcmToken = fcmToken))
                .onSuccess { preferences.setUserId(uid) }
                .onFailure { AppLogger.w(tag = "Auth", message = "Failed to register anonymously") }
        } else {
            // Already registered — update FCM only if changed
            val cachedFcm = preferences.getFcmToken()
            if (fcmToken != null && fcmToken != cachedFcm) {
                client.updateFcm(FcmUpdateDto(fcmToken = fcmToken))
                preferences.setFcmToken(fcmToken)
            }
            // else — nothing to do, skip network call entirely
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

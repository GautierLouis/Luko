package xyz.luko.domain.repository

import xyz.luko.firebase.FirebaseManager

class AppStartUseCase(
    private val authRepository: AuthRepository,
    private val firebaseManager: FirebaseManager
) {

    suspend fun initialize() = runCatching {
        firebaseManager.initialize() //TODO make sure this finish before requesting fcm or userId
        val fcmToken = firebaseManager.getFCMToken().getOrNull()
        val cached = authRepository.getUserId()

        if (cached == null) {
            // First launch — Firebase + create user
            val uid = firebaseManager.registerAnonymously().getOrThrow()
            authRepository.registerAnonymously(uid, fcmToken)
        } else {
            // Already registered — update FCM only if changed
            authRepository.updateFcm(fcmToken)
        }
    }
}

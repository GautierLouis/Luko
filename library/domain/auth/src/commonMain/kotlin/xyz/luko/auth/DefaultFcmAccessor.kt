package xyz.luko.auth

import xyz.luko.firebase.notification.FcmAccessor

class DefaultFcmAccessor(
    private val authRepository: AuthRepository,
) : FcmAccessor {
    override suspend fun onNewFcmToken(token: String): Result<Unit> =
        authRepository.onNewFcmToken(token)
}

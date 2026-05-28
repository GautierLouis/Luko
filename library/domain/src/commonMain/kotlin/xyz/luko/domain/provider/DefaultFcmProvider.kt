package xyz.luko.domain.provider

import xyz.luko.domain.repository.AuthRepository
import xyz.luko.firebase.notification.FcmProvider

class DefaultFcmProvider(
    private val authRepository: AuthRepository,
) : FcmProvider {
    override suspend fun onNewFcmToken(token: String): Result<Unit> =
        authRepository.onNewFcmToken(token)
}

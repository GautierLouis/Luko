package com.louisgautier.auth

import com.louisgautier.firebase.notification.FcmAccessor

class DefaultFcmAccessor(
    private val authRepository: AuthRepository,
) : FcmAccessor {
    override suspend fun registerNewToken(token: String): Result<Unit> =
        authRepository.registerNewToken(token)
}

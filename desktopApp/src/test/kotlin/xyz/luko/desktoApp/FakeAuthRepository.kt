package xyz.luko.desktoApp

import xyz.luko.domain.repository.AuthRepository

class FakeAuthRepository : AuthRepository {
    override suspend fun registerAnonymously() = Result.success(Unit)
    override suspend fun onNewFcmToken(token: String) = Result.success(Unit)
}

package xyz.luko.domain.provider

import xyz.luko.firebase.FirebaseManager
import xyz.luko.network.interfaces.TokenProvider

class DefaultTokenProvider(
    private val firebaseManager: FirebaseManager,
) : TokenProvider {
    override suspend fun getToken(forceRefresh: Boolean): String? {
        return firebaseManager.getIdToken(forceRefresh).getOrNull()
    }
}

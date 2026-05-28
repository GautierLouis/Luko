package xyz.luko.server.domain.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface TokenVerifier {
    suspend fun verify(token: String): Result<String>
}

internal class DefaultTokenVerifier : TokenVerifier {
    override suspend fun verify(token: String): Result<String> {
        return runCatching {
            withContext(Dispatchers.IO) {
                FirebaseAuth.getInstance().verifyIdToken(token).uid
            }
        }
    }
}

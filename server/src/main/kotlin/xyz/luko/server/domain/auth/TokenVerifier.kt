package xyz.luko.server.domain.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.luko.server.ServerConfig

interface TokenVerifier {
    suspend fun verify(token: String): Result<String>
}

internal class DefaultTokenVerifier(
    private val serverConfig: ServerConfig,
) : TokenVerifier {
    override suspend fun verify(token: String): Result<String> {
        if (serverConfig.env == ServerConfig.Env.DEV) return Result.success("dev-user")

        return runCatching {
            withContext(Dispatchers.IO) {
                FirebaseAuth.getInstance().verifyIdToken(token).uid
            }
        }
    }
}

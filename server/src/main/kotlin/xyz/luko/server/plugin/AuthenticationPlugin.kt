package xyz.luko.server.plugin

import com.google.firebase.auth.FirebaseAuth
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val BEARER = "firebase"

class AuthenticationPlugin : Plugin {

    override fun Application.register() {
        install(Authentication) {
            bearer(BEARER) {
                authenticate { credential ->
                    val token = runCatching {
                        withContext(Dispatchers.IO) {
                            FirebaseAuth.getInstance().verifyIdToken(credential.token)
                        }
                    }.getOrNull() ?: return@authenticate null

                    UserIdPrincipal(token.uid)
                }
            }
        }
    }
}


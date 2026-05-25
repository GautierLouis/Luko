package xyz.luko.server.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer
import xyz.luko.server.domain.auth.TokenVerifier

const val BEARER = "firebase"

class AuthenticationPlugin(
    private val tokenVerifier: TokenVerifier
) : Plugin {

    override fun Application.register() {
        install(Authentication) {
            bearer(BEARER) {
                authenticate { credential ->
                    tokenVerifier.verify(credential.token)
                        .onFailure { return@authenticate null }
                        .onSuccess { UserIdPrincipal(it) }
                }
            }
        }
    }
}

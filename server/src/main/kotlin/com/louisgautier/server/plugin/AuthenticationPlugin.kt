package com.louisgautier.server.plugin

import com.louisgautier.server.auth.JwtProvider
import com.louisgautier.server.auth.JwtProvider.Constants.JWT_NAME
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

class AuthenticationPlugin(
    private val jwtProvider: JwtProvider
) {
    fun Application.register() {
        install(Authentication) {
            jwt(JWT_NAME) {
                verifier { header -> jwtProvider.verify(header) }
                validate { credential -> jwtProvider.validate(credential) }
                challenge { _, _ ->
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = mapOf("error" to "Invalid or missing token")
                    )
                }
            }
        }
    }
}
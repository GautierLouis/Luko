package com.louisgautier.server.router.route

import com.louisgautier.apicontracts.dto.UserRefreshTokenJson
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.auth.JwtProvider.Constants.JWT_NAME
import com.louisgautier.server.domain.repo.AuthenticationRepository
import com.louisgautier.server.router.RouteController
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

class AuthedRouteController(
    private val authenticationRepository: AuthenticationRepository
) : RouteController {
    override fun Route.register() {
        authenticate(JWT_NAME) {
            get<EndPoint.Me> {
                val principal = call.principal<JWTPrincipal>()!!
                val supabaseUserId = principal.payload.getClaim("sub").asString()
                call.respond(mapOf("user_id" to supabaseUserId))
            }

            post<EndPoint.RefreshToken> {
                val creds = call.receive<UserRefreshTokenJson>()
                authenticationRepository.refreshSession(creds.refreshToken)
                    .onSuccess { data ->
                        call.respond(HttpStatusCode.OK, data)
                    }
                    .onFailure {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
            }
        }
    }
}
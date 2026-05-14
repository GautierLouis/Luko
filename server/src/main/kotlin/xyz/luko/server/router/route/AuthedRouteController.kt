package xyz.luko.server.router.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import xyz.luko.apicontracts.dto.UserRefreshTokenJson
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.server.auth.JwtProvider.Constants.JWT_NAME
import xyz.luko.server.domain.repo.AuthenticationRepository
import xyz.luko.server.router.RouteController

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
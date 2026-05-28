package xyz.luko.server.router.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import xyz.luko.apicontracts.dto.AppHeader.APP_PLATFORM
import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.domain.repo.UserRepository
import xyz.luko.server.plugin.BEARER
import xyz.luko.server.router.RouteController

class AuthedRouteController(
    private val userRepository: UserRepository
) : RouteController {
    override fun Route.register() {
        authenticate(BEARER) {
            post<Destination.RegisterAnonymously> {
                val principal = call.principal<UserIdPrincipal>()!!
                val body = call.receive<AuthRegistrationDto>()
                val platform = call.request.headers[APP_PLATFORM]!!

                userRepository.registerAnonymously(
                    uid = principal.name,
                    platform = platform,
                    body = body
                )

                call.respond(HttpStatusCode.OK)
            }

            post<Destination.UpdateFcm> {
                val principal = call.principal<UserIdPrincipal>()!!
                val body = call.receive<FcmUpdateDto>()

                userRepository.updateFcm(principal.name, body)

                call.respond(HttpStatusCode.NoContent)
            }

            get<Destination.Me> {
                val principal = call.principal<UserIdPrincipal>()
                call.respond(mapOf("user_id" to principal))
            }
        }
    }
}

package xyz.luko.server.router.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import xyz.luko.apicontracts.dto.RegisterDeviceRequestDto
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.server.domain.repo.AuthenticationRepository
import xyz.luko.server.router.RouteController

class UnauthedRouteController(
    private val authenticationRepository: AuthenticationRepository
) : RouteController {
    override fun Route.register() {
        get("/") {
            call.respondText("Ktor")
        }

        post<EndPoint.RegisterAnonymously> {
            val creds = call.receive<RegisterDeviceRequestDto>()

            authenticationRepository.registerAnonymously(creds)
                .onSuccess { data ->
                    call.respond(HttpStatusCode.OK, data)
                }
                .onFailure {
                    call.respond(HttpStatusCode.InternalServerError)
                }
        }

        post<EndPoint.UpdateFcm> {
            val creds = call.receive<RegisterDeviceRequestDto>()

            authenticationRepository.updateFcm(creds)
            call.respond(HttpStatusCode.OK, creds)
        }
    }
}
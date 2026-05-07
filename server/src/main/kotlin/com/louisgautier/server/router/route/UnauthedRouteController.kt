package com.louisgautier.server.router.route

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.domain.repo.AuthenticationRepository
import com.louisgautier.server.router.RouteController
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

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
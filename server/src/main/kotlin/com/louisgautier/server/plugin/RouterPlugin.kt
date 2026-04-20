package com.louisgautier.server.plugin

import com.louisgautier.server.router.RouteController
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

class RouterPlugin(
    private val controllers: List<RouteController>
) {
    fun Application.register() {
        routing {
            controllers.forEach { controller ->
                with(controller) { register() }
            }
        }
    }
}
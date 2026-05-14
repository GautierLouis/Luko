package xyz.luko.server.plugin

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import xyz.luko.server.router.RouteController

class RouterPlugin(
    private val controllers: List<RouteController>
) : Plugin {
    override fun Application.register() {
        routing {
            controllers.forEach { controller ->
                with(controller) { register() }
            }
        }
    }
}
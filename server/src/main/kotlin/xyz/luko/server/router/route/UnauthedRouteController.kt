package xyz.luko.server.router.route

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import xyz.luko.server.router.RouteController

class UnauthedRouteController : RouteController {
    override fun Route.register() {
        get("/") {
            call.respondText("Ktor")
        }
    }
}

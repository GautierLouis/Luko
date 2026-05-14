package xyz.luko.server.router.route

import io.ktor.http.ContentType
import io.ktor.openapi.OpenApiInfo
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.OpenApiDocSource
import io.ktor.server.routing.routingRoot
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.server.router.RouteController

class AdminRouteController(
    private val metricsRegistry: PrometheusMeterRegistry
) : RouteController {

    override fun Route.register() {
        swaggerUI(path = "admin/swagger") {
            info = OpenApiInfo("Luko API", "1.0")
            source = OpenApiDocSource.Routing(ContentType.Application.Json) {
                routingRoot.descendants()
            }
        }
        get<EndPoint.Admin.Metrics> {
            call.respond(metricsRegistry.scrape())
        }
    }
}

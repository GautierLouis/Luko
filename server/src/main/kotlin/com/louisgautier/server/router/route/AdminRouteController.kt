package com.louisgautier.server.router.route

import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.router.RouteController
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

class AdminRouteController(
    private val metricsRegistry: PrometheusMeterRegistry
) : RouteController {

    override fun Route.register() {
        swaggerUI(path = "admin/swagger")
        openAPI(path = "admin/openapi")
        get<EndPoint.Admin.Metrics> {
            call.respond(metricsRegistry.scrape())
        }
    }
}

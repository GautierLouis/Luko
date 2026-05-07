package com.louisgautier.server.router.route

import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.router.RouteController
import io.ktor.http.ContentType
import io.ktor.openapi.OpenApiInfo
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.OpenApiDocSource
import io.ktor.server.routing.routingRoot
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

class AdminRouteController(
    private val metricsRegistry: PrometheusMeterRegistry
) : RouteController {

    override fun Route.register() {
        swaggerUI(path = "admin/swagger") {
            info = OpenApiInfo("LearnChinese API", "1.0")
            source = OpenApiDocSource.Routing(ContentType.Application.Json) {
                routingRoot.descendants()
            }
        }
        get<EndPoint.Admin.Metrics> {
            call.respond(metricsRegistry.scrape())
        }
    }
}

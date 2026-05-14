package xyz.luko.server.router

import io.ktor.server.routing.Route

interface RouteController {
    fun Route.register()
}
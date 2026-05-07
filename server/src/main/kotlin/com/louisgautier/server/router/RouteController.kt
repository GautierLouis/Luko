package com.louisgautier.server.router

import io.ktor.server.routing.Route

interface RouteController {
    fun Route.register()
}
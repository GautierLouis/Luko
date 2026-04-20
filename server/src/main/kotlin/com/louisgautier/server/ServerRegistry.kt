package com.louisgautier.server

import com.louisgautier.server.plugin.AuthenticationPlugin
import com.louisgautier.server.plugin.BasePlugin
import com.louisgautier.server.plugin.MetricsPlugin
import com.louisgautier.server.plugin.RouterPlugin
import io.ktor.server.application.Application

class ServerRegistry(
    private val authenticationPlugin: AuthenticationPlugin,
    private val basePlugin: BasePlugin,
    private val metricsPlugin: MetricsPlugin,
    private val routerPlugin: RouterPlugin,
) {

    fun Application.start() {
        with(basePlugin) { register() }
        with(metricsPlugin) { register() }
        with(authenticationPlugin) { register() }
        with(routerPlugin) { register() }
    }
}
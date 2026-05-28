package xyz.luko.server

import io.ktor.server.application.Application
import xyz.luko.server.plugin.AuthenticationPlugin
import xyz.luko.server.plugin.BasePlugin
import xyz.luko.server.plugin.ErrorPlugin
import xyz.luko.server.plugin.FirebasePlugin
import xyz.luko.server.plugin.HeaderValidatorPlugin
import xyz.luko.server.plugin.MetricsPlugin
import xyz.luko.server.plugin.RouterPlugin

class ServerRegistry(
    private val basePlugin: BasePlugin,
    private val metricsPlugin: MetricsPlugin,
    private val headerValidatorPlugin: HeaderValidatorPlugin,
    private val routerPlugin: RouterPlugin,
    private val errorPlugin: ErrorPlugin,
    private val firebasePlugin: FirebasePlugin,
    private val authenticationPlugin: AuthenticationPlugin
) {

    fun Application.start() {
        with(errorPlugin) { register() }
        with(basePlugin) { register() }
        with(firebasePlugin) { register() }
        with(authenticationPlugin) { register() }
        with(headerValidatorPlugin) { register() }
        with(metricsPlugin) { register() }
        with(routerPlugin) { register() }
    }
}

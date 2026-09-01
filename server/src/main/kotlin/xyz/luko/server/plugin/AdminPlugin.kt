package xyz.luko.server.plugin

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import io.ktor.server.request.path
import io.ktor.server.response.respond

class AdminAccessPlugin(
    private val adminToken: String, // from ServerConfig / env — never hardcode
) : Plugin {
    override fun Application.register() {
        install(createApplicationPlugin("AdminAccess") {
            onCall { call ->
                val path = call.request.path()
                if (!path.startsWith("/admin")) return@onCall

                val token = call.request.headers["X-Admin-Token"]
                if (token != adminToken) {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        })
    }
}

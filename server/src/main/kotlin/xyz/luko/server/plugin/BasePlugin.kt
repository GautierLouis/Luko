package xyz.luko.server.plugin

import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.resources.Resources
import xyz.luko.apicontracts.defaultJson

class BasePlugin : Plugin {
    override fun Application.register() {
        install(ContentNegotiation) {
            json(defaultJson)
        }
        install(Resources)
        install(CORS) {
            anyHost()
            allowHeader(HttpHeaders.ContentType)
        }
    }
}
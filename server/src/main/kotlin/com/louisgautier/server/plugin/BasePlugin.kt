package com.louisgautier.server.plugin

import com.louisgautier.apicontracts.defaultJson
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.resources.Resources

class BasePlugin {
    fun Application.register() {
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
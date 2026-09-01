package xyz.luko.server.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import io.ktor.server.request.path
import io.ktor.server.resources.href
import xyz.luko.apicontracts.dto.AppHeader.APP_BUILD
import xyz.luko.apicontracts.dto.AppHeader.APP_PLATFORM
import xyz.luko.apicontracts.dto.AppHeader.APP_VERSION
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.error.missingHeader

class HeaderValidatorPlugin : Plugin {
    override fun Application.register() {
        val adminPath = href(Destination.Admin())

        install(createApplicationPlugin("AppHeadersValidation") {
            onCall { call ->
                if (call.request.path().startsWith(adminPath)) return@onCall

                call.request.headers[APP_PLATFORM]
                    ?: throw missingHeader(APP_PLATFORM)
                call.request.headers[APP_VERSION]
                    ?: throw missingHeader(APP_VERSION)
                call.request.headers[APP_BUILD]
                    ?: throw missingHeader(APP_BUILD)
            }
        })
    }
}

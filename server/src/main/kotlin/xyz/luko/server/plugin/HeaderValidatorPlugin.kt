package xyz.luko.server.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import xyz.luko.apicontracts.dto.AppHeader.APP_BUILD
import xyz.luko.apicontracts.dto.AppHeader.APP_PLATFORM
import xyz.luko.apicontracts.dto.AppHeader.APP_VERSION
import xyz.luko.server.error.missingHeader

class HeaderValidatorPlugin : Plugin {
    override fun Application.register() {
        install(createApplicationPlugin("AppHeadersValidation") {
            onCall { call ->
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

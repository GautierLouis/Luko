package xyz.luko.server.plugin

import io.ktor.server.application.Application

interface Plugin {
    fun Application.register()
}
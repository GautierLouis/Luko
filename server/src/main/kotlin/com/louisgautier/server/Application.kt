package com.louisgautier.server

import com.louisgautier.server.database.Database
import com.louisgautier.server.di.initKoin
import com.louisgautier.server.di.serverModule
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import org.koin.core.module.Module
import org.koin.ktor.ext.get

/**
 * Main entry point for the application
 * Refer to application.conf for configuration
 */
fun main(args: Array<String>): Unit = EngineMain.main(args)

/**
 * Configures and initializes the Ktor application module.
 *
 * This function handles the setup of dependency injection via Koin, starts the [ServerRegistry],
 * and initializes the [Database].
 *
 * @param defaultModule An optional Koin [Module] to override the default server configuration,
 * typically used for testing purposes.
 */
fun Application.module(defaultModule: Module?) {

    initKoin(defaultModule ?: serverModule)

    with(get<ServerRegistry>()) {
        start()
    }

    with(get<Database>()) {
        init()
    }
}

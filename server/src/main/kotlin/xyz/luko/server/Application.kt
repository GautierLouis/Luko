package xyz.luko.server

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.module.Module
import org.koin.ktor.ext.get
import xyz.luko.server.data.database.Database
import xyz.luko.server.di.initKoin
import xyz.luko.server.di.providesModule

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

    initKoin(defaultModule ?: providesModule())

    with(get<ServerRegistry>()) {
        start()
    }

    with(get<Database>()) {
        launch(Dispatchers.IO) { init() }
    }
}

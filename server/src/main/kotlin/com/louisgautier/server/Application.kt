package com.louisgautier.server

import com.louisgautier.server.database.Database
import com.louisgautier.server.di.initKoin
import com.louisgautier.server.parser.FileParser
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import kotlinx.coroutines.launch
import org.koin.ktor.ext.get

/**
 * Main entry point for the application
 * Refer to application.conf for configuration
 */
fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {

    initKoin()

    with(get<ServerRegistry>()) {
        start()
    }

    with(get<Database>()) {
        init()
    }

    launch {
        get<FileParser>().init()
    }
}

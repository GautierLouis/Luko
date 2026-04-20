package com.louisgautier.server.di

import com.louisgautier.server.ServerConfig
import com.louisgautier.server.ServerRegistry
import com.louisgautier.server.auth.authModule
import com.louisgautier.server.database.databaseModule
import com.louisgautier.server.domain.domainModule
import com.louisgautier.server.parser.FileParser
import com.louisgautier.server.plugin.pluginModule
import com.louisgautier.server.router.routerModule
import com.louisgautier.server.supabase.supabaseModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.initKoin() {
    install(Koin) {
        val env = ServerConfig.build(environment)

        slf4jLogger(env.koinLogLevel)

        modules(module {
            includes(
                databaseModule,
                domainModule,
                supabaseModule,
                routerModule,
                authModule,
                pluginModule
            )

            single { PrometheusMeterRegistry(PrometheusConfig.DEFAULT) }
            single { env }
            singleOf(::ServerRegistry)
            singleOf(::FileParser)
        })
    }
}


package xyz.luko.server.di

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import xyz.luko.server.ServerConfig
import xyz.luko.server.ServerRegistry
import xyz.luko.server.auth.authModule
import xyz.luko.server.database.databaseModule
import xyz.luko.server.domain.domainModule
import xyz.luko.server.plugin.pluginModule
import xyz.luko.server.router.routerModule
import xyz.luko.server.supabase.supabaseModule

fun Application.initKoin(
    defaultModule: Module,
) {
    install(Koin) {
        val env = ServerConfig.build(environment)

        slf4jLogger(env.koinLogLevel)

        val internalModule = module {
            single { PrometheusMeterRegistry(PrometheusConfig.DEFAULT) }
            single { env }
        }

        modules(listOf(defaultModule, internalModule))
    }
}

fun provideServerModule() = module {
    includes(infraModule, serverModule)
}

private val infraModule = module {
    includes(
        databaseModule,
        supabaseModule,
    )
}

val serverModule = module {
    includes(
        domainModule,
        routerModule,
        authModule,
        pluginModule
    )
    singleOf(::ServerRegistry)
}


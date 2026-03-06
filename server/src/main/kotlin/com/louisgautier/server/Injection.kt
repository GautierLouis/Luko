package com.louisgautier.server

import com.louisgautier.server.domain.AuthenticationRepository
import com.louisgautier.server.domain.AuthenticationRepositoryImpl
import com.louisgautier.server.domain.DictionaryRepository
import com.louisgautier.server.domain.GraphicRepository
import com.louisgautier.server.domain.UserRepository
import com.louisgautier.server.parser.FileParser
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.application.install
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.installModule(envModule: Module) {
    install(Koin) {
        slf4jLogger(Level.INFO)
        modules(listOf(envModule) + standardModules)
    }
}

private val serverModule = module {
    single { PrometheusMeterRegistry(PrometheusConfig.DEFAULT) }
    single { JwtProvider(get()) }
}

private val domainModule = module {
    singleOf(::AuthenticationRepositoryImpl) bind AuthenticationRepository::class
    singleOf(::DictionaryRepository)
    singleOf(::GraphicRepository)
    singleOf(::UserRepository)
    singleOf(::FileParser)
}

private val supabaseModule = module {
    single {
        createSupabaseClient(
            supabaseUrl = get<BuildEnvironment>().supabaseUrl,
            supabaseKey = get<BuildEnvironment>().supabasePublicKey
        ) {
            install(Auth)
        }
    }
}

fun buildEnvModule(applicationEnvironment: ApplicationEnvironment) = module {
    single {
        BuildEnvironment.build(applicationEnvironment)
    }
}

private val standardModules = listOf(serverModule, domainModule, supabaseModule)

package com.louisgautier.server.supabase

import com.louisgautier.server.ServerConfig
import io.github.jan.supabase.SupabaseClientBuilder
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.logging.LogLevel
import io.github.jan.supabase.storage.Storage
import io.ktor.client.plugins.HttpRequestRetry
import org.koin.core.qualifier.named
import org.koin.dsl.module

@OptIn(SupabaseInternal::class)
private fun SupabaseClientBuilder.default(level: LogLevel) {
    defaultLogLevel = level
    httpConfig {
        install(HttpRequestRetry) {
            maxRetries = 1
        }
    }
}

enum class SupabaseClientMode {
    ADMIN, USER
}

val supabaseModule = module {
    single(named(SupabaseClientMode.USER)) {
        val config = get<ServerConfig>()
        createSupabaseClient(
            supabaseUrl = config.supabaseUrl,
            supabaseKey = config.supabasePublicKey
        ) {
            default(config.ktorLogLevel)
            install(Auth)
        }
    }

    single(named(SupabaseClientMode.ADMIN)) {
        val config = get<ServerConfig>()
        createSupabaseClient(
            supabaseUrl = config.supabaseUrl,
            supabaseKey = config.supabasePrivateKey
        ) {
            default(config.ktorLogLevel)
            install(Storage)
        }
    }
}
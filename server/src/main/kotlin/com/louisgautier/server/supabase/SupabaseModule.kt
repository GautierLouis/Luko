package com.louisgautier.server.supabase

import com.louisgautier.server.ServerConfig
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.ktor.client.plugins.HttpRequestRetry
import org.koin.dsl.module

@OptIn(SupabaseInternal::class)
val supabaseModule = module {
    single {
        val env = get<ServerConfig>()
        createSupabaseClient(
            supabaseUrl = get<ServerConfig>().supabaseUrl,
            supabaseKey = get<ServerConfig>().supabasePublicKey
        ) {
            install(Auth)
            defaultLogLevel = env.ktorLogLevel
            httpConfig {
                install(HttpRequestRetry) {
                    maxRetries = 1
                }
            }
        }
    }
}
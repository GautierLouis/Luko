package xyz.luko.server.supabase

import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.ktor.client.plugins.HttpRequestRetry
import org.koin.dsl.module
import xyz.luko.server.ServerConfig

@OptIn(SupabaseInternal::class)
val supabaseModule = module {
    single {
        val config = get<ServerConfig>()
        createSupabaseClient(
            supabaseUrl = config.supabaseUrl,
            supabaseKey = config.supabasePrivateKey
        ) {
            defaultLogLevel = config.ktorLogLevel
            httpConfig {
                install(HttpRequestRetry) {
                    maxRetries = 1
                }
            }
            install(Storage)
        }
    }
}

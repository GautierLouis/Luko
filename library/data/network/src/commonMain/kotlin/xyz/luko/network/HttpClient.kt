package xyz.luko.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import xyz.luko.apicontracts.defaultJson
import xyz.luko.network.interfaces.TokenProvider
import xyz.luko.utils.AppConfig

internal fun buildHttpClient(
    tokenProvider: TokenProvider,
    appConfig: AppConfig,
    engine: HttpClientEngine
) = HttpClient(engine) {
    expectSuccess = true
    install(Resources)
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(defaultJson)
    }

    defaultRequest {
        url(urlString = appConfig.baseUrl)
        header("X-Platform", appConfig.platform)
        header("App-Version", appConfig.versionName)
        header("App-Build", appConfig.versionCode)
        contentType(ContentType.Application.Json)
    }

    install(Auth) {
        bearer {
            loadTokens {
                val token =
                    tokenProvider.getToken(forceRefresh = false) ?: return@loadTokens null
                BearerTokens(
                    accessToken = token,
                    refreshToken = "" // unused — Firebase owns refresh
                )
            }

            refreshTokens {
                val token =
                    tokenProvider.getToken(forceRefresh = true) ?: return@refreshTokens null
                BearerTokens(
                    accessToken = token,
                    refreshToken = "" // same
                )
            }
        }
    }
}

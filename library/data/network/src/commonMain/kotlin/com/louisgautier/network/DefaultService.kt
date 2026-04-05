package com.louisgautier.network

import com.louisgautier.apicontracts.defaultJson
import com.louisgautier.apicontracts.dto.UserRefreshTokenJson
import com.louisgautier.apicontracts.dto.UserTokenJson
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.network.interfaces.TokenAccessor
import com.louisgautier.utils.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
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
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

internal class DefaultService(
    private val engine: HttpClientEngine = engineFactory.create(),
    private val tokenAccessor: TokenAccessor,
    private val env: NetworkEnvironment,
    private val appConfig: AppConfig
) {
    val unauthedClient = createDefaultClient()

    val authedClient = createDefaultClient { installAuth() }

    internal fun createDefaultClient(
        config: HttpClientConfig<*>.() -> Unit = { },
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
            url { protocol = env.scheme }
            host = env.host
            port = env.port
            header("X-Platform", appConfig.platform)
            header("App-Version", appConfig.versionName)
            header("App-Build", appConfig.versionCode)
            contentType(ContentType.Application.Json)
        }

        apply(config)
    }

    private fun HttpClientConfig<*>.installAuth() {
        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = tokenAccessor.getUserToken()
                    val refreshToken = tokenAccessor.getUserRefreshToken()
                    if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                        null // No tokens available
                    } else {
                        BearerTokens(accessToken, refreshToken)
                    }
                }

                refreshTokens {
                    val oldRefreshToken =
                        UserRefreshTokenJson(tokenAccessor.getUserRefreshToken().orEmpty())

                    val newTokens =
                        call<UserTokenJson> {
                            unauthedClient.post(EndPoint.RefreshToken()) {
                                setBody(oldRefreshToken)
                            }
                        }

                    if (newTokens.isSuccess) {
                        tokenAccessor.setUserToken(newTokens.getOrNull()!!.accessToken)
                        tokenAccessor.setUserRefreshToken(newTokens.getOrNull()!!.refreshToken)
                        BearerTokens(
                            newTokens.getOrNull()!!.accessToken,
                            newTokens.getOrNull()!!.refreshToken,
                        )
                    } else {
                        // Failed to refresh, clear tokens or trigger logout
                        tokenAccessor.removeUserToken()
                        null
                    }
                }
            }
        }
    }
}

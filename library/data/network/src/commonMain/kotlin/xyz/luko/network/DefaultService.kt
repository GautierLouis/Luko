package xyz.luko.network

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
import xyz.luko.apicontracts.defaultJson
import xyz.luko.apicontracts.dto.UserRefreshTokenJson
import xyz.luko.apicontracts.dto.UserTokenJson
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.network.interfaces.TokenAccessor
import xyz.luko.utils.AppConfig
import xyz.luko.utils.Flavor

internal class DefaultService(
    private val tokenAccessor: TokenAccessor,
    private val appConfig: AppConfig,
    private val engine: HttpClientEngine = engineFactory.create()
) {

    val unauthedClient = createDefaultClient()

    val authedClient = createDefaultClient { installAuth() }

    private val baseUrl: String
        get() = when (appConfig.flavor) {
            Flavor.DEV -> "http://10.0.2.2"
            Flavor.STAGING -> "https://staging-api.lukoapp.xyz"
            Flavor.PROD -> "https://api.lukoapp.xyz"
        }

    private fun createDefaultClient(
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
            url(urlString = baseUrl)
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

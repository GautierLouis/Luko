package com.louisgautier.network

import com.louisgautier.apicontracts.dto.UserRefreshTokenJson
import com.louisgautier.apicontracts.dto.UserTokenJson
import com.louisgautier.network.interfaces.TokenAccessor
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.Flavor
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BaseHttpClientTest {
    private val mockTokenAccessor = mock<TokenAccessor>(MockMode.autofill)
    private val mockAppConfig = AppConfig(
        platform = "mock",
        flavor = Flavor.DEV,
        versionName = "mock",
        versionCode = "mock"
    )
    private val mockEnvironment = NetworkEnvironment.Dev

    @Test
    fun `assert that call function is catching malformed response properly`() {
        val mockEngine =
            MockEngine { request ->
                respond(
                    content = "",
                    status = HttpStatusCode.OK,
                )
            }

        val client = DefaultService(mockEngine, mockTokenAccessor, mockEnvironment, mockAppConfig)
        val service = DefaultAuthService(client.unauthedClient)

        runBlocking {
            val response = service.forceRefresh(UserRefreshTokenJson(""))
            assertTrue(response.isFailure)
        }
    }

    @Test
    fun `assert that call function is catching response != 2xx properly`() {
        val mockEngine =
            MockEngine { request ->
                respond(
                    content = "",
                    status = HttpStatusCode.Unauthorized,
                )
            }

        val client = DefaultService(mockEngine, mockTokenAccessor, mockEnvironment, mockAppConfig)
        val service = DefaultAuthService(client.unauthedClient)

        runBlocking {
            val response = service.registerAnon()
            assertTrue(response.isFailure)
        }
    }

    @Test
    fun `assert that call function is catching response == 2xx properly`() {
        val mockEngine =
            MockEngine { request ->
                respond(
                    content = ByteReadChannel(Json.encodeToString(UserTokenJson("", "", 0L))),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }

        val client = DefaultService(mockEngine, mockTokenAccessor, mockEnvironment, mockAppConfig)
        val service = DefaultAuthService(client.unauthedClient)

        runBlocking {
            val response = service.registerAnon()
            assertTrue(response.isSuccess)
        }
    }

    @Test
    fun `assert that call function is catching timeout properly`() {
        val mockEngine =
            MockEngine { request ->
                throw HttpRequestTimeoutException(request.url.toString(), 1000)
            }

        val client = DefaultService(mockEngine, mockTokenAccessor, mockEnvironment, mockAppConfig)
        val service = DefaultAuthService(client.unauthedClient)

        runBlocking {
            val response = service.registerAnon()
            assertTrue(response.isFailure)
        }
    }

    @Test
    fun `assert that token is send properly`() {
        val mockEngine =
            MockEngine { request ->
                respond(
                    content = "",
                    status = HttpStatusCode.OK,
                )
            }

        everySuspend { mockTokenAccessor.getUserToken() } returns "token"
        everySuspend { mockTokenAccessor.getUserRefreshToken() } returns "refresh_token"

        val client = DefaultService(mockEngine, mockTokenAccessor, mockEnvironment, mockAppConfig)
        val authService = DefaultAuthService(client.unauthedClient)
        val unauthService = DefaultUserService(client.authedClient)

        runBlocking {
            authService.registerAnon()
            unauthService.me()

            assertTrue(mockEngine.requestHistory[0].headers["Authorization"] == null)
            assertTrue(mockEngine.requestHistory[1].headers["Authorization"] != null)
        }
    }

    @Test
    fun `assert the refresh token is called when there is no token`() {
        val requestLog = mutableListOf<Pair<String, Headers>>()

        val mockEngine = MockEngine { request ->

            requestLog += request.url.encodedPath to request.headers

            when (request.url.encodedPath) {
                "/me" -> {
                    val auth = request.headers["Authorization"]
                    if (auth?.contains("old-token") == true) {
                        respond(
                            content = "",
                            status = HttpStatusCode.Unauthorized,
                        )
                    } else {
                        respond(
                            content = "",
                            status = HttpStatusCode.OK,
                        )
                    }
                }

                "/refresh_token" -> {
                    respond(
                        content = """{"access_token":"new-access","refresh_token":"new-refresh","expires_in":3600}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type", "application/json")
                    )
                }

                else -> respondBadRequest()
            }
        }

        val client = DefaultService(mockEngine, mockTokenAccessor, mockEnvironment, mockAppConfig)
        val service = DefaultUserService(client.authedClient)

        everySuspend { mockTokenAccessor.getUserToken() } returns "old-token"
        everySuspend { mockTokenAccessor.getUserRefreshToken() } returns "initial-refresh"
        everySuspend { mockTokenAccessor.setUserToken(any()) } returns Unit
        everySuspend { mockTokenAccessor.setUserRefreshToken(any()) } returns Unit

        runBlocking {
            service.me()

            val paths = requestLog.map { it.first }
            assertEquals(listOf("/me", "/refresh_token", "/me"), paths)
        }

        verifySuspend {
            mockTokenAccessor.setUserToken(any())
            mockTokenAccessor.setUserRefreshToken(any())
        }
    }
}

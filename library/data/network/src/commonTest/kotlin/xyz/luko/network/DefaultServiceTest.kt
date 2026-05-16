package xyz.luko.network

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import xyz.luko.network.interfaces.TokenAccessor
import xyz.luko.utils.AppConfig
import xyz.luko.utils.Flavor
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DefaultServiceTest {
    private val mockTokenAccessor =
        object : TokenAccessor {
            override suspend fun getUserToken() = "access-token"

            override suspend fun getUserRefreshToken() = "refresh-token"

            override suspend fun setUserToken(token: String) {}

            override suspend fun setUserRefreshToken(refreshToken: String) {}

            override suspend fun removeUserToken() {}
        }

    private val mockBaseUrl = "https://api.mock.com"

    private val mockAppConfig =
        AppConfig(
            platform = "android",
            flavor = Flavor.DEV,
            versionName = "1.0.0",
            versionCode = "100",
        )

    private var engine: MockEngine? = null
    private var service: DefaultService? = null
    private val request: HttpRequestData
        get() = engine!!.requestHistory.first()

    @BeforeTest
    fun before() {
        engine =
            MockEngine { _ ->
                respond(
                    content = ByteReadChannel(""),
                    status = HttpStatusCode.OK,
                )
            }
        service = DefaultService(engine!!, mockTokenAccessor, mockBaseUrl, mockAppConfig)
    }

    @AfterTest
    fun after() {
        engine = null
        service = null
    }

    // --- Default headers ---

    @Test
    fun `default request contains X-Platform header`() =
        runTest {
            runCatching { service!!.unauthedClient.get("/test") }
            assertEquals("android", request.headers["X-Platform"])
        }

    @Test
    fun `default request contains App-Version header`() =
        runTest {
            runCatching { service!!.unauthedClient.get("/test") }
            assertEquals("1.0.0", request.headers["App-Version"])
        }

    @Test
    fun `default request contains App-Build header`() =
        runTest {
            runCatching { service!!.unauthedClient.get("/test") }
            assertEquals("100", request.headers["App-Build"])
        }

    @Test
    fun `default request uses correct host from environment`() =
        runTest {
            runCatching { service!!.unauthedClient.get("/test") }
            assertEquals("api.mock.com", request.url.host)
        }

    @Test
    fun `default request uses https scheme from environment`() =
        runTest {
            runCatching { service!!.unauthedClient.get("/test") }
            assertEquals(URLProtocol.HTTPS, request.url.protocol)
        }

    @Test
    fun `default request has json content type`() =
        runTest {
            runCatching { service!!.unauthedClient.get("/test") }
            assertEquals(
                ContentType.Application.Json.toString(),
                request.headers[HttpHeaders.ContentType],
            )
        }

    // --- Auth header ---

    @Test
    fun `unauthedClient does not send Authorization header`() =
        runTest {
            runCatching { service!!.unauthedClient.get("/test") }
            assertNull(request.headers[HttpHeaders.Authorization])
        }

    @Test
    fun `authedClient sends Authorization header`() =
        runTest {
            runCatching { service!!.authedClient.get("/test") }
            assertNotNull(request.headers[HttpHeaders.Authorization])
        }

    @Test
    fun `authedClient Authorization header is a Bearer token`() =
        runTest {
            runCatching { service!!.authedClient.get("/test") }
            assertEquals(request.headers[HttpHeaders.Authorization]?.startsWith("Bearer "), true)
        }
}

package xyz.luko.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CallTest {

    private fun mockClient(
        status: HttpStatusCode = HttpStatusCode.OK,
    ) = HttpClient(MockEngine { _ ->
        respond(
            content = ByteReadChannel("""string"""),
            status = status,
        )
    }) { expectSuccess = true }

    @Test
    fun `success returns body`() = runTest {
        val client = mockClient(HttpStatusCode.OK)
        val result = call<String> {
            client.get(urlString = "/fake")
        }
        assertTrue(result.isSuccess)
        assertEquals("string", result.getOrNull())
    }

    @Test
    fun `network IOException returns failure`() = runTest {
        val result = call<String> {
            throw IOException("No internet")
        }
        assertTrue(result.isFailure)
    }

    @Test
    fun `HttpRequestTimeoutException returns failure`() = runTest {
        val result = call<String> {
            throw HttpRequestTimeoutException("url", 5000L)
        }
        assertTrue(result.isFailure)
    }

    @Test
    fun `CancellationException returns failure`() = runTest {
        val result = call<String> {
            throw CancellationException("cancelled")
        }
        assertTrue(result.isFailure)
    }

    @Test
    fun `500 response throws and returns failure`() = runTest {
        val client = mockClient(HttpStatusCode.InternalServerError)
        val result = call<String> {
            client.get(urlString = "/fake")
        }
        assertTrue(result.isFailure)
    }

    @Test
    fun `401 response returns failure`() = runTest {
        val client = mockClient(HttpStatusCode.Unauthorized)
        val result = call<String> {
            client.get(urlString = "/fake")
        }
        assertTrue(result.isFailure)
    }
}
package com.louisgautier.server

import io.ktor.server.testing.testApplication
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        //
//        environment {
//            config = ApplicationConfig("application-test.conf")
//        }
//
//        val response = client.get("/")
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals("Ktor", response.bodyAsText())
    }
}
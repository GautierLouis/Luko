package com.louisgautier.server

import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.database.entity.GraphicTable
import com.louisgautier.server.database.entity.UserTable
import com.louisgautier.server.mock.testModule
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.mp.KoinPlatform.stopKoin
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @AfterTest
    fun after() {
        stopKoin()
        transaction {
            SchemaUtils.drop(DictionaryTable, GraphicTable, UserTable)
        }
    }

    @Test
    fun testRoot() = testApplication {
        environment {
            config = ApplicationConfig("application-test.conf")
        }
        application {
            module(defaultModule = testModule)
        }

        val response = client.get(urlString = "/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Ktor", response.bodyAsText())
    }
}
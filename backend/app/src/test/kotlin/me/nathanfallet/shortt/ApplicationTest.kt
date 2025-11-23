package me.nathanfallet.shortt

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testStartup() = testApplication {
        environment {
            config = ApplicationConfig("application.test.conf")
        }
        application {
            module()
        }
        val response = client.get("/healthz")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}

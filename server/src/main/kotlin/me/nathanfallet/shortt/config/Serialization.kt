package me.nathanfallet.shortt.config

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

object Serialization {
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Serialization.json)
    }
}

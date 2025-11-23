package me.nathanfallet.shortt.presentation.config

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import me.nathanfallet.shortt.api.Serialization

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Serialization.json)
    }
}

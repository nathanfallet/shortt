package me.nathanfallet.shortt

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.nathanfallet.shortt.config.configureDI
import me.nathanfallet.shortt.config.configureRouting
import me.nathanfallet.shortt.config.configureSerialization

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDI()
    configureRouting()
}

package me.nathanfallet.shortt

import io.ktor.server.application.*
import io.ktor.server.netty.*
import me.nathanfallet.shortt.config.configureDI
import me.nathanfallet.shortt.config.configureRouting
import me.nathanfallet.shortt.config.configureSerialization
import me.nathanfallet.shortt.config.configureTelemetry
import me.nathanfallet.shortt.presentation.middlewares.ErrorHandlingMiddleware

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureDI()
    configureTelemetry()
    configureRouting()
    ErrorHandlingMiddleware().install(this)
}

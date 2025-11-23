package me.nathanfallet.shortt

import io.ktor.server.application.*
import io.ktor.server.netty.*
import me.nathanfallet.shortt.domain.di.domainModule
import me.nathanfallet.shortt.infrastructure.config.configureTelemetry
import me.nathanfallet.shortt.infrastructure.di.infrastructureModule
import me.nathanfallet.shortt.presentation.config.configureErrorHandling
import me.nathanfallet.shortt.presentation.config.configureRouting
import me.nathanfallet.shortt.presentation.config.configureSerialization
import me.nathanfallet.shortt.presentation.config.configureValidation
import me.nathanfallet.shortt.presentation.di.presentationModule
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        modules(domainModule, infrastructureModule, presentationModule)
    }
    configureTelemetry()
    configureSerialization()
    configureValidation()
    configureErrorHandling()
    configureRouting()
}

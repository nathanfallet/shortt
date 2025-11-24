package me.nathanfallet.shortt.presentation.config

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import me.nathanfallet.shortt.api.requests.links.CreateLinkRequest

/**
 * Configures request validation for the Ktor application.
 */
fun Application.configureValidation() {
    install(RequestValidation) {
        validate<CreateLinkRequest> { request ->
            ValidationResult.Valid
        }
    }
}

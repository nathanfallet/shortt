package me.nathanfallet.shortt.presentation.middlewares

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import me.nathanfallet.shortt.api.requests.links.CreateLinkRequest

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<CreateLinkRequest> { request ->
            ValidationResult.Valid
        }
    }
}

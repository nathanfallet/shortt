package me.nathanfallet.shortt.presentation.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import me.nathanfallet.shortt.domain.exceptions.auth.InvalidCredentialsException
import me.nathanfallet.shortt.domain.exceptions.auth.InvalidTokenException
import me.nathanfallet.shortt.domain.exceptions.links.SlugAlreadyExistsException
import me.nathanfallet.shortt.domain.exceptions.users.UserAlreadyExistsException

fun Application.configureErrorHandling() {
    install(StatusPages) {
        // Auth
        exception<InvalidCredentialsException> { call, _ ->
            call.respond(
                HttpStatusCode.Unauthorized,
                mapOf("error" to "Invalid credentials")
            )
        }
        exception<InvalidTokenException> { call, _ ->
            call.respond(
                HttpStatusCode.Unauthorized,
                mapOf("error" to "Invalid token")
            )
        }

        // Users
        exception<UserAlreadyExistsException> { call, _ ->
            call.respond(
                HttpStatusCode.Conflict,
                mapOf("error" to "User already exists")
            )
        }

        // Links
        exception<SlugAlreadyExistsException> { call, _ ->
            call.respond(
                HttpStatusCode.Conflict,
                mapOf("error" to "Slug already exists")
            )
        }

        // Generic (500)
        exception<Throwable> { call, cause ->
            cause.printStackTrace() // for debugging purposes
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Internal server error")
            )
        }
    }
}

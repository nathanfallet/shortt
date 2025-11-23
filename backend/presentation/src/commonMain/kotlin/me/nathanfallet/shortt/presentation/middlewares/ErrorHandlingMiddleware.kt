package me.nathanfallet.shortt.presentation.middlewares

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

// Make this better?
class ErrorHandlingMiddleware {
    fun install(app: Application) {
        app.install(StatusPages) {
            /*
            exception<UserNotFoundException> { call, cause ->
                call.respond(
                    HttpStatusCode.NotFound,
                    mapOf("error" to "User not found")
                )
            }

            exception<SlugAlreadyExistsException> { call, cause ->
                call.respond(
                    HttpStatusCode.Conflict,
                    mapOf("error" to "Slug already exists")
                )
            }
            */

            exception<Throwable> { call, cause ->
                cause.printStackTrace() // for debugging purposes
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Internal server error")
                )
            }
        }
    }
}

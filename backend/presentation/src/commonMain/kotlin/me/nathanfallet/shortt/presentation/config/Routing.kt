package me.nathanfallet.shortt.presentation.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.presentation.routes.auth.authRoutes
import me.nathanfallet.shortt.presentation.routes.links.linksRoutes
import me.nathanfallet.shortt.presentation.routes.users.usersRoutes
import org.koin.ktor.ext.get

/**
 * Configures routing for the Ktor application.
 */
fun Application.configureRouting() {
    install(Resources)
    install(IgnoreTrailingSlash)
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.XRequestId)
        anyHost()
    }
    routing {
        authenticate("api-v1-jwt", optional = true) {
            authRoutes(get())
            usersRoutes(get())
            linksRoutes(get())
        }
    }
}

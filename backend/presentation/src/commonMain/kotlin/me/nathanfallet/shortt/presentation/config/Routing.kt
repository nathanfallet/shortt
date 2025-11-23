package me.nathanfallet.shortt.presentation.config

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.presentation.routes.auth.authRoutes
import me.nathanfallet.shortt.presentation.routes.links.linksRoutes
import me.nathanfallet.shortt.presentation.routes.users.usersRoutes
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    install(Resources)
    routing {
        authRoutes(get())
        usersRoutes(get())
        linksRoutes(get())
    }
}

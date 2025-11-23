package me.nathanfallet.shortt.config

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.presentation.routes.links.linksRoutes
import me.nathanfallet.shortt.presentation.routes.users.usersRoutes
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    install(Resources)
    routing {
        usersRoutes(get())
        linksRoutes(get())
    }
}

package me.nathanfallet.shortt.config

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.domain.services.UserServiceImpl
import me.nathanfallet.shortt.infrastructure.database.repositories.UserRepositoryImpl
import me.nathanfallet.shortt.presentation.routes.userRoutes

fun Application.configureRouting() {
    install(Resources)
    routing {
        userRoutes(
            // TODO: Setup dependency injection
            UserServiceImpl(UserRepositoryImpl())
        )
    }
}

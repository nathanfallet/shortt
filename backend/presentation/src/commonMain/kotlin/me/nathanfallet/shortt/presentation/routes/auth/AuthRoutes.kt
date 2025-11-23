package me.nathanfallet.shortt.presentation.routes.auth

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.api.resources.auth.AuthApi
import me.nathanfallet.shortt.domain.usecases.auth.RegisterUserUseCase

data class AuthRoutesDependencies(
    val registerUserUseCase: RegisterUserUseCase,
)

fun Route.authRoutes(dependencies: AuthRoutesDependencies) = with(dependencies) {
    get<AuthApi.Register> {
        // TODO
        val user = registerUserUseCase("name", "password")
        call.respond(user)
    }
    // TODO: More routes
}

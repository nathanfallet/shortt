package me.nathanfallet.shortt.presentation.routes.auth

import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.api.requests.auth.LoginRequest
import me.nathanfallet.shortt.api.requests.auth.RegisterRequest
import me.nathanfallet.shortt.api.resources.auth.AuthApi
import me.nathanfallet.shortt.domain.usecases.auth.LoginUserUseCase
import me.nathanfallet.shortt.domain.usecases.auth.RegisterUserUseCase
import me.nathanfallet.shortt.presentation.mappers.auth.toLoginResponse
import me.nathanfallet.shortt.presentation.mappers.auth.toRegisterResponse

/**
 * Dependencies required for authentication routes.
 */
data class AuthRoutesDependencies(
    val registerUserUseCase: RegisterUserUseCase,
    val loginUserUseCase: LoginUserUseCase,
)

/**
 * Configures authentication routes.
 */
fun Route.authRoutes(dependencies: AuthRoutesDependencies) = with(dependencies) {
    post<AuthApi.Register> {
        val request = call.receive<RegisterRequest>()
        val user = registerUserUseCase(request.username, request.password)
        call.respond(user.toRegisterResponse())
    }
    post<AuthApi.Login> {
        val request = call.receive<LoginRequest>()
        val authenticatedUser = loginUserUseCase(request.username, request.password)
        call.respond(authenticatedUser.toLoginResponse())
    }
}

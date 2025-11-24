package me.nathanfallet.shortt.presentation.routes.users

import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.api.resources.users.UsersApi
import me.nathanfallet.shortt.domain.usecases.users.GetUserByIdUseCase
import me.nathanfallet.shortt.domain.usecases.users.GetUsersUseCase

/**
 * Dependencies required for users routes.
 */
data class UsersRoutesDependencies(
    val getUsersUseCase: GetUsersUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
)

/**
 * Configures users routes.
 */
fun Route.usersRoutes(dependencies: UsersRoutesDependencies) = with(dependencies) {
    get<UsersApi> {
        val users = getUsersUseCase()
        call.respond(users)
    }
    get<UsersApi.Id> { params ->
        val userId = params.id
        val user = getUserByIdUseCase(userId) ?: run {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
            return@get
        }
        call.respond(user)
    }
}

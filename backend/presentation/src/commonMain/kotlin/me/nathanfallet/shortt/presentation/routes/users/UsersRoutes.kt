package me.nathanfallet.shortt.presentation.routes.users

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.api.requests.users.CreateUserRequest
import me.nathanfallet.shortt.api.resources.users.UsersApi
import me.nathanfallet.shortt.domain.usecases.users.CreateUserUseCase
import me.nathanfallet.shortt.domain.usecases.users.GetUserByIdUseCase
import me.nathanfallet.shortt.domain.usecases.users.GetUsersUseCase

data class UsersRoutesDependencies(
    val getUsersUseCase: GetUsersUseCase,
    val createUserUseCase: CreateUserUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
)

fun Route.usersRoutes(dependencies: UsersRoutesDependencies) = with(dependencies) {
    get<UsersApi> {
        val users = getUsersUseCase()
        call.respond(users)
    }
    post<UsersApi> {
        val request = call.receive<CreateUserRequest>()
        val user = createUserUseCase(request.name)
        call.respond(HttpStatusCode.Created, user)
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

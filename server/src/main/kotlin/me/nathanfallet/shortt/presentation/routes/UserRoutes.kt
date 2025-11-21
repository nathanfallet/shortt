package me.nathanfallet.shortt.presentation.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.domain.services.UserService
import me.nathanfallet.shortt.presentation.requests.CreateUserRequest
import me.nathanfallet.shortt.presentation.resources.Users
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Route.userRoutes(
    userService: UserService,
) {
    get<Users> {
        val users = userService.getUsers()
        call.respond(users)
    }
    post<Users> {
        val request = call.receive<CreateUserRequest>()
        val user = userService.createUser(request.name)
        call.respond(HttpStatusCode.Created, user)
    }
    get<Users.Id> { params ->
        val userId = params.id
        val user = userService.getUserById(userId) ?: run {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
            return@get
        }
        call.respond(user)
    }
}

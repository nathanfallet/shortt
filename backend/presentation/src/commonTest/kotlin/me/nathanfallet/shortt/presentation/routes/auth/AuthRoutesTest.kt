package me.nathanfallet.shortt.presentation.routes.auth

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import me.nathanfallet.shortt.api.requests.auth.LoginRequest
import me.nathanfallet.shortt.api.requests.auth.RegisterRequest
import me.nathanfallet.shortt.api.responses.auth.LoginResponse
import me.nathanfallet.shortt.api.responses.auth.RegisterResponse
import me.nathanfallet.shortt.api.responses.users.CurrentUserResponse
import me.nathanfallet.shortt.client.ApiClientImpl
import me.nathanfallet.shortt.domain.models.auth.AuthenticatedUser
import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.usecases.auth.LoginUserUseCase
import me.nathanfallet.shortt.domain.usecases.auth.RegisterUserUseCase
import me.nathanfallet.shortt.presentation.config.configureSerialization
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.Uuid

class AuthRoutesTest {
    private val user = User(Uuid.random(), "username", "hashedPassword")
    private val returnUser = CurrentUserResponse(user.id, user.username)

    @Test
    fun testPostApiAuthRegister() = testApplication {
        val registerUseCase = mockk<RegisterUserUseCase>()
        val request = RegisterRequest(user.username, "password")
        coEvery { registerUseCase(request.username, request.password) } returns user

        application {
            configureSerialization()
            install(Resources)
            routing {
                authRoutes(AuthRoutesDependencies(registerUseCase, mockk()))
            }
        }

        val client = ApiClientImpl("", clientBuilder = ::createClient)
        val response = client.auth.register(request)
        assertEquals(RegisterResponse(returnUser), response)
    }

    @Test
    fun testPostApiAuthLogin() = testApplication {
        val loginUseCase = mockk<LoginUserUseCase>()
        val request = LoginRequest(user.username, "password")
        coEvery { loginUseCase(request.username, request.password) } returns AuthenticatedUser(
            user, "accessToken", "refreshToken"
        )

        application {
            configureSerialization()
            install(Resources)
            routing {
                authRoutes(AuthRoutesDependencies(mockk(), loginUseCase))
            }
        }

        val client = ApiClientImpl("", clientBuilder = ::createClient)
        val response = client.auth.login(request)
        assertEquals(LoginResponse(returnUser, "accessToken", "refreshToken"), response)
    }
}

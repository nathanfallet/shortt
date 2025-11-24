package me.nathanfallet.shortt.client.api.auth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.shortt.api.requests.auth.LoginRequest
import me.nathanfallet.shortt.api.requests.auth.RegisterRequest
import me.nathanfallet.shortt.api.resources.auth.AuthApi
import me.nathanfallet.shortt.api.responses.auth.LoginResponse
import me.nathanfallet.shortt.api.responses.auth.RegisterResponse

/**
 * Implementation of the AuthApiClient interface using Ktor HttpClient.
 */
class AuthApiClientImpl(
    private val client: HttpClient,
) : AuthApiClient {
    override suspend fun login(request: LoginRequest): LoginResponse = client
        .post(AuthApi.Login()) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun register(request: RegisterRequest): RegisterResponse = client
        .post(AuthApi.Register()) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}

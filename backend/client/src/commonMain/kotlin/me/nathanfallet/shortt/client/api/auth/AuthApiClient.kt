package me.nathanfallet.shortt.client.api.auth

import me.nathanfallet.shortt.api.requests.auth.LoginRequest
import me.nathanfallet.shortt.api.requests.auth.RegisterRequest
import me.nathanfallet.shortt.api.responses.auth.LoginResponse
import me.nathanfallet.shortt.api.responses.auth.RegisterResponse

/**
 * Interface representing the authentication API client.
 */
interface AuthApiClient {
    /**
     * Logs in a user with the provided login request.
     */
    suspend fun login(request: LoginRequest): LoginResponse

    /**
     * Registers a new user with the provided registration request.
     */
    suspend fun register(request: RegisterRequest): RegisterResponse
}

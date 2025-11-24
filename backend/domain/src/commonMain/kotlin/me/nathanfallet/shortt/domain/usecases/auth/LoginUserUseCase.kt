package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.models.auth.AuthenticatedUser

/**
 * Use case for logging in a user.
 */
interface LoginUserUseCase {
    /**
     * Logs in a user with the given username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     *
     * @return The authenticated user.
     */
    suspend operator fun invoke(username: String, password: String): AuthenticatedUser
}

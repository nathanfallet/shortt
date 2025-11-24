package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.models.users.User

/**
 * Use case for registering a new user.
 */
interface RegisterUserUseCase {
    /**
     * Registers a new user with the given username and password.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     *
     * @return The registered user.
     */
    suspend operator fun invoke(username: String, password: String): User
}

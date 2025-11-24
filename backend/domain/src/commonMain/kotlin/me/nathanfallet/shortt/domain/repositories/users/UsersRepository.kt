package me.nathanfallet.shortt.domain.repositories.users

import me.nathanfallet.shortt.domain.models.users.User
import kotlin.uuid.Uuid

/**
 * Repository interface for managing users.
 */
interface UsersRepository {
    /**
     * Finds all users in the system.
     *
     * @return A list of all users.
     */
    suspend fun findAll(): List<User>

    /**
     * Finds a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     *
     * @return The user if found, or null if not found.
     */
    suspend fun findById(id: Uuid): User?

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user.
     *
     * @return The user if found, or null if not found.
     */
    suspend fun findByUsername(username: String): User?

    /**
     * Creates a new user.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     *
     * @return The created user.
     */
    suspend fun create(username: String, password: String): User
}

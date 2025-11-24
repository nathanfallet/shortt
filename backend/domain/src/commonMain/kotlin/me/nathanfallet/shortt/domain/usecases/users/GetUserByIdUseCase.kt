package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User
import kotlin.uuid.Uuid

/**
 * Use case for retrieving a user by their ID.
 */
interface GetUserByIdUseCase {
    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     *
     * @return The User object if found, or null if not found.
     */
    suspend operator fun invoke(id: Uuid): User?
}

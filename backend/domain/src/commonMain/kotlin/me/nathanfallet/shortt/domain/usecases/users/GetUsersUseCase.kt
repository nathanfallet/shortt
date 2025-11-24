package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User

/**
 * Use case for retrieving all users.
 */
interface GetUsersUseCase {
    /**
     * Retrieves all users.
     *
     * @return A list of all User objects.
     */
    suspend operator fun invoke(): List<User>
}

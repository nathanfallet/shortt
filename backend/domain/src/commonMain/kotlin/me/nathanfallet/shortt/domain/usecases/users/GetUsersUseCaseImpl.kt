package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository

/**
 * Implementation of the [GetUsersUseCase] interface.
 */
class GetUsersUseCaseImpl(
    private val usersRepository: UsersRepository,
) : GetUsersUseCase {
    override suspend fun invoke(): List<User> {
        return usersRepository.findAll()
    }
}

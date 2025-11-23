package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import kotlin.uuid.Uuid

class GetUserByIdUseCaseImpl(
    private val usersRepository: UsersRepository,
) : GetUserByIdUseCase {
    override suspend fun invoke(id: Uuid): User? {
        return usersRepository.findById(id)
    }
}

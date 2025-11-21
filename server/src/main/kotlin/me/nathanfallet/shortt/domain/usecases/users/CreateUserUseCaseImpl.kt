package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CreateUserUseCaseImpl(
    private val usersRepository: UsersRepository,
) : CreateUserUseCase {
    override suspend fun invoke(name: String): User {
        // TODO: Update this to have an implementation that makes sense
        return usersRepository.create(User(Uuid.random()))
    }
}

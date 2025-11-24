package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.exceptions.users.UserAlreadyExistsException
import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.PasswordEncoderService

/**
 * Implementation of [RegisterUserUseCase].
 */
class RegisterUserUseCaseImpl(
    private val repository: UsersRepository,
    private val passwordEncoderService: PasswordEncoderService,
) : RegisterUserUseCase {
    override suspend fun invoke(username: String, password: String): User {
        if (repository.findByUsername(username) != null) throw UserAlreadyExistsException()
        val encodedPassword = passwordEncoderService.encode(password)
        val user = repository.create(username, encodedPassword)
        return user
    }
}

package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.exceptions.users.UserAlreadyExistsException
import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.PasswordEncoder

/**
 * Implementation of [RegisterUserUseCase].
 */
class RegisterUserUseCaseImpl(
    private val repository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
) : RegisterUserUseCase {
    override suspend fun invoke(username: String, password: String): User {
        if (repository.findByUsername(username) != null) throw UserAlreadyExistsException()
        val encodedPassword = passwordEncoder.encode(password)
        val user = repository.create(username, encodedPassword)
        return user
    }
}

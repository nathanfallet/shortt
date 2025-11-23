package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.PasswordEncoder

class RegisterUserUseCaseImpl(
    private val repository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
) : RegisterUserUseCase {
    override suspend fun invoke(name: String, password: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        //repository.create()
        TODO("Not yet implemented")
    }
}

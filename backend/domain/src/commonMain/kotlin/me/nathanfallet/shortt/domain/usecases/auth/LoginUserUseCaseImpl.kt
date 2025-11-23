package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.exceptions.auth.InvalidCredentialsException
import me.nathanfallet.shortt.domain.models.auth.AuthenticatedUser
import me.nathanfallet.shortt.domain.models.auth.TokenType
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.PasswordEncoder
import me.nathanfallet.shortt.domain.services.TokenGenerator

class LoginUserUseCaseImpl(
    private val repository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenGenerator: TokenGenerator,
) : LoginUserUseCase {
    override suspend fun invoke(username: String, password: String): AuthenticatedUser {
        val user = repository.findByUsername(username) ?: throw InvalidCredentialsException()
        if (!passwordEncoder.matches(password, user.password)) throw InvalidCredentialsException()
        val accessToken = tokenGenerator.generateToken(user.id, TokenType.ACCESS)
        val refreshToken = tokenGenerator.generateToken(user.id, TokenType.REFRESH)
        return AuthenticatedUser(user, accessToken, refreshToken)
    }
}

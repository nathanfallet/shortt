package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.exceptions.auth.InvalidCredentialsException
import me.nathanfallet.shortt.domain.models.auth.AuthenticatedUser
import me.nathanfallet.shortt.domain.models.auth.TokenType
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.PasswordEncoderService
import me.nathanfallet.shortt.domain.services.TokenService

/**
 * Implementation of [LoginUserUseCase].
 */
class LoginUserUseCaseImpl(
    private val repository: UsersRepository,
    private val passwordEncoderService: PasswordEncoderService,
    private val tokenService: TokenService,
) : LoginUserUseCase {
    override suspend fun invoke(username: String, password: String): AuthenticatedUser {
        val user = repository.findByUsername(username) ?: throw InvalidCredentialsException()
        if (!passwordEncoderService.matches(password, user.password)) throw InvalidCredentialsException()
        val accessToken = tokenService.generateToken(user.id, TokenType.ACCESS)
        val refreshToken = tokenService.generateToken(user.id, TokenType.REFRESH)
        return AuthenticatedUser(user, accessToken, refreshToken)
    }
}

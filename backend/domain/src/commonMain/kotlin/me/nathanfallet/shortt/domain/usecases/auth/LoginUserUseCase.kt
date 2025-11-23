package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.models.auth.AuthenticatedUser

interface LoginUserUseCase {
    suspend operator fun invoke(username: String, password: String): AuthenticatedUser
}

package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.models.users.User

interface RegisterUserUseCase {
    suspend operator fun invoke(username: String, password: String): User
}

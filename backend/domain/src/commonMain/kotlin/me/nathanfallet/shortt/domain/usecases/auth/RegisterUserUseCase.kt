package me.nathanfallet.shortt.domain.usecases.auth

import me.nathanfallet.shortt.domain.models.users.User

interface RegisterUserUseCase {
    suspend operator fun invoke(name: String, password: String): User
}

package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User

interface CreateUserUseCase {
    suspend operator fun invoke(name: String): User
}

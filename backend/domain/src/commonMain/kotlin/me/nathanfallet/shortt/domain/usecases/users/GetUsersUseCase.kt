package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User

interface GetUsersUseCase {
    suspend operator fun invoke(): List<User>
}

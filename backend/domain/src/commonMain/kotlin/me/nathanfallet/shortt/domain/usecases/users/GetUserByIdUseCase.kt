package me.nathanfallet.shortt.domain.usecases.users

import me.nathanfallet.shortt.domain.models.users.User
import kotlin.uuid.Uuid

interface GetUserByIdUseCase {
    suspend operator fun invoke(id: Uuid): User?
}

package me.nathanfallet.shortt.domain.repositories.users

import me.nathanfallet.shortt.domain.models.users.User
import kotlin.uuid.Uuid

interface UsersRepository {

    suspend fun findAll(): List<User>
    suspend fun findById(id: Uuid): User?
    suspend fun create(user: User): User

}

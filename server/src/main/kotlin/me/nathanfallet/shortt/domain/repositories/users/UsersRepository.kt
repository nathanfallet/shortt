package me.nathanfallet.shortt.domain.repositories.users

import me.nathanfallet.shortt.domain.models.users.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface UsersRepository {

    suspend fun findAll(): List<User>
    suspend fun findById(id: Uuid): User?
    suspend fun create(user: User): User

}

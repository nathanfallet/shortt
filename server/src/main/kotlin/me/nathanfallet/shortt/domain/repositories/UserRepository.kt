package me.nathanfallet.shortt.domain.repositories

import me.nathanfallet.shortt.domain.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface UserRepository {

    suspend fun findAll(): List<User>
    suspend fun findById(id: Uuid): User?
    suspend fun create(user: User): User

}

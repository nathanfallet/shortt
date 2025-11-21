package me.nathanfallet.shortt.domain.services

import me.nathanfallet.shortt.domain.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface UserService {

    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Uuid): User?
    suspend fun createUser(name: String): User

}

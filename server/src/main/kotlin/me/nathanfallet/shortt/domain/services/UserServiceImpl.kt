package me.nathanfallet.shortt.domain.services

import me.nathanfallet.shortt.domain.models.User
import me.nathanfallet.shortt.domain.repositories.UserRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override suspend fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override suspend fun getUserById(id: Uuid): User? {
        return userRepository.findById(id)
    }

    override suspend fun createUser(name: String): User {
        TODO("Not yet implemented")
    }

}

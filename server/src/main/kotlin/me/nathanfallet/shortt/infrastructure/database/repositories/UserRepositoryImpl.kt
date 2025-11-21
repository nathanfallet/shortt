package me.nathanfallet.shortt.infrastructure.database.repositories

import me.nathanfallet.shortt.domain.models.User
import me.nathanfallet.shortt.domain.repositories.UserRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UserRepositoryImpl(

) : UserRepository {

    // TODO: Setup real database
    private val users = hashMapOf<String, User>()

    override suspend fun findAll(): List<User> {
        return users.values.toList()
    }

    override suspend fun findById(id: Uuid): User? {
        return users[id.toString()]
    }

    override suspend fun create(user: User): User {
        users[user.id.toString()] = user
        return user
    }

}

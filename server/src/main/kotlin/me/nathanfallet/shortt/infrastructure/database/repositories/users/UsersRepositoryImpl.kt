package me.nathanfallet.shortt.infrastructure.database.repositories.users

import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UsersRepositoryImpl(

) : UsersRepository {

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

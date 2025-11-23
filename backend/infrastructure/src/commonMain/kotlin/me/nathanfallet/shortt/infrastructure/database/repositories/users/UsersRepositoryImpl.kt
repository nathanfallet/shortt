package me.nathanfallet.shortt.infrastructure.database.repositories.users

import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.infrastructure.database.TransactionManager
import me.nathanfallet.shortt.infrastructure.database.tables.users.UsersTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

class UsersRepositoryImpl(
    private val transactionManager: TransactionManager,
) : UsersRepository {

    init {
        transactionManager.transaction {
            SchemaUtils.create(UsersTable)
        }
    }

    override suspend fun findAll(): List<User> =
        transactionManager.suspendTransaction {
            UsersTable
                .selectAll()
                .map(UsersTable::toUser)
        }

    override suspend fun findById(id: Uuid): User? =
        transactionManager.suspendTransaction {
            UsersTable
                .selectAll()
                .where { UsersTable.id eq id.toJavaUuid() }
                .map(UsersTable::toUser)
                .firstOrNull()
        }

    override suspend fun findByUsername(username: String): User? =
        transactionManager.suspendTransaction {
            UsersTable
                .selectAll()
                .where { UsersTable.username eq username }
                .map(UsersTable::toUser)
                .firstOrNull()
        }

    override suspend fun create(username: String, password: String): User =
        transactionManager.suspendTransaction {
            UsersTable.insert {
                it[UsersTable.username] = username
                it[UsersTable.password] = password
            }.resultedValues!!.map(UsersTable::toUser).first()
        }

}

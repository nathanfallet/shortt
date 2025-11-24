package me.nathanfallet.shortt.infrastructure.database.tables.users

import me.nathanfallet.shortt.domain.models.users.User
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import kotlin.uuid.toKotlinUuid

/**
 * Table object representing the "users" table in the database.
 */
object UsersTable : UUIDTable("users") {
    /**
     * The username of the user.
     */
    val username = varchar("username", 255)

    /**
     * The password of the user.
     */
    val password = varchar("password", 255)

    /**
     * Maps a database row to a [User] domain model.
     *
     * @param row The database row to map.
     *
     * @return The mapped [User] object.
     */
    fun toUser(
        row: ResultRow,
    ) = User(
        row[id].value.toKotlinUuid(),
        row[username],
        row[password],
    )
}

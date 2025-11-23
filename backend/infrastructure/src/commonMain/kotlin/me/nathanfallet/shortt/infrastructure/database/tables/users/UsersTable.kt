package me.nathanfallet.shortt.infrastructure.database.tables.users

import me.nathanfallet.shortt.domain.models.users.User
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import kotlin.uuid.toKotlinUuid

object UsersTable : UUIDTable("users") {

    fun toUser(
        row: ResultRow,
    ) = User(
        row[id].value.toKotlinUuid(),
    )

}

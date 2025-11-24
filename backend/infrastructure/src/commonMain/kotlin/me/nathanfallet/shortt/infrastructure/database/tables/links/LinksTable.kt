package me.nathanfallet.shortt.infrastructure.database.tables.links

import me.nathanfallet.shortt.domain.models.links.Link
import me.nathanfallet.shortt.infrastructure.database.tables.users.UsersTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import kotlin.uuid.toKotlinUuid

object LinksTable : UUIDTable("links") {

    val userId = reference("user_id", UsersTable.id)
    val url = text("url")
    val slug = varchar("slug", 255).uniqueIndex()

    fun toLink(
        row: ResultRow,
    ) = Link(
        row[id].value.toKotlinUuid(),
        row[userId].value.toKotlinUuid(),
        row[url],
        row[slug],
    )

}

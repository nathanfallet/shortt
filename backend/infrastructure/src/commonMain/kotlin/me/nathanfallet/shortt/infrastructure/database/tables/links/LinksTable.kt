package me.nathanfallet.shortt.infrastructure.database.tables.links

import me.nathanfallet.shortt.domain.models.links.Link
import me.nathanfallet.shortt.infrastructure.database.tables.users.UsersTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import kotlin.uuid.toKotlinUuid

/**
 * Table object representing the "links" table in the database.
 */
object LinksTable : UUIDTable("links") {
    /**
     * Reference to the user who created the link.
     */
    val userId = reference("user_id", UsersTable.id)

    /**
     * The original URL that is being shortened.
     */
    val url = text("url")

    /**
     * The unique slug for the shortened link.
     */
    val slug = varchar("slug", 255).uniqueIndex()

    /**
     * Maps a database row to a [Link] domain model.
     *
     * @param row The database row to map.
     *
     * @return The mapped [Link] object.
     */
    fun toLink(
        row: ResultRow,
    ) = Link(
        row[id].value.toKotlinUuid(),
        row[userId].value.toKotlinUuid(),
        row[url],
        row[slug],
    )
}

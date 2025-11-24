package me.nathanfallet.shortt.infrastructure.database.repositories.links

import me.nathanfallet.shortt.domain.models.links.Link
import me.nathanfallet.shortt.domain.repositories.links.LinksRepository
import me.nathanfallet.shortt.infrastructure.database.TransactionManager
import me.nathanfallet.shortt.infrastructure.database.tables.links.LinksTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

/**
 * Implementation of the [LinksRepository] interface using Exposed framework.
 */
class LinksRepositoryImpl(
    private val transactionManager: TransactionManager,
) : LinksRepository {

    init {
        transactionManager.transaction {
            SchemaUtils.create(LinksTable)
        }
    }

    override suspend fun findByUserId(userId: Uuid): List<Link> =
        transactionManager.suspendTransaction {
            LinksTable
                .selectAll()
                .where { LinksTable.userId eq userId.toJavaUuid() }
                .map(LinksTable::toLink)
        }

    override suspend fun findById(id: Uuid): Link? =
        transactionManager.suspendTransaction {
            LinksTable
                .selectAll()
                .where { LinksTable.id eq id.toJavaUuid() }
                .map(LinksTable::toLink)
                .firstOrNull()
        }

    override suspend fun create(url: String, slug: String, userId: Uuid): Link =
        transactionManager.suspendTransaction {
            LinksTable.insert {
                it[LinksTable.url] = url
                it[LinksTable.slug] = slug
                it[LinksTable.userId] = userId.toJavaUuid()
            }.resultedValues!!.map(LinksTable::toLink).first()
        }

}

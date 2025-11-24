package me.nathanfallet.shortt.infrastructure.database

import io.opentelemetry.context.Context
import io.opentelemetry.extension.kotlin.asContextElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.Transaction

/**
 * Implementation of [TransactionManager] using Exposed framework.
 */
class TransactionManagerImpl(
    /**
     * The database factory to get the database instance.
     */
    private val databaseFactory: DatabaseFactory,
) : TransactionManager {

    override fun <T> transaction(statement: Transaction.() -> T): T =
        org.jetbrains.exposed.v1.jdbc.transactions.transaction(databaseFactory.getDatabase()) {
            statement()
        }

    override suspend fun <T> suspendTransaction(statement: suspend Transaction.() -> T): T =
        withContext(Dispatchers.IO + Context.current().asContextElement()) {
            org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction(databaseFactory.getDatabase()) {
                statement()
            }
        }

}

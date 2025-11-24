package me.nathanfallet.shortt.infrastructure.database

import org.jetbrains.exposed.v1.core.Transaction

/**
 * Interface for managing database transactions.
 */
interface TransactionManager {
    /**
     * Executes a block of code within a database transaction.
     */
    fun <T> transaction(statement: Transaction.() -> T): T

    /**
     * Executes a block of code within a suspendable database transaction.
     */
    suspend fun <T> suspendTransaction(statement: suspend Transaction.() -> T): T
}

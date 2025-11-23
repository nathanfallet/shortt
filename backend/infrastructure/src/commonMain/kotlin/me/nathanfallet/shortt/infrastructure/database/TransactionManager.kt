package me.nathanfallet.shortt.infrastructure.database

import org.jetbrains.exposed.v1.core.Transaction

interface TransactionManager {

    fun <T> transaction(statement: Transaction.() -> T): T
    suspend fun <T> suspendTransaction(statement: suspend Transaction.() -> T): T

}

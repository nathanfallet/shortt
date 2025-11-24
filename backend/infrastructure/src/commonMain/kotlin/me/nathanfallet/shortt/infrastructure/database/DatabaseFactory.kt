package me.nathanfallet.shortt.infrastructure.database

import org.jetbrains.exposed.v1.jdbc.Database

/**
 * Factory interface for obtaining a Database instance.
 */
interface DatabaseFactory {
    /**
     * Gets the Database instance.
     */
    fun getDatabase(): Database
}

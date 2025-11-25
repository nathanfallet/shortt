package me.nathanfallet.shortt.infrastructure.database

import org.jetbrains.exposed.v1.jdbc.Database

/**
 * H2 Database Factory implementation.
 */
class H2DatabaseFactory(
    /**
     * The name of the in-memory H2 database.
     */
    name: String,
) : DatabaseFactory {
    private val db: Database by lazy {
        Database.connect(
            "jdbc:h2:mem:$name;DB_CLOSE_DELAY=-1;", "org.h2.Driver"
        )
    }

    override fun getDatabase(): Database = db
}

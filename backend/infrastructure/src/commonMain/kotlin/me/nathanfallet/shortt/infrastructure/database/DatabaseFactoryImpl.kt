package me.nathanfallet.shortt.infrastructure.database

import org.jetbrains.exposed.v1.jdbc.Database

class DatabaseFactoryImpl : DatabaseFactory {

    private val db: Database by lazy {
        // TODO: Update
        Database.connect(
            url = System.getenv("DATABASE_URL"),
            driver = "org.postgresql.Driver",
            user = System.getenv("DATABASE_USER"),
            password = System.getenv("DATABASE_PASSWORD"),
        )
    }

    override fun getDatabase(): Database = db

}

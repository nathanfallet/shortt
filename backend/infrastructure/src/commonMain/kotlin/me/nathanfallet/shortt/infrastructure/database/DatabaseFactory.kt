package me.nathanfallet.shortt.infrastructure.database

import org.jetbrains.exposed.v1.jdbc.Database

interface DatabaseFactory {

    fun getDatabase(): Database

}

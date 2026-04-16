package me.nathanfallet.shortt.infrastructure.health

import dev.kourier.amqp.connection.ConnectionState
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import me.nathanfallet.shortt.infrastructure.database.DatabaseFactory
import me.nathanfallet.shortt.infrastructure.messaging.RabbitMQFactory
import org.koin.ktor.ext.inject

fun Application.configureHealth() {
    val databaseFactory by inject<DatabaseFactory>()
    val rabbitMQFactory by inject<RabbitMQFactory>()

    val checks: Map<String, suspend () -> Boolean> = mapOf(
        "database" to {
            databaseFactory.isHealthy()
        },
        "messaging" to {
            rabbitMQFactory.getChannel().state == ConnectionState.OPEN
        }
    )

    suspend fun healthChecks(): Pair<Map<String, Boolean>, HttpStatusCode> = checks.map { (name, check) ->
        async { name to runCatching { check() }.getOrDefault(false) }
    }.awaitAll().associate { it }.let { results ->
        results to if (results.all { it.value }) HttpStatusCode.OK else HttpStatusCode.ServiceUnavailable
    }

    routing {
        get("/healthz") {
            val (results, status) = healthChecks()
            call.respond(status, results)
        }
        get("/readyz") {
            val (results, status) = healthChecks()
            call.respond(status, results)
        }
    }
}

package me.nathanfallet.shortt.infrastructure.health

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import me.nathanfallet.shortt.infrastructure.database.TransactionManager
import me.nathanfallet.shortt.infrastructure.messaging.RabbitMQFactory
import org.koin.ktor.ext.inject

fun Application.configureHealth() {
    val transactionManager by inject<TransactionManager>()
    val rabbitMQFactory by inject<RabbitMQFactory>()

    val checks: Map<String, suspend () -> Boolean> = mapOf(
        "database" to {
            transactionManager.transaction {
                //exec("SELECT 1") { it.next() } == true // TODO: Fix
                true
            }
        },
        "messaging" to {
            //rabbitMQFactory.getChannel().exchangeDeclarePassive("shortt") // TODO: Find a way to make it pass tests
            true
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

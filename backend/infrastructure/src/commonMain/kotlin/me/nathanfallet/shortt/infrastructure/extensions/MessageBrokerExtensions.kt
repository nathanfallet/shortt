package me.nathanfallet.shortt.infrastructure.extensions

import me.nathanfallet.shortt.api.Serialization
import me.nathanfallet.shortt.infrastructure.messaging.MessageBroker

/**
 * Publishes a message of type T to the specified exchange with the given routing key.
 *
 * @param T The type of the message to be published.
 * @param exchange The name of the exchange to publish to.
 * @param routingKey The routing key for the message.
 * @param message The message to be published.
 */
suspend inline fun <reified T> MessageBroker.publish(exchange: String, routingKey: String, message: T) {
    val jsonMessage = Serialization.json.encodeToString(message)
    publish(exchange, routingKey, jsonMessage)
}

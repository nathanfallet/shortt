package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.Field

/**
 * Interface for a message broker that supports publishing and consuming messages.
 */
interface MessageBroker {
    /**
     * Publishes a message to the specified exchange with the given routing key.
     *
     * @param exchange The name of the exchange to publish to.
     * @param routingKey The routing key for the message.
     * @param message The message to be published.
     * @param headers Optional headers to include with the message.
     */
    suspend fun publish(exchange: String, routingKey: String, message: String, headers: Map<String, Field>? = null)

    /**
     * Starts consuming messages from the specified queue using the provided message handler.
     *
     * @param queue The name of the queue to consume from.
     * @param handler The message handler to process incoming messages.
     */
    suspend fun startConsuming(queue: String, handler: MessageHandler)
}

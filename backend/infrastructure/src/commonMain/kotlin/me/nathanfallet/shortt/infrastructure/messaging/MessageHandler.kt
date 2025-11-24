package me.nathanfallet.shortt.infrastructure.messaging

/**
 * Interface for handling incoming messages from a message broker.
 */
interface MessageHandler {
    /**
     * Handles the incoming message.
     *
     * @param routingKey The routing key of the message.
     * @param body The body of the message.
     */
    suspend operator fun invoke(routingKey: String, body: String)
}

package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.AMQPResponse
import dev.kourier.amqp.channel.AMQPChannel

/**
 * Interface for handling incoming messages from a message broker.
 */
interface MessageHandler {
    /**
     * Handles the incoming message.
     *
     * @param channel The AMQP channel through which the message was received.
     * @param delivery The delivery information of the message.
     *
     * @return The result of handling the message.
     */
    suspend operator fun invoke(
        channel: AMQPChannel,
        delivery: AMQPResponse.Channel.Message.Delivery,
    ): MessageHandlerResult
}

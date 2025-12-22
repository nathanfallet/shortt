package me.nathanfallet.shortt.infrastructure.messaging.handlers

import dev.kourier.amqp.AMQPResponse
import dev.kourier.amqp.channel.AMQPChannel
import me.nathanfallet.shortt.infrastructure.extensions.handleWithRetryAndDead
import me.nathanfallet.shortt.infrastructure.messaging.MessageHandler
import me.nathanfallet.shortt.infrastructure.messaging.MessageHandlerResult

class LinkExpiredHandler(

) : MessageHandler {
    override suspend fun invoke(
        channel: AMQPChannel,
        delivery: AMQPResponse.Channel.Message.Delivery,
    ): MessageHandlerResult = channel.handleWithRetryAndDead(
        delivery = delivery,
        maxXDeathCount = 5,
        dead = true,
    ) {
        val body = delivery.message.body.decodeToString()

        // Implement handling logic here

        MessageHandlerResult.Success
    }
}

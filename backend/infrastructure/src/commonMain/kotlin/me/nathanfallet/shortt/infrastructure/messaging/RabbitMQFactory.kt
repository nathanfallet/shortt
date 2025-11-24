package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.channel.AMQPChannel

/**
 * Factory interface for obtaining an AMQPChannel instance.
 */
interface RabbitMQFactory {
    /**
     * Gets the AMQPChannel instance.
     *
     * @return AMQPChannel instance
     */
    fun getChannel(): AMQPChannel
}

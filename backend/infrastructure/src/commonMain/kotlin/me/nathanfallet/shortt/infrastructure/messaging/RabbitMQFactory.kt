package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.channel.AMQPChannel

/**
 * Factory interface for obtaining an AMQPChannel instance.
 */
interface RabbitMQFactory {
    /**
     * Initializes the message broker connection and any necessary resources.
     */
    suspend fun initialize()
    
    /**
     * Gets the AMQPChannel instance.
     *
     * @return AMQPChannel instance
     */
    fun getChannel(): AMQPChannel
}

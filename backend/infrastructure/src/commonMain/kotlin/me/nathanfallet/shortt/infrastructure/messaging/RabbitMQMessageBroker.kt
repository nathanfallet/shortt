package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.Field
import dev.kourier.amqp.properties
import me.nathanfallet.shortt.infrastructure.extensions.mapOfRequestId
import me.nathanfallet.shortt.infrastructure.extensions.withCallId

class RabbitMQMessageBroker(
    private val rabbitMQFactory: RabbitMQFactory,
) : MessageBroker {
    override suspend fun publish(exchange: String, routingKey: String, message: String, headers: Map<String, Field>?) {
        val mapOfRequestId = mapOfRequestId()
        rabbitMQFactory.getChannel().basicPublish(
            body = message.toByteArray(),
            exchange = exchange,
            routingKey = routingKey,
            properties = properties {
                deliveryMode = 2u
                this@properties.headers = (headers ?: emptyMap()) + mapOfRequestId
            },
        )
    }

    override suspend fun startConsuming(queue: String, handler: MessageHandler) {
        val channel = rabbitMQFactory.getChannel()
        channel.basicConsume(
            queue = queue,
            noAck = false,
            onDelivery = { delivery ->
                delivery.withCallId {
                    when (val result = handler(channel, delivery)) {
                        is MessageHandlerResult.Success -> channel.basicAck(delivery.message.deliveryTag)
                        is MessageHandlerResult.Failure -> channel.basicNack(
                            delivery.message.deliveryTag,
                            requeue = result.requeue
                        )
                    }
                }
            }
        )
    }
}

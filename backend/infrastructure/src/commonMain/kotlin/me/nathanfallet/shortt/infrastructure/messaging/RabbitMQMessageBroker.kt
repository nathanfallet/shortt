package me.nathanfallet.shortt.infrastructure.messaging

class RabbitMQMessageBroker(
    private val rabbitMQFactory: RabbitMQFactory,
) : MessageBroker {
    override suspend fun publish(exchange: String, routingKey: String, message: String) {
        // Maybe add properties?
        rabbitMQFactory.getChannel().basicPublish(
            body = message.toByteArray(),
            exchange = exchange,
            routingKey = routingKey,
        )
    }

    override suspend fun startConsuming(queue: String, handler: MessageHandler) {
        rabbitMQFactory.getChannel().basicConsume(
            queue = queue,
            noAck = false,
            onDelivery = { delivery ->
                // Error management?
                handler(delivery.message.routingKey, delivery.message.body.decodeToString())
            }
        )
    }
}

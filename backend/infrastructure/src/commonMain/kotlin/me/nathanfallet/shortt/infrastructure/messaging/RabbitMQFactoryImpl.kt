package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.BuiltinExchangeType
import dev.kourier.amqp.channel.AMQPChannel
import dev.kourier.amqp.channel.queueBind
import dev.kourier.amqp.connection.AMQPConnection
import dev.kourier.amqp.opentelemetry.withTracing
import dev.kourier.amqp.robust.createRobustAMQPConnection
import kotlinx.coroutines.CoroutineScope
import me.nathanfallet.shortt.infrastructure.extensions.exchangeDeclareWithAdditionalResources
import me.nathanfallet.shortt.infrastructure.extensions.queueDeclareWithAdditionalResources
import me.nathanfallet.shortt.infrastructure.observability.TelemetryFactory

/**
 * Implementation of RabbitMQFactory to provide AMQPChannel instances.
 */
class RabbitMQFactoryImpl(
    private val coroutineScope: CoroutineScope,
    private val host: String,
    private val port: Int,
    private val user: String,
    private val password: String,
    private val telemetryFactory: TelemetryFactory,
) : RabbitMQFactory {
    private lateinit var amqpConnection: AMQPConnection
    private lateinit var amqpChannel: AMQPChannel

    override suspend fun initialize() {
        amqpConnection = createRobustAMQPConnection(coroutineScope) {
            server {
                host = this@RabbitMQFactoryImpl.host
                port = this@RabbitMQFactoryImpl.port
                user = this@RabbitMQFactoryImpl.user
                password = this@RabbitMQFactoryImpl.password
            }
        }.withTracing(telemetryFactory.getTracer())

        amqpChannel = amqpConnection.openChannel().apply {
            basicQos(10u)

            exchangeDeclareWithAdditionalResources(
                dlx = true,
                dead = true,
            ) {
                name = "shortt"
                type = BuiltinExchangeType.TOPIC
                durable = true
            }
            queueDeclareWithAdditionalResources(
                exchange = "shortt",
                dlx = true,
                quorum = true,
            ) {
                name = "shortt-queue"
                durable = true
            }

            queueBind {
                queue = "shortt-queue"
                exchange = "shortt"
                routingKey = "#"
            }
        }
    }

    override fun getChannel(): AMQPChannel = amqpChannel
}

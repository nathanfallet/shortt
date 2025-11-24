package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.channel.AMQPChannel
import dev.kourier.amqp.opentelemetry.withTracing
import dev.kourier.amqp.robust.createRobustAMQPConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
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
    private val aMQPConnection by lazy {
        runBlocking { // TODO: See if we can avoid runBlocking here
            createRobustAMQPConnection(coroutineScope) {
                server {
                    host = this@RabbitMQFactoryImpl.host
                    port = this@RabbitMQFactoryImpl.port
                    user = this@RabbitMQFactoryImpl.user
                    password = this@RabbitMQFactoryImpl.password
                }
            }.withTracing(telemetryFactory.getTracer())
        }
    }

    private val amqpChannel by lazy {
        runBlocking { // TODO: See if we can avoid runBlocking here
            aMQPConnection.openChannel()
        }
    }

    override fun getChannel(): AMQPChannel = amqpChannel
}

package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.AMQPResponse
import dev.kourier.amqp.Field
import dev.kourier.amqp.channel.AMQPChannel
import dev.kourier.amqp.properties
import io.ktor.util.logging.*
import kotlin.coroutines.cancellation.CancellationException
import me.nathanfallet.shortt.infrastructure.extensions.mapOfRequestId
import me.nathanfallet.shortt.infrastructure.extensions.withCallId

private val logger = KtorSimpleLogger("RabbitMQMessageBroker")

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
                // Last line of defence: a delivery that leaves this block unsettled is never
                // redelivered while the connection lives, and holds a prefetch slot forever. Fill the
                // prefetch with those and the consumer stops draining the queue entirely.
                var settled = false
                try {
                    delivery.withCallId {
                        when (val result = handler(channel, delivery)) {
                            is MessageHandlerResult.Success -> {
                                settled = true
                                channel.basicAck(delivery.message.deliveryTag)
                            }

                            is MessageHandlerResult.Failure -> {
                                settled = true
                                channel.basicNack(
                                    delivery.message.deliveryTag,
                                    requeue = result.requeue
                                )
                            }
                        }
                    }
                } catch (ce: CancellationException) {
                    // Shutdown, not a bad message. The broker redelivers everything unacknowledged
                    // once the channel drops, and nacking on a dying channel would fail anyway.
                    throw ce
                } catch (e: Throwable) {
                    logger.error(
                        "Unhandled failure while delivering ${delivery.message.exchange}/" +
                                "${delivery.message.routingKey} (tag ${delivery.message.deliveryTag})",
                        e,
                    )
                    // `settled` guards against settling twice: if the ack/nack above is what threw,
                    // we do not know whether the broker saw it, and a second settle on the same tag
                    // is a channel-level error that closes the channel and drops every other
                    // in-flight delivery with it. Leave this one to the post-reconnect redelivery.
                    if (!settled) settleUnhandled(channel, delivery)
                }
            }
        )
    }

    /**
     * Rejects a delivery whose handling failed in a way nothing else caught, routing it to the
     * dead-letter exchange rather than back onto the queue it came from.
     *
     * Requeueing would put the message straight back at the head of the same queue with its
     * `x-death` count untouched — the infinite loop this exists to remove.
     *
     * If the rejection itself fails there is nothing left to try: the channel is already gone, and
     * the broker will redeliver the message once the connection is re-established.
     */
    private suspend fun settleUnhandled(
        channel: AMQPChannel,
        delivery: AMQPResponse.Channel.Message.Delivery,
    ) {
        try {
            channel.basicNack(delivery.message.deliveryTag, requeue = false)
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Throwable) {
            logger.error(
                "Could not reject delivery ${delivery.message.deliveryTag}; " +
                        "leaving it to the broker to redeliver after reconnect",
                e,
            )
        }
    }
}

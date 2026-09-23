package me.nathanfallet.shortt.infrastructure.messaging

import dev.kourier.amqp.AMQPMessage
import dev.kourier.amqp.AMQPResponse
import dev.kourier.amqp.Properties
import dev.kourier.amqp.channel.AMQPChannel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertFailsWith

/**
 * The delivery callback is the last place that can settle a message. Whatever happens inside it, the
 * delivery must end up either acknowledged or rejected: an unsettled delivery holds a prefetch slot
 * for the life of the connection, and once the prefetch is full the consumer stops pulling anything
 * from that queue at all.
 */
class RabbitMQMessageBrokerTest {

    private val delivery = AMQPResponse.Channel.Message.Delivery(
        message = AMQPMessage(
            exchange = "jobs",
            routingKey = "jobs.default",
            deliveryTag = 42uL,
            properties = Properties(),
            redelivered = false,
            body = "{}".encodeToByteArray(),
        ),
        consumerTag = "test",
    )

    /**
     * Starts consuming against a mocked channel and hands back the callback the broker registered,
     * so a delivery can be pushed through exactly as the AMQP client would push it.
     */
    private suspend fun captureOnDelivery(
        channel: AMQPChannel,
        handler: MessageHandler,
    ): suspend (AMQPResponse.Channel.Message.Delivery) -> Unit {
        val onDelivery = slot<suspend (AMQPResponse.Channel.Message.Delivery) -> Unit>()
        coEvery {
            channel.basicConsume(
                queue = any(),
                consumerTag = any(),
                noAck = any(),
                exclusive = any(),
                arguments = any(),
                onDelivery = capture(onDelivery),
                onCanceled = any(),
            )
        } returns AMQPResponse.Channel.Basic.ConsumeOk("consumer-tag")

        val factory = mockk<RabbitMQFactory>()
        every { factory.getChannel() } returns channel

        RabbitMQMessageBroker(factory).startConsuming("jobs.default", handler)
        return onDelivery.captured
    }

    private fun handlerThrowing(throwable: Throwable) = object : MessageHandler {
        override suspend fun invoke(
            channel: AMQPChannel,
            delivery: AMQPResponse.Channel.Message.Delivery,
        ): MessageHandlerResult = throw throwable
    }

    @Test
    fun `an Error raised by the handler still settles the delivery`() = runTest {
        val channel = mockk<AMQPChannel>(relaxed = true)

        captureOnDelivery(channel, handlerThrowing(IllegalAccessError("boom")))(delivery)

        // Rejected towards the dead-letter exchange, not requeued: requeueing puts the message back
        // at the head of the same queue with its x-death count untouched, which loops forever.
        coVerify(exactly = 1) { channel.basicNack(42uL, multiple = false, requeue = false) }
        coVerify(exactly = 0) { channel.basicAck(any<ULong>(), any<Boolean>()) }
    }

    @Test
    fun `a delivery is never settled twice when the acknowledgement itself throws`() = runTest {
        val channel = mockk<AMQPChannel>(relaxed = true)
        coEvery { channel.basicAck(any<ULong>(), any<Boolean>()) } throws IllegalStateException("channel closed")

        val handler = object : MessageHandler {
            override suspend fun invoke(
                channel: AMQPChannel,
                delivery: AMQPResponse.Channel.Message.Delivery,
            ): MessageHandlerResult = MessageHandlerResult.Success
        }
        captureOnDelivery(channel, handler)(delivery)

        // A second settle on the same tag is a channel-level error: it closes the channel and takes
        // every other in-flight delivery down with it. This one is left to the redelivery that
        // follows the reconnect.
        coVerify(exactly = 0) { channel.basicNack(any<ULong>(), any<Boolean>(), any<Boolean>()) }
    }

    @Test
    fun `cancellation propagates instead of being turned into a rejection`() = runTest {
        val channel = mockk<AMQPChannel>(relaxed = true)
        val onDelivery = captureOnDelivery(channel, handlerThrowing(CancellationException("shutting down")))

        assertFailsWith<CancellationException> { onDelivery(delivery) }

        // Shutdown, not a bad message: the broker redelivers whatever is unacknowledged once the
        // channel drops, and nacking on a dying channel would fail anyway.
        coVerify(exactly = 0) { channel.basicNack(any<ULong>(), any<Boolean>(), any<Boolean>()) }
        coVerify(exactly = 0) { channel.basicAck(any<ULong>(), any<Boolean>()) }
    }
}

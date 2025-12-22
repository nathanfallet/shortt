package me.nathanfallet.shortt.infrastructure.extensions

import dev.kourier.amqp.AMQPResponse
import dev.kourier.amqp.BuiltinExchangeType
import dev.kourier.amqp.Field
import dev.kourier.amqp.channel.AMQPChannel
import dev.kourier.amqp.channel.exchangeDeclare
import dev.kourier.amqp.channel.queueBind
import dev.kourier.amqp.channel.queueDeclare
import dev.kourier.amqp.states.DeclaredExchangeBuilder
import dev.kourier.amqp.states.DeclaredQueueBuilder
import dev.kourier.amqp.states.declaredExchange
import dev.kourier.amqp.states.declaredQueue
import io.ktor.callid.*
import io.ktor.http.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.datetime.Clock
import me.nathanfallet.shortt.api.Serialization
import me.nathanfallet.shortt.infrastructure.messaging.MessageBroker
import me.nathanfallet.shortt.infrastructure.messaging.MessageHandlerResult

/**
 * Publishes a message of type T to the specified exchange with the given routing key.
 *
 * @param T The type of the message to be published.
 * @param exchange The name of the exchange to publish to.
 * @param routingKey The routing key for the message.
 * @param message The message to be published.
 */
suspend inline fun <reified T> MessageBroker.publish(exchange: String, routingKey: String, message: T) {
    val jsonMessage = Serialization.json.encodeToString(message)
    publish(exchange, routingKey, jsonMessage)
}

/**
 * Declares an exchange along with additional resources such as dead-letter exchanges and queues.
 *
 * @param block A lambda to configure the declared exchange.
 * @param dlx Whether to create a dead-letter exchange and queue.
 * @param dead Whether to create a dead-letter exchange and queue.
 */
suspend fun AMQPChannel.exchangeDeclareWithAdditionalResources(
    dlx: Boolean = true,
    dead: Boolean = true,
    block: DeclaredExchangeBuilder.() -> Unit,
) {
    val declaredExchange = declaredExchange(block)
    exchangeDeclare(declaredExchange)

    if (dlx) {
        exchangeDeclare {
            name = "${declaredExchange.name}-dlx"
            type = BuiltinExchangeType.FANOUT
            durable = true
        }
        queueDeclare {
            name = "${declaredExchange.name}-dlx"
            durable = true
            arguments = mapOf(
                "x-dead-letter-exchange" to Field.LongString(declaredExchange.name),
                "x-message-ttl" to Field.Int(5000),
            )
        }
        queueBind {
            queue = "${declaredExchange.name}-dlx"
            exchange = "${declaredExchange.name}-dlx"
            routingKey = "#"
        }
    }
    if (dead) {
        exchangeDeclare {
            name = "${declaredExchange.name}-dead"
            type = BuiltinExchangeType.FANOUT
            durable = true
        }
        queueDeclare {
            name = "${declaredExchange.name}-dead"
            durable = true
        }
        queueBind {
            queue = "${declaredExchange.name}-dead"
            exchange = "${declaredExchange.name}-dead"
            routingKey = "#"
        }
    }
}

/**
 * Declares a queue along with additional resources such as dead-letter exchanges and quorum settings.
 *
 * @param exchange The name of the exchange to bind the dead-letter exchange to.
 * @param dlx Whether to create a dead-letter exchange and queue.
 * @param quorum Whether to create the queue as a quorum queue.
 * @param block A lambda to configure the declared queue.
 */
suspend fun AMQPChannel.queueDeclareWithAdditionalResources(
    exchange: String,
    dlx: Boolean = true,
    quorum: Boolean = false,
    block: DeclaredQueueBuilder.() -> Unit,
) {
    val declaredQueue = declaredQueue(block)

    val dlxArguments =
        if (dlx) mapOf("x-dead-letter-exchange" to Field.LongString("${exchange}-dlx"))
        else emptyMap()
    val quorumArguments =
        if (quorum && declaredQueue.durable && !declaredQueue.exclusive) mapOf("x-queue-type" to Field.LongString("quorum"))
        else emptyMap()

    queueDeclare(declaredQueue.copy(arguments = declaredQueue.arguments + dlxArguments + quorumArguments))
}

/**
 * Handles a message delivery with retry logic and dead-letter queue support.
 *
 * @param delivery The message delivery to be handled.
 * @param maxXDeathCount The maximum number of retries before sending to the dead-letter queue.
 * @param dead Whether to send the message to the dead-letter queue after exceeding retries.
 * @param block The block of code to execute for handling the message.
 *
 * @return The result of the message handling.
 */
suspend inline fun AMQPChannel.handleWithRetryAndDead(
    delivery: AMQPResponse.Channel.Message.Delivery,
    maxXDeathCount: Int = 5,
    dead: Boolean = true,
    block: () -> MessageHandlerResult,
): MessageHandlerResult {
    return try {
        block()
    } catch (e: Exception) {
        val retryCount = delivery.message.properties.headers?.xDeathCount ?: 0L
        val tryAgain = retryCount < maxXDeathCount
        val reason = e.message ?: "Unknown error"
        if (tryAgain) return MessageHandlerResult.Failure(reason, requeue = false) // Reject to dlx
        if (dead) sendToDeadLetterQueue(delivery, reason)
        return MessageHandlerResult.Success // Moved to dead-letter queue or discarded
    }
}

/**
 * Sends the given message delivery to the dead-letter queue with updated headers.
 *
 * @param delivery The message delivery to be sent to the dead-letter queue.
 * @param reason The reason for sending the message to the dead-letter queue.
 */
suspend fun AMQPChannel.sendToDeadLetterQueue(
    delivery: AMQPResponse.Channel.Message.Delivery,
    reason: String,
) {
    val deadExchange = delivery.message.exchange + "-dead"
    val updatedHeaders = (delivery.message.properties.headers ?: emptyMap()).toMutableMap()
    updatedHeaders["x-failed-reason"] = Field.LongString(reason)
    updatedHeaders["x-failed-at"] = Field.Long(Clock.System.now().toEpochMilliseconds())

    val newProps = delivery.message.properties.copy(headers = updatedHeaders)
    basicPublish(
        body = delivery.message.body,
        exchange = deadExchange,
        routingKey = delivery.message.routingKey,
        properties = newProps
    )
}

/**
 * Retrieves the x-death count from the message headers.
 */
val Map<String, Field>.xDeathCount: Long?
    get() {
        val xDeathArray = get("x-death") as? Field.Array
        val xDeath = xDeathArray?.value?.firstOrNull() as? Field.Table
        return (xDeath?.value?.get("count") as? Field.Long)?.value
    }

/**
 * Retrieves the current request ID from the coroutine context, if available.
 *
 * @return A map containing the request ID header, or an empty map if not available.
 */
suspend fun mapOfRequestId(): Map<String, Field.LongString> {
    return currentCoroutineContext()[KtorCallIdContextElement]
        ?.let { mapOf(HttpHeaders.XRequestId to Field.LongString(it.callId)) }
        ?: emptyMap()
}

/**
 * Executes a block of code with a specified call ID in the coroutine context, if the call ID is present.
 *
 * @param block The block of code to execute.
 */
suspend fun AMQPResponse.Channel.Message.Delivery.withCallId(block: suspend () -> Unit) {
    val callId = message.properties.headers?.get(HttpHeaders.XRequestId)?.let {
        when (it) {
            is Field.LongString -> it.value
            else -> null
        }
    }
    callId?.let { withCallId(it, block) } ?: block()
}

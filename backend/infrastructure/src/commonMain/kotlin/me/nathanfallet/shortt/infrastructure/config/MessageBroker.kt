package me.nathanfallet.shortt.infrastructure.config

import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import me.nathanfallet.shortt.infrastructure.messaging.MessageBroker
import me.nathanfallet.shortt.infrastructure.messaging.RabbitMQFactory
import me.nathanfallet.shortt.infrastructure.messaging.handlers.LinkExpiredHandler
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureMessageBroker() = runBlocking {
    val rabbitMQFactory by inject<RabbitMQFactory>()
    rabbitMQFactory.initialize()
    val messageBroker by inject<MessageBroker>()
    messageBroker.startConsuming("", get<LinkExpiredHandler>())
}

package me.nathanfallet.shortt.infrastructure.messaging.handlers

import me.nathanfallet.shortt.infrastructure.messaging.MessageHandler

class LinkExpiredHandler(

) : MessageHandler {
    override suspend fun invoke(routingKey: String, body: String) {

    }
}

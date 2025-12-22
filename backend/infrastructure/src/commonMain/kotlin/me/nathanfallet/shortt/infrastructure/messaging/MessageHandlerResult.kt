package me.nathanfallet.shortt.infrastructure.messaging

/**
 * Represents the result of handling a message.
 */
sealed class MessageHandlerResult {
    /**
     * Indicates that the message was handled successfully.
     */
    object Success : MessageHandlerResult()

    /**
     * Indicates that the message handling failed with a reason.
     */
    data class Failure(
        /**
         * The reason for the failure.
         */
        val reason: String,
        /**
         * Whether to requeue the message for later processing.
         */
        val requeue: Boolean = false,
    ) : MessageHandlerResult()
}

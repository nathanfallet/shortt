package me.nathanfallet.shortt.infrastructure.extensions

/**
 * Context information for handling a message with retry and dead-letter logic.
 */
class HandleWithRetryAndDeadContext(
    /**
     * The number of times the message has been retried so far.
     * This can be used to decide when to stop retrying and move the message to the dead-letter queue.
     */
    val retryCount: Long,
    /**
     * Whether the message should be retried again.
     * This is determined by the retry policy and the current retry count.
     */
    val tryAgain: Boolean,
    /**
     * If tryAgain is false, this indicates if the message should be moved to the dead-letter queue or if it should be discarded.
     */
    val dead: Boolean,
)

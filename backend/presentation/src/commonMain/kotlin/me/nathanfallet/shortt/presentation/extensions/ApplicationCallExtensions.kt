package me.nathanfallet.shortt.presentation.extensions

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import me.nathanfallet.shortt.domain.exceptions.auth.InvalidTokenException
import kotlin.uuid.Uuid

fun ApplicationCall.userIdOrNull(): Uuid? {
    return principal<JWTPrincipal>()?.payload?.subject?.let(Uuid::parse)
}

fun ApplicationCall.userId(): Uuid {
    return userIdOrNull() ?: throw InvalidTokenException()
}

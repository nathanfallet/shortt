package me.nathanfallet.shortt.presentation.extensions

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun ApplicationCall.userIdOrNull(): Uuid? {
    return principal<JWTPrincipal>()?.payload?.subject?.let(Uuid::parse)
}

@OptIn(ExperimentalUuidApi::class)
fun ApplicationCall.userId(): Uuid {
    return userIdOrNull() ?: error("Invalid user ID") // TODO: return a 401 Unauthorized response
}

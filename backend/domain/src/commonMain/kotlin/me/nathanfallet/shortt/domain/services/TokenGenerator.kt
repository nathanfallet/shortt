package me.nathanfallet.shortt.domain.services

import me.nathanfallet.shortt.domain.models.auth.TokenType
import kotlin.uuid.Uuid

interface TokenGenerator {
    fun generateToken(userId: Uuid, type: TokenType): String
}

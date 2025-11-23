package me.nathanfallet.shortt.domain.models.users

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class User(
    val id: Uuid,
)

package me.nathanfallet.shortt.domain.models.links

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Link(
    val id: Uuid,
    val userId: Uuid,
    // TODO: Add more
)

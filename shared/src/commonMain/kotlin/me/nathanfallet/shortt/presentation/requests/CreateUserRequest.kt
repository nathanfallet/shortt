package me.nathanfallet.shortt.presentation.requests

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Serializable
@JsExport
data class CreateUserRequest(
    val name: String,
)

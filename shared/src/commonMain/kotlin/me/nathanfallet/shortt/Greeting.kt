package me.nathanfallet.shortt

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
class Greeting {
    fun greet(): String {
        return "Hello, World!"
    }
}

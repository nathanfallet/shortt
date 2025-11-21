package me.nathanfallet.shortt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    application
}

group = "me.nathanfallet.shortt"
version = "1.0.0"
application {
    mainClass.set("me.nathanfallet.shortt.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.backend.api)
    implementation(projects.backend.domain)
    implementation(projects.backend.infrastructure)
    implementation(projects.backend.presentation)

    implementation(libs.logback)
    implementation(libs.ktor.serverContentNegotiation)
    implementation(libs.ktor.serializationKotlinxJson)
    implementation(libs.ktor.serverMetricsMicrometer)
    implementation(libs.ktor.serverNetty)
    implementation(libs.koin.ktor)
    implementation(libs.mysql)

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}

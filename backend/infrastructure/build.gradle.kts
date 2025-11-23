plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.uuid.ExperimentalUuidApi")
            }
        }
        commonMain.dependencies {
            api(projects.backend.api)
            api(projects.backend.domain)

            api(libs.koin.ktor)
            api(libs.ktor.serverCore)
            api(libs.ktor.serverMetricsMicrometer)
            api(libs.opentelemetry.api)
            api(libs.opentelemetry.sdk)
            api(libs.opentelemetry.sdk.extension.autoconfigure)
            api(libs.opentelemetry.exporter.otlp)
            api(libs.opentelemetry.extension.kotlin)
            api(libs.opentelemetry.semconv)
            api(libs.opentelemetry.ktor)
            api(libs.opentelemetry.micrometer)
            api(libs.opentelemetry.jdbc)
            api(libs.exposed.core)
            api(libs.exposed.jdbc)
            api(libs.mysql)
            api(libs.hikari)
            api(libs.bcrypt)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


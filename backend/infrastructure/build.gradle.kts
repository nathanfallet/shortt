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

            api(libs.opentelemetry.api)
            api(libs.opentelemetry.sdk)
            api(libs.opentelemetry.sdk.extension.autoconfigure)
            api(libs.opentelemetry.exporter.otlp)
            api(libs.opentelemetry.extension.kotlin)
            api(libs.opentelemetry.semconv)
            api(libs.opentelemetry.ktor)
            api(libs.opentelemetry.micrometer)
            api(libs.exposed.core)
            api(libs.exposed.jdbc)
            api(libs.bcrypt)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kover)
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
            api(libs.ktor.serverContentNegotiation)
            api(libs.ktor.serializationKotlinxJson)
            api(libs.ktor.serverResources)
            api(libs.ktor.serverStatusPages)
            api(libs.ktor.serverRequestValidation)
            api(libs.ktor.serverAuthJwt)
            api(libs.ktor.serverCors)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.serverTestHost)
            implementation(libs.ktor.clientCore)
            implementation(libs.ktor.clientContentNegotiation)
            implementation(libs.mockk)
            implementation(projects.backend.client)
        }
    }
}

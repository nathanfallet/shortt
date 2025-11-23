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

            api(libs.ktor.serverCore)
            api(libs.ktor.serverResources)
            api(libs.ktor.serverAuthJwt)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kover)
    application
}

application {
    mainClass.set("me.nathanfallet.shortt.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
        localImageName.set("shortt")
        findProperty("imageTag")?.let { imageTag.set(it.toString()) }

        externalRegistry.set(
            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
                appName = provider { "shortt" },
                username = provider { "nathanfallet" },
                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
            )
        )
    }
}

dependencies {
    implementation(projects.backend.api)
    implementation(projects.backend.domain)
    implementation(projects.backend.infrastructure)
    implementation(projects.backend.presentation)

    implementation(libs.logback)
    implementation(libs.ktor.serverNetty)

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}

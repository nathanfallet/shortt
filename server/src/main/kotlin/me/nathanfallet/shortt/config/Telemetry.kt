package me.nathanfallet.shortt.config

import io.ktor.server.application.*
import io.ktor.server.metrics.micrometer.*
import io.opentelemetry.instrumentation.ktor.v3_0.KtorServerTelemetry
import io.opentelemetry.instrumentation.micrometer.v1_5.OpenTelemetryMeterRegistry
import me.nathanfallet.shortt.infrastructure.observability.TelemetryFactory
import org.koin.ktor.ext.inject

fun Application.configureTelemetry() {
    val openTelemetryFactory by inject<TelemetryFactory>()
    install(KtorServerTelemetry) {
        setOpenTelemetry(openTelemetryFactory.getOpenTelemetry())
    }
    val appMicrometerRegistry = OpenTelemetryMeterRegistry.create(openTelemetryFactory.getOpenTelemetry())
    install(MicrometerMetrics) {
        registry = appMicrometerRegistry
    }
}

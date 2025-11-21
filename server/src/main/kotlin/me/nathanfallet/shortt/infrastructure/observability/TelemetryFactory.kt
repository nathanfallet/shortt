package me.nathanfallet.shortt.infrastructure.observability

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.logs.LoggerProvider
import io.opentelemetry.api.metrics.Meter
import io.opentelemetry.api.trace.Tracer

interface TelemetryFactory {

    val sdk: OpenTelemetry
    fun getTracer(): Tracer
    fun getMeter(): Meter
    fun getLoggerProvider(): LoggerProvider

}

package me.nathanfallet.shortt.infrastructure.observability

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.logs.LoggerProvider
import io.opentelemetry.api.metrics.Meter
import io.opentelemetry.api.trace.Tracer

interface TelemetryFactory {

    fun getOpenTelemetry(): OpenTelemetry
    fun getTracer(): Tracer
    fun getMeter(): Meter
    fun getLoggerProvider(): LoggerProvider

}

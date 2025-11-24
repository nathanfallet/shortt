package me.nathanfallet.shortt.infrastructure.observability

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.logs.LoggerProvider
import io.opentelemetry.api.metrics.Meter
import io.opentelemetry.api.trace.Tracer

/**
 * Factory interface for obtaining OpenTelemetry components.
 */
interface TelemetryFactory {
    /**
     * Gets the OpenTelemetry instance.
     */
    fun getOpenTelemetry(): OpenTelemetry

    /**
     * Gets the Tracer instance.
     */
    fun getTracer(): Tracer

    /**
     * Gets the Meter instance.
     */
    fun getMeter(): Meter

    /**
     * Gets the LoggerProvider instance.
     */
    fun getLoggerProvider(): LoggerProvider
}

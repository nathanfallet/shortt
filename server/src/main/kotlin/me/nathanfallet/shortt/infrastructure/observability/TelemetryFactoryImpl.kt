package me.nathanfallet.shortt.infrastructure.observability

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.logs.LoggerProvider
import io.opentelemetry.api.metrics.Meter
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk
import io.opentelemetry.semconv.ServiceAttributes

class TelemetryFactoryImpl : TelemetryFactory {

    override val sdk: OpenTelemetrySdk by lazy {
        AutoConfiguredOpenTelemetrySdk.builder()
            .addResourceCustomizer { oldResource, _ ->
                oldResource.toBuilder()
                    .putAll(oldResource.attributes)
                    .put(ServiceAttributes.SERVICE_NAME, "shortt")
                    .put("deployment.environment", "production")
                    .build()
            }
            .build()
            .openTelemetrySdk
            .also { GlobalOpenTelemetry.set(it) }
    }

    override fun getTracer(): Tracer = sdk.getTracer("shortt")
    override fun getMeter(): Meter = sdk.getMeter("shortt")
    override fun getLoggerProvider(): LoggerProvider = sdk.logsBridge

}

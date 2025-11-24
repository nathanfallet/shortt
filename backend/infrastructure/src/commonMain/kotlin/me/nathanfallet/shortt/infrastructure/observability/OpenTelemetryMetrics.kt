package me.nathanfallet.shortt.infrastructure.observability

import me.nathanfallet.shortt.domain.services.MetricsCollector

/**
 * Implementation of [MetricsCollector] using OpenTelemetry.
 */
class OpenTelemetryMetrics(
    telemetryFactory: TelemetryFactory,
) : MetricsCollector {

    private val linksClickedCounter = telemetryFactory.getMeter()
        .counterBuilder("links_clicked")
        .setDescription("Number of link clicks")
        .setUnit("{click}")
        .build()

    override fun recordLinkClicked() {
        linksClickedCounter.add(1)
    }

}

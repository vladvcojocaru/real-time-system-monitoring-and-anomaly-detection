package com.vlad.metrics.runnable;

import com.vlad.metrics.kafka.CpuMetricProducer;
import com.vlad.metrics.models.CpuMetric;
import com.vlad.metrics.services.CpuMetricsService;

public class CpuMetricProducerRunnable implements Runnable{
    private final CpuMetricsService cpuMetricsService;
    private final CpuMetricProducer cpuMetricProducer;

    public CpuMetricProducerRunnable(CpuMetricsService cpuMetricsService, CpuMetricProducer cpuMetricProducer) {
        this.cpuMetricsService = cpuMetricsService;
        this.cpuMetricProducer = cpuMetricProducer;
    }

    @Override
    public void run(){
        try {
            // Continuous loop to collect and send CPU metrics at regular intervals.
            while (true) {
                // Fetch the current CPU metrics (total load and per-core loads).
                CpuMetric cpuMetric = cpuMetricsService.getCpuMetrics();

                // Send the collected CPU metrics to Kafka.
                cpuMetricProducer.sendCpuMetric(cpuMetric);

                // Wait for 1 second before collecting the next metric.
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Handle interruptions to the main thread (e.g., during shutdown).
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

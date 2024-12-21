package com.vlad.metrics.producer.runnable;

import com.vlad.metrics.producer.kafka.CpuMetricProducer;
//import com.vlad.metrics.models.old_models.CpuMetric;
import com.vlad.metrics.models.CpuMetric;
import com.vlad.metrics.producer.services.CpuMetricService;

public class CpuMetricProducerRunnable implements Runnable{
    private final CpuMetricService cpuMetricService;
    private final CpuMetricProducer cpuMetricProducer;

    public CpuMetricProducerRunnable(CpuMetricService cpuMetricService, CpuMetricProducer cpuMetricProducer) {
        this.cpuMetricService = cpuMetricService;
        this.cpuMetricProducer = cpuMetricProducer;
    }

    @Override
    public void run(){
        try {
            // Continuous loop to collect and send CPU metrics at regular intervals.
            while (true) {
                // Fetch the current CPU metrics (total load and per-core loads).
                CpuMetric cpuMetric = cpuMetricService.getCpuMetrics();

                // Send the collected CPU metrics to Kafka.
                cpuMetricProducer.sendMetric(cpuMetric);

                // Wait for 1 second before collecting the next metric.
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Handle interruptions to the main thread (e.g., during shutdown).
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

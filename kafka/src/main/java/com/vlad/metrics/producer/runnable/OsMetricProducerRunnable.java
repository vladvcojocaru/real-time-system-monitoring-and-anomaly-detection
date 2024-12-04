package com.vlad.metrics.producer.runnable;


import com.vlad.metrics.producer.kafka.OsMetricProducer;
import com.vlad.metrics.models.OsMetric;
import com.vlad.metrics.producer.services.OsMetricService;

public class OsMetricProducerRunnable implements Runnable{
    private final OsMetricService osMetricService;
    private final OsMetricProducer osMetricProducer;

    public OsMetricProducerRunnable(OsMetricService osMetricService, OsMetricProducer osMetricProducer) {
        this.osMetricService = osMetricService;
        this.osMetricProducer = osMetricProducer;
    }

    @Override
    public void run(){
        try {
            // Continuous loop to collect and send CPU metrics at regular intervals.
            while (true) {
                // Fetch the current CPU metrics (total load and per-core loads).
                OsMetric cpuMetric = osMetricService.getOsMetrics();

                // Send the collected CPU metrics to Kafka.
                osMetricProducer.sendMetric(cpuMetric);

                // Wait for 1 second before collecting the next metric.
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Handle interruptions to the main thread (e.g., during shutdown).
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

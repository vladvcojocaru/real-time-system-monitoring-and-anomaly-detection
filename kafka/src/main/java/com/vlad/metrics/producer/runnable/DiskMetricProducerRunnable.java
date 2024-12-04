package com.vlad.metrics.producer.runnable;

import com.vlad.metrics.producer.kafka.DiskMetricProducer;
import com.vlad.metrics.models.DiskMetric;
import com.vlad.metrics.producer.services.DiskMetricService;

public class DiskMetricProducerRunnable implements Runnable {

    private final DiskMetricService diskMetricService;
    private final DiskMetricProducer diskMetricProducer;

    public DiskMetricProducerRunnable(
        DiskMetricService diskMetricService,
        DiskMetricProducer diskMetricProducer
    ) {
        this.diskMetricService = diskMetricService;
        this.diskMetricProducer = diskMetricProducer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                DiskMetric[] diskMetric = diskMetricService.getDiskMetric();

                diskMetricProducer.sendMetrics(diskMetric);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Handle interruptions to the main thread (e.g., during shutdown).
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

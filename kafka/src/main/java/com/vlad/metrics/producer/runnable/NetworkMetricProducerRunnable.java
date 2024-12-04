package com.vlad.metrics.producer.runnable;

import com.vlad.metrics.producer.kafka.NetworkMetricProducer;
import com.vlad.metrics.models.NetworkMetric;
import com.vlad.metrics.producer.services.NetworkMetricService;

public class NetworkMetricProducerRunnable implements Runnable {

    private final NetworkMetricService networkMetricService;
    private final NetworkMetricProducer networkMetricProducer;

    public NetworkMetricProducerRunnable(
        NetworkMetricService networkMetricService,
        NetworkMetricProducer networkMetricProducer
    ) {
        this.networkMetricService = networkMetricService;
        this.networkMetricProducer = networkMetricProducer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                NetworkMetric[] networkMetric =
                    networkMetricService.getNetworkMetric();

                networkMetricProducer.sendMetrics(networkMetric);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Handle interruptions to the main thread (e.g., during shutdown).
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

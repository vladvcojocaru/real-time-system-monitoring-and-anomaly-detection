package com.vlad.metrics.runnable;

import com.vlad.metrics.kafka.MemoryMetricProducer;
import com.vlad.metrics.models.MemoryMetric;
import com.vlad.metrics.services.MemoryMetricService;

public class MemoryMetricProducerRunnable implements Runnable{
    private final MemoryMetricProducer memoryMetricProducer;
    private final MemoryMetricService memoryMetricService;

    public MemoryMetricProducerRunnable(MemoryMetricProducer memoryMetricProducer, MemoryMetricService memoryMetricService) {
        this.memoryMetricProducer = memoryMetricProducer;
        this.memoryMetricService = memoryMetricService;
    }


    @Override
    public void run() {
        try {
            while (true){
                MemoryMetric memoryMetric = memoryMetricService.getMemoryMetrics();

                memoryMetricProducer.sendMetric(memoryMetric);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e){
            // Handle interruptions to the main thread (e.g., during shutdown).
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

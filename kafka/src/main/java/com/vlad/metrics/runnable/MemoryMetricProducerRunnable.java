package com.vlad.metrics.runnable;

import com.vlad.metrics.kafka.MemoryMetricProducer;
import com.vlad.metrics.models.MemoryMetric;
import com.vlad.metrics.services.MemoryMetricService;

public class MemoryMetricProducerRunnable implements Runnable{
    private final MemoryMetricService memoryMetricService;
    private final MemoryMetricProducer memoryMetricProducer;

    public MemoryMetricProducerRunnable(MemoryMetricService memoryMetricService,  MemoryMetricProducer memoryMetricProducer) {
        this.memoryMetricService = memoryMetricService;
        this.memoryMetricProducer = memoryMetricProducer;
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
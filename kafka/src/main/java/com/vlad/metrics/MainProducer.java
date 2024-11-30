package com.vlad.metrics;

import com.vlad.metrics.kafka.*;
import com.vlad.metrics.models.SensorMetric;
import com.vlad.metrics.runnable.*;
import com.vlad.metrics.services.*;

/**
 * Main entry point for the application that produces CPU metrics to Kafka.
 * This class collects real-time CPU usage data and sends it to the configured
 * Kafka topic.
 */
public class MainProducer {

    /**
     * The main method initializes the services and starts the metric collection and
     * publishing loop.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        CpuMetricService cpuMetricService = new CpuMetricService();
        OsMetricService osMetricService = new OsMetricService();
        MemoryMetricService memoryMetricService = new MemoryMetricService();
        DiskMetricService diskMetricService = new DiskMetricService();
        NetworkMetricService networkMetricService = new NetworkMetricService();
        SensorMetricService sensorMetricService = new SensorMetricService();

        CpuMetricProducer cpuMetricProducer = new CpuMetricProducer(
            "cpu-metrics"
        );
        OsMetricProducer osMetricProducer = new OsMetricProducer(
                "os-metrics"
        );
        MemoryMetricProducer memoryMetricProducer = new MemoryMetricProducer(
            "memory-metrics"
        );
        DiskMetricProducer diskMetricProducer = new DiskMetricProducer(
            "disk-metrics"
        );
        NetworkMetricProducer networkMetricProducer = new NetworkMetricProducer(
            "network-metrics"
        );
        SensorMetricProducer sensorMetricProducer = new SensorMetricProducer(
                "sensor-metrics"
        );

        Runnable cpuMetricProducerRunnable = new CpuMetricProducerRunnable(
            cpuMetricService,
            cpuMetricProducer
        );
        Runnable osMetricProducerRunnable = new OsMetricProducerRunnable(
            osMetricService,
            osMetricProducer
        );

        Runnable memoryMetricProducerRunnable =
            new MemoryMetricProducerRunnable(
                memoryMetricService,
                memoryMetricProducer
            );
        Runnable diskMetricProducerRunnable = new DiskMetricProducerRunnable(
            diskMetricService,
            diskMetricProducer
        );
        Runnable networkMetricProducerRunnable =
            new NetworkMetricProducerRunnable(
                networkMetricService,
                networkMetricProducer
            );
        Runnable sensorMetricProducerRunnable = new SensorMetricProducerRunnable(sensorMetricService, sensorMetricProducer);

        Thread cpuMetricProducerThread = new Thread(cpuMetricProducerRunnable);
        Thread osMetricProducerThread = new Thread(osMetricProducerRunnable);
        Thread memoryMetricProducerThread = new Thread(
            memoryMetricProducerRunnable
        );
        Thread diskMetricProducerThread = new Thread(
            diskMetricProducerRunnable
        );
        Thread networkMetricProducerThread = new Thread(
            networkMetricProducerRunnable
        );
        Thread sensorMetricProducerThread = new Thread(sensorMetricProducerRunnable);

        cpuMetricProducerThread.start();
        osMetricProducerThread.start();
        memoryMetricProducerThread.start();
        diskMetricProducerThread.start();
        networkMetricProducerThread.start();
        sensorMetricProducerThread.start();

        Runtime.getRuntime()
            .addShutdownHook(
                new Thread(() -> {
                    System.out.println("Shutting down...");
                    cpuMetricProducer.close();
                    cpuMetricProducer.close();
                    osMetricProducer.close();
                    memoryMetricProducer.close();
                    diskMetricProducer.close();
                    networkMetricProducer.close();
                    sensorMetricProducer.close();
                })
            );

        try {
            cpuMetricProducerThread.join();
            osMetricProducerThread.join();
            memoryMetricProducerThread.join();
            diskMetricProducerThread.join();
            networkMetricProducerThread.join();
            sensorMetricProducerThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}

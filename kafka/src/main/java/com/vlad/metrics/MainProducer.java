package com.vlad.metrics;

import com.vlad.metrics.kafka.CpuMetricProducer;
import com.vlad.metrics.kafka.MemoryMetricProducer;
import com.vlad.metrics.kafka.OsMetricProducer;
import com.vlad.metrics.runnable.CpuMetricProducerRunnable;
import com.vlad.metrics.runnable.MemoryMetricProducerRunnable;
import com.vlad.metrics.runnable.OsMetricProducerRunnable;
import com.vlad.metrics.services.CpuMetricService;
import com.vlad.metrics.services.MemoryMetricService;
import com.vlad.metrics.services.OsMetricService;

import java.io.IOException;

/**
 * Main entry point for the application that produces CPU metrics to Kafka.
 * This class collects real-time CPU usage data and sends it to the configured Kafka topic.
 */
public class MainProducer {

    // The Kafka topic where CPU metrics will be published.
    private final static String CPU_TOPIC = "cpu-metrics";

    // Script that starts the server and creates the necessary topics
    private static final String START_SCRIPT = "src/main/resources/start.sh";

    /**
     * The main method initializes the services and starts the metric collection and publishing loop.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Run the start.sh script
        // runStartupScript();

        // Initialize Services for collecting OSHI metrics
        CpuMetricService cpuMetricService = new CpuMetricService();
        OsMetricService osMetricService = new OsMetricService();
        MemoryMetricService memoryMetricService = new MemoryMetricService();

        // Initialize Producers to send metrics to Kafka
        CpuMetricProducer cpuMetricProducer = new CpuMetricProducer("cpu-metrics");
        OsMetricProducer osMetricProducer = new OsMetricProducer("os-metrics");
        MemoryMetricProducer memoryMetricProducer = new MemoryMetricProducer("memory-metrics");

        // Create Runnable threads for each producer
        Runnable cpuMetricProducerRunnable = new CpuMetricProducerRunnable(cpuMetricService, cpuMetricProducer);
        Runnable osMetricProducerRunnable = new OsMetricProducerRunnable(osMetricService, osMetricProducer);
        Runnable memoryMetricProducerRunnable = new MemoryMetricProducerRunnable(memoryMetricService, memoryMetricProducer);

        // Start the producer threads
        Thread cpuMetricProducerThread = new Thread(cpuMetricProducerRunnable);
        Thread osMetricProducerThread = new Thread(osMetricProducerRunnable);
        Thread memoryMetricProducerThread = new Thread(memoryMetricProducerRunnable);

        cpuMetricProducerThread.start();
        osMetricProducerThread.start();
        memoryMetricProducerThread.start();

        // Add a shutdown hook to gracefully close the Kafka producer on application exit.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            cpuMetricProducer.close();
        }));

        try {
            // Main thread can be used for other tasks or simply to wait until shutdown
            cpuMetricProducerThread.join();
            osMetricProducerThread.join();
            memoryMetricProducerThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }

    }

    /**
     * Runs the `start.sh` script before starting the application logic.
     */
    private static void runStartupScript() {
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", START_SCRIPT);
        try {
            System.out.println("Executing startup script: " + START_SCRIPT);
            Process process = processBuilder.start();
            int exitCode = process.waitFor(); // Wait for the script to finish
            if (exitCode == 0) {
                System.out.println("Startup script executed successfully.");
            } else {
                System.err.println("Startup script failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to execute startup script: " + e.getMessage());
        }
    }
}

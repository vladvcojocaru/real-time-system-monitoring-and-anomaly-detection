package com.vlad.metrics;

import com.vlad.metrics.kafka.CpuMetricProducer;
import com.vlad.metrics.models.CpuMetric;
import com.vlad.metrics.runnable.CpuMetricProducerRunnable;
import com.vlad.metrics.services.CpuMetricsService;

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
        CpuMetricsService cpuMetricsService = new CpuMetricsService();

        // Initialize Producers to send metrics to Kafka
        CpuMetricProducer cpuMetricProducer = new CpuMetricProducer(CPU_TOPIC);

        // Create Runnable threads for each producer
        Runnable cpuMetricProducerRunnable = new CpuMetricProducerRunnable(cpuMetricsService, cpuMetricProducer);

        // Start the producer threads
        Thread cpuMetricProducerThread = new Thread(cpuMetricProducerRunnable);

        cpuMetricProducerThread.start();

        // Add a shutdown hook to gracefully close the Kafka producer on application exit.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            cpuMetricProducer.close();
        }));

        try {
            // Main thread can be used for other tasks or simply to wait until shutdown
            cpuMetricProducerThread.join();
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

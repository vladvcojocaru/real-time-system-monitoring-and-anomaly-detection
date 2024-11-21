package com.vlad.metrics;

import com.vlad.metrics.kafka.CpuMetricProducer;
import com.vlad.metrics.models.CpuMetric;
import com.vlad.metrics.services.CpuMetricsService;

/**
 * Main entry point for the application that produces CPU metrics to Kafka.
 * This class collects real-time CPU usage data and sends it to the configured Kafka topic.
 */
public class MainProducer {

    // The Kafka topic where CPU metrics will be published.
    private final static String CPU_TOPIC = "cpu-metrics";

    /**
     * The main method initializes the services and starts the metric collection and publishing loop.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Service to collect CPU metrics using OSHI.
        CpuMetricsService metricsService = new CpuMetricsService();

        // Producer to send CPU metrics to the Kafka topic.
        CpuMetricProducer cpuMetricProducer = new CpuMetricProducer(CPU_TOPIC);

        // Add a shutdown hook to gracefully close the Kafka producer on application exit.
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("Shutting down...");
            cpuMetricProducer.close();
        }));

        try{
            // Continuous loop to collect and send CPU metrics at regular intervals.
            while (true){
                // Fetch the current CPU metrics (total load and per-core loads).
                CpuMetric cpuMetric= metricsService.getCpuMetrics();

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

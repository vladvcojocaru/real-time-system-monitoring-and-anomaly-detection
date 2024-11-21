package com.vlad.metrics;

import com.vlad.metrics.kafka.CpuMetricProducer;
import com.vlad.metrics.models.CpuMetric;
import com.vlad.metrics.services.CpuMetricsService;

public class MainProducer {
    private final static String CPU_TOPIC = "cpu-metrics";
    public static void main(String[] args) {
        CpuMetricsService metricsService = new CpuMetricsService();
        CpuMetricProducer cpuMetricProducer = new CpuMetricProducer(CPU_TOPIC);

        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("Shutting down...");
            cpuMetricProducer.close();
        }));

        try{
            while (true){
                // Fetch the current cpu metric
                CpuMetric cpuMetric= metricsService.getCpuMetrics();

                // Send the metrics to Kafka
                cpuMetricProducer.sendCpuMetric(cpuMetric);

                // Sleep 1 second before sending the new metric
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

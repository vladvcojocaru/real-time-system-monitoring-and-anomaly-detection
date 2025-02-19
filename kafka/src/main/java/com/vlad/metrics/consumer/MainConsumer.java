package com.vlad.metrics.consumer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.vlad.metrics.consumer.kafka.KafkaConsumerConfig;
import com.vlad.metrics.models.*;
import com.vlad.metrics.prometheus.PrometheusMetricManager;
import com.vlad.metrics.util.Constants;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import io.prometheus.client.exporter.HTTPServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MainConsumer {

    public static void main(String[] args) throws IOException {
         KafkaConsumer<String, byte[]> consumer = KafkaConsumerConfig.createConsumer(Constants.METRICS_CONSUMER_GROUP);

         consumer.subscribe(Arrays.asList(
                 Constants.CPU_METRICS_TOPIC,
                 Constants.NETWORK_METRICS_TOPIC,
                 Constants.OS_METRICS_TOPIC,
                 Constants.DISK_METRICS_TOPIC,
                 Constants.MEMORY_METRICS_TOPIC,
                 Constants.SENSOR_METRICS_TOPIC
         ));

        System.out.println("Subscribed to metrics topics");

        PrometheusMetricManager.startMetricsServer(1234);

        try{
            while(true){
                ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(5000));

                for (ConsumerRecord<String, byte[]> record: records){
                    String topic = record.topic();
                    byte[] value = record.value();

                    switch (topic) {
                        case Constants.CPU_METRICS_TOPIC:
                            processCpuMetrics(value);
                            break;
                        case Constants.DISK_METRICS_TOPIC:
                            processDiskMetrics(value);
                            break;
                        case Constants.MEMORY_METRICS_TOPIC:
                            processMemoryMetrics(value);
                            break;
                        case Constants.NETWORK_METRICS_TOPIC:
                            processNetworkMetrics(value);
                            break;
                        case Constants.OS_METRICS_TOPIC:
                            processOsMetrics(value);
                            break;
                        case Constants.SENSOR_METRICS_TOPIC:
                            processSensorMetrics(value);
                            break;
                        default:
                            System.err.println("Unknown topic: " + topic);
                    }
                }
            }
        } finally {
            consumer.close();
        }
    }
    private static void processCpuMetrics(byte[] value) {
        try {
            CpuMetric cpuMetric = CpuMetric.parseFrom(value);
            PrometheusMetricManager.updateCpuMetrics(cpuMetric);
            System.out.println("CPU Metric: " + cpuMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process CPU metric: " + e.getMessage());
        }
    }

    private static void processDiskMetrics(byte[] value) {
        try {
            DiskMetric diskMetric = DiskMetric.parseFrom(value);
            PrometheusMetricManager.updateDiskMetrics(diskMetric);
            System.out.println("Disk Metric: " + diskMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Disk metric: " + e.getMessage());
        }
    }

    private static void processMemoryMetrics(byte[] value) {
        try {
            MemoryMetric memoryMetric = MemoryMetric.parseFrom(value);
            PrometheusMetricManager.updateMemoryMetrics(memoryMetric);
            System.out.println("Memory Metric: " + memoryMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Memory metric: " + e.getMessage());
        }
    }

    private static void processNetworkMetrics(byte[] value) {
        try {
            NetworkMetric networkMetric = NetworkMetric.parseFrom(value);
            PrometheusMetricManager.updateNetworkMetrics(networkMetric);
            System.out.println("Network Metric: " + networkMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Network metric: " + e.getMessage());
        }
    }

    private static void processOsMetrics(byte[] value) {
        try {
            OsMetric osMetric = OsMetric.parseFrom(value);
            PrometheusMetricManager.updateOsMetrics(osMetric);
            System.out.println("OS Metric: " + osMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process OS metric: " + e.getMessage());
        }
    }

    private static void processSensorMetrics(byte[] value) {
        try {
            SensorMetric sensorMetric = SensorMetric.parseFrom(value);
            PrometheusMetricManager.updateSensorMetrics(sensorMetric);
            System.out.println("Sensor Metric: " + sensorMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Sensor metric: " + e.getMessage());
        }
    }
}

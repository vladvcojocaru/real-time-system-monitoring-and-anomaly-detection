package com.vlad.metrics.consumer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.vlad.metrics.consumer.kafka.KafkaConsumerConfig;
import com.vlad.metrics.models.*;
import com.vlad.metrics.prometheus.PrometheusMetricManager;
import com.vlad.metrics.util.Constants;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MainConsumer {

    public static void main(String[] args) throws IOException {
         KafkaConsumer<String, byte[]> consumer = KafkaConsumerConfig.createConsumer(Constants.METRICS_CONSUMER_GROUP);

        try (consumer) {
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
            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(5000));

                for (ConsumerRecord<String, byte[]> record : records) {
                    String topic = record.topic();
                    String key = record.key();
                    byte[] value = record.value();

                    switch (topic) {
                        case Constants.CPU_METRICS_TOPIC:
                            processCpuMetrics(value, key);
                            break;
                        case Constants.DISK_METRICS_TOPIC:
                            processDiskMetrics(value, key);
                            break;
                        case Constants.MEMORY_METRICS_TOPIC:
                            processMemoryMetrics(value, key);
                            break;
                        case Constants.NETWORK_METRICS_TOPIC:
                            processNetworkMetrics(value, key);
                            break;
                        case Constants.OS_METRICS_TOPIC:
                            processOsMetrics(value, key);
                            break;
                        case Constants.SENSOR_METRICS_TOPIC:
                            processSensorMetrics(value, key);
                            break;

                        default:
                            System.err.println("Unknown topic: " + topic);
                    }
                }
            }
        }
    }
    private static void processCpuMetrics(byte[] value, String key) {
        try {
            CpuMetric cpuMetric = CpuMetric.parseFrom(value);
            PrometheusMetricManager.updateCpuMetrics(cpuMetric, key);
            System.out.println("CPU Metric from machine " + key + ":\n" + cpuMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process CPU metric from machine " + key + ":\n " + e.getMessage());
        }
    }

    private static void processDiskMetrics(byte[] value, String key) {
        try {
            DiskMetric diskMetric = DiskMetric.parseFrom(value);
            PrometheusMetricManager.updateDiskMetrics(diskMetric, key);
            System.out.println("Disk Metric from machine " + key + ":\n" + diskMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Disk metric from machine " + key + ":\n" + e.getMessage());
        }
    }

    private static void processMemoryMetrics(byte[] value, String key) {
        try {
            MemoryMetric memoryMetric = MemoryMetric.parseFrom(value);
            PrometheusMetricManager.updateMemoryMetrics(memoryMetric, key);
            System.out.println("Memory Metric from machine " + key + ":\n" + memoryMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Memory metric from machine " + key + ":\n" + e.getMessage());
        }
    }

    private static void processNetworkMetrics(byte[] value, String key) {
        try {
            NetworkMetric networkMetric = NetworkMetric.parseFrom(value);
            PrometheusMetricManager.updateNetworkMetrics(networkMetric, key);
            System.out.println("Network Metric from machine " + key + ":\n" + networkMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Network metric from machine " + key + ":\n" + e.getMessage());
        }
    }

    private static void processOsMetrics(byte[] value, String key) {
        try {
            OsMetric osMetric = OsMetric.parseFrom(value);
            PrometheusMetricManager.updateOsMetrics(osMetric, key);
            System.out.println("OS Metric from machine " + key + ":\n" + osMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process OS metric from machine " + key + ":\n" + e.getMessage());
        }
    }

    private static void processSensorMetrics(byte[] value, String key) {
        try {
            SensorMetric sensorMetric = SensorMetric.parseFrom(value);
            PrometheusMetricManager.updateSensorMetrics(sensorMetric, key);
            System.out.println("Sensor Metric from machine " + key + ":\n" + sensorMetric);
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to process Sensor metric from machine " + key + ":\n" + e.getMessage());
        }
    }
}

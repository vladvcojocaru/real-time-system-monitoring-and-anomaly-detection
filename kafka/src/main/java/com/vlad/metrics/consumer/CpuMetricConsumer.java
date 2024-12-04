package com.vlad.metrics.consumer;

import com.google.gson.Gson;
import com.vlad.metrics.consumer.kafka.KafkaConsumerConfig;
import com.vlad.metrics.models.CpuMetric;
import com.vlad.metrics.util.Constants;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class CpuMetricConsumer {

    public static void main(String[] args) {
        // DONE //
        // Properties props = new Properties();
        // props.setProperty(
        //     ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        //     "localhost:9092"
        // );
        // props.setProperty(
        //     ConsumerConfig.GROUP_ID_CONFIG,
        //     "cpu-metrics-consumer-group"
        // );
        // props.setProperty(
        //     ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        //     "org.apache.kafka.common.serialization.StringDeserializer"
        // );
        // props.setProperty(
        //     ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        //     "org.apache.kafka.common.serialization.StringDeserializer"
        // );
        // props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // DONE //

        KafkaConsumer<String, String> consumer =
            KafkaConsumerConfig.createConsumer("group1");

        consumer.subscribe(Collections.singleton(Constants.CPU_METRICS_TOPIC));
        consumer.subscribe(Collections.singleton(Constants.DISK_METRICS_TOPIC));
        consumer.subscribe(
            Collections.singleton(Constants.MEMORY_METRICS_TOPIC)
        );
        consumer.subscribe(
            Collections.singleton(Constants.NETWORK_METRICS_TOPIC)
        );
        consumer.subscribe(Collections.singleton(Constants.OS_METRICS_TOPIC));
        consumer.subscribe(
            Collections.singleton(Constants.SENSOR_METRICS_TOPIC)
        );
        Gson gson = new Gson();

        System.out.println("Listening for CpuMetric data...");

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(
                    Duration.ofMillis(100)
                );

                for (ConsumerRecord<String, String> record : records) {
                    CpuMetric cpuMetric = gson.fromJson(
                        record.value(),
                        CpuMetric.class
                    );
                    System.out.println("Consumed Metric: " + cpuMetric);

                    // Example: Forward to redis, db, process further the data etc.
                    processMetric(cpuMetric);
                }
            }
        } finally {
            consumer.close();
        }
    }

    private static void processMetric(CpuMetric cpuMetric) {
        System.out.println("Processing metric: " + cpuMetric);
    }
}

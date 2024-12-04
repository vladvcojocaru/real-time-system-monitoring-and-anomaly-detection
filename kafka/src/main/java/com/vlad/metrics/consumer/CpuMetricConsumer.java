package com.vlad.metrics.consumer;

import com.google.gson.Gson;
import com.vlad.metrics.consumer.kafka.KafkaConsumerConfig;
import com.vlad.metrics.models.CpuMetric;
import com.vlad.metrics.util.Constants;
import java.time.Duration;
import java.util.Arrays;
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
            KafkaConsumerConfig.createConsumer(
                Constants.METRICS_CONSUMER_GROUP
            );

        consumer.subscribe(
            Arrays.asList(
                Constants.CPU_METRICS_TOPIC,
                Constants.DISK_METRICS_TOPIC,
                Constants.MEMORY_METRICS_TOPIC,
                Constants.NETWORK_METRICS_TOPIC,
                Constants.OS_METRICS_TOPIC,
                Constants.SENSOR_METRICS_TOPIC
            )
        );
        Gson gson = new Gson();

        System.out.println("Listening for CpuMetric data...");

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(
                    Duration.ofMillis(100)
                );

                for (ConsumerRecord<String, String> record : records) {
                    CpuMetric metric = gson.fromJson(
                        record.value(),
                        CpuMetric.class
                    );
                    System.out.println("Consumed Metric: " + metric);

                    // Example: Forward to redis, db, process further the data etc.
                    processMetric(metric);
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

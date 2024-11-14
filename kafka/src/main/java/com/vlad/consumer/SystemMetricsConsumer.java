package com.vlad.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class SystemMetricsConsumer {
    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
    private static final String GROUP_ID = "metrics_consumer_group";

    // Topics
    private static final String CPU_TOPIC = "cpu_metrics";
    private static final String MEMORY_TOPIC = "memory_metrics";
    private static final String DISCK_TOPIC = "disk_metrics";
    private static final String NETWORK_TOPIC = "networks_metrics";

    public static void main(String[] args) {
        // 1. Set up properties for the Kafka consumer
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        // TODO: Use something like protobuf for better performance
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 2. Create a KafkaConsumer instance
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
    }
}

package com.vlad.metrics.consumer.kafka;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerConfig {

    public static KafkaConsumer<String, byte[]> createConsumer(String group) {
        // The Kafka broker(s) to connect to. 'localhost:9092' assumes Kafka is running locally.
        Properties properties = new Properties();
        properties.setProperty(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "192.168.0.101:9092"
        );
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group);
        properties.setProperty(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.StringDeserializer"
        );
        properties.setProperty(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.ByteArrayDeserializer"
        );
        properties.setProperty(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
            "latest"
        );

        // Return a new KafkaProducer instance with the above properties
        return new KafkaConsumer<>(properties);
    }
}

package com.vlad.metrics.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * Provides a configuration utility to create a Kafka producer.
 * This class centralizes the configuration for Kafka producers, ensuring
 * consistent settings across different parts of the application.
 */
public class KafkaProducerConfig {
    /**
     * Creates and returns a KafkaProducer instance with the required configurations.
     * The producer is configured to send String keys and String values to a Kafka cluster.
     *
     * @return A KafkaProducer<String, String> instance configured for the application.
     */
    public static KafkaProducer<String, String> createProducer() {
        // Kafka producer properties
        Properties properties = new Properties();

        // The Kafka broker(s) to connect to. 'localhost:9092' assumes Kafka is running locally.
        properties.put("bootstrap.servers", "localhost:9092");

        // Serializer for the message keys. Configured to use String keys.
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Serializer for the message values. Configured to use String values.
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Return a new KafkaProducer instance with the above properties
        return new KafkaProducer<>(properties);
    }
}

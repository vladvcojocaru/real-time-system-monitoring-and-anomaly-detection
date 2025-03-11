package com.vlad.metrics.producer.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

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
    public static KafkaProducer<String, byte[]> createProducer() {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.101:9092");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");

        return new KafkaProducer<>(properties);
    }
}

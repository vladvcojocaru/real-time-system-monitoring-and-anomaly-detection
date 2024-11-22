package com.vlad.metrics.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * A generic producer for sending metrics to a Kafka topic.
 *
 * @param <T> The type of metric to be sent (e.g., OsMetric, CpuMetric).
 */
public abstract class MetricProducer<T> {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    /**
     * Constructor to initialize the Kafka producer and target topic.
     *
     * @param topic The Kafka topic where metrics will be sent.
     */
    public MetricProducer(String topic) {
        this.producer = KafkaProducerConfig.createProducer();
        this.topic = topic;
    }

    /**
     * Abstract method to convert a metric object to a JSON string.
     * Subclasses must implement this to provide serialization logic.
     *
     * @param metric The metric object to serialize.
     * @return The serialized JSON string.
     */
    protected abstract String serializeMetric(T metric);

    /**
     * Sends a metric to the configured Kafka topic.
     *
     * @param metric The metric object to send.
     */
    public void sendMetric(T metric) {
        String payload = serializeMetric(metric);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, payload);

        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.printf("Sent to topic '%s': %s (partition %d, offset %d)%n",
                        metadata.topic(), payload, metadata.partition(), metadata.offset());
            } else {
                System.err.println("Error sending message: " + exception.getMessage());
            }
        });
    }

    /**
     * Closes the Kafka producer to release resources.
     */
    public void close() {
        producer.close();
    }
}

package com.vlad.metrics.kafka;

import com.vlad.metrics.models.CpuMetric;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * A producer class for sending CPU metrics to a Kafka topic.
 * It uses the KafkaProducer configured in KafkaProducerConfig to send serialized CPU metrics.
 */
public class CpuMetricProducer {

    // KafkaProducer instance for sending messages to Kafka.
    private final KafkaProducer<String, String> producer;

    // The Kafka topic where the CPU metrics will be sent.
    private final String topic;
    // TODO: initialize topic in constructor

    /**
     * Constructor that initializes the Kafka producer and the target topic.
     *
     * @param topic The name of the Kafka topic to which CPU metrics will be sent.
     */
    public CpuMetricProducer(String topic) {
        // Create a producer using the centralized configuration.
        this.producer = KafkaProducerConfig.createProducer();

        // Assign the topic name to this instance.
        this.topic = topic;
    }


    /**
     * Sends a CpuMetric object to the Kafka topic.
     *
     * @param metric The CpuMetric object containing total CPU load and per-core loads.
     */
    public void sendCpuMetric(CpuMetric metric){
        // Serialize the CpuMetric object to a JSON string for Kafka payload.
        String payload = metric.toJson();

        // Create a Kafka ProducerRecord with the topic and serialized payload.
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, payload);

        // Send the message asynchronously.
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                // Log success with topic, payload, partition, and offset information.
                System.out.printf("Sent to topic '%s': %s (partition %d, offset %d)%n",
                        metadata.topic(), payload, metadata.partition(), metadata.offset());
            } else {
                // Log the exception if the message fails to send.
                System.err.println("Error sending message: " + exception.getMessage());
            }
        });
    }

    /**
     * Closes the Kafka producer to release resources.
     * This should be called when the producer is no longer needed, typically during application shutdown.
     */
    public void close(){
        producer.close();
    }
}

package com.vlad.metrics.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public abstract class MetricProducer<T> {
    private final KafkaProducer<String, byte[]> producer;
    private final String topic;
    private final String machineId;

    public MetricProducer(String topic, String machineId) {
        this.producer = KafkaProducerConfig.createProducer();
        this.topic = topic;
        this.machineId = machineId;
    }

    protected abstract byte[] serializeMetric(T metric);

    public void sendMetric(T metric) {
        byte[] payload = serializeMetric(metric);

        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, machineId, payload);

        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.printf("Sent to topic '%s': %s (partition %d, offset %d)%n",
                        metadata.topic(), payload, metadata.partition(), metadata.offset());
            } else {
                System.err.println("Error sending message: " + exception.getMessage());
            }
        });
    }

    public void sendMetrics(T[] metrics) {
        for (T metric : metrics) {
            sendMetric(metric); // Use the existing single-metric method
        }
    }

    public void close() {
        producer.close();
    }
}

package com.vlad.metrics.kafka;

import com.vlad.metrics.models.CpuMetric;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CpuMetricProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;
    // TODO: initialize topic in constructor
    // Constructor that initializes the producer and topic
    public CpuMetricProducer(String topic) {
        this.producer = KafkaProducerConfig.createProducer();
        this.topic = topic;
    }

    // Method to send CpuMetric to kafka
    public void sendCpuMetric(CpuMetric metric){
        String payload = metric.toJson(); // Serialize CpuMetric object to json
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

    public void close(){
        producer.close();
    }
}

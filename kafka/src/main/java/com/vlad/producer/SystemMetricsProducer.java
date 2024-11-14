package com.vlad.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class SystemMetricsProducer {

    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
    private static final String GROUP_ID = "metrics_consumer_group";

    // Topics
    private static final String CPU_TOPIC = "cpu_metrics";
    private static final String MEMORY_TOPIC = "memory_metrics";
    private static final String DISCK_TOPIC = "disk_metrics";
    private static final String NETWORK_TOPIC = "networks_metrics";

    // Test topic
    private static final String TEST_TOPIC = "test_topic";

    public static void main(String[] args) {
        // 1. Set up properties for the Kafka consumer
        Properties properties = new Properties();

        // TODO: Use something like protobuf for better performance
        properties.put("bootstrap.servers", BOOTSTRAP_SERVER);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        // 2. Create a KafkaConsumer instance

        // 3. Send message

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            String key = "key1";
            String value = "Hello kafka";
            for (int i = 0; i < 100; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>(TEST_TOPIC, key + i, value + i);

                // 4. Asynchronously send the record
                RecordMetadata metadata = producer.send(record).get();

                // 5. Print metadata for the sent record
                System.out.printf(
                        "Message sent to topic: %s, partition %d, offset: %d%n\n",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

package com.vlad.metrics.runnable;

import org.apache.kafka.clients.producer.Producer;
import java.util.concurrent.TimeUnit;

/**
 * Abstract runnable class for producing metrics to Kafka.
 *
 * @param <T> The type of metric being produced (e.g., OsMetric, CpuMetric).
 */
public abstract class MetricProducerRunnable<T> implements Runnable {
    private final Producer<String, String> producer;
    private final String topic;
    private final long intervalMillis;

    /**
     * Constructor to initialize the producer and topic.
     *
     * @param producer       The Kafka producer instance.
     * @param topic          The Kafka topic where metrics will be sent.
     * @param intervalMillis The interval between metric collections in milliseconds.
     */
    public MetricProducerRunnable(Producer<String, String> producer, String topic, long intervalMillis) {
        this.producer = producer;
        this.topic = topic;
        this.intervalMillis = intervalMillis;
    }

    /**
     * Fetches the metric to send.
     *
     * @return The collected metric.
     */
    protected abstract T collectMetric();

    /**
     * Serializes the metric into a Kafka-compatible payload.
     *
     * @param metric The metric to serialize.
     * @return The serialized metric as a string.
     */
    protected abstract String serializeMetric(T metric);

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Collect the metric
                T metric = collectMetric();

                // Serialize and send the metric
                String payload = serializeMetric(metric);
                producer.send(new org.apache.kafka.clients.producer.ProducerRecord<>(topic, null, payload), (metadata, exception) -> {
                    if (exception == null) {
                        System.out.printf("Sent to topic '%s': %s (partition %d, offset %d)%n",
                                metadata.topic(), payload, metadata.partition(), metadata.offset());
                    } else {
                        System.err.println("Error sending message: " + exception.getMessage());
                    }
                });

                // Wait before collecting the next metric
                TimeUnit.MILLISECONDS.sleep(intervalMillis);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer thread interrupted: " + e.getMessage());
        } finally {
            producer.close();
        }
    }
}

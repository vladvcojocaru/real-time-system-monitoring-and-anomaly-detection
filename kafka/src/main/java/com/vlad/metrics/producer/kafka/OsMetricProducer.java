package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.OsMetric;

/**
 * Producer class for sending OS metrics to a Kafka topic.
 */
public class OsMetricProducer extends MetricProducer<OsMetric> {

    public OsMetricProducer(String topic) {
        super(topic);
    }

    @Override
    protected byte[] serializeMetric(OsMetric metric) {
        return metric.toByteArray(); // Convert OsMetric to JSON
    }
}

package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.MemoryMetric;

public class MemoryMetricProducer extends MetricProducer<MemoryMetric> {

    public MemoryMetricProducer(String topic) {
        super(topic);
    }

    @Override
    protected String serializeMetric(MemoryMetric metric) {
        return metric.toJson(); // Convert OsMetric to JSON
    }
}

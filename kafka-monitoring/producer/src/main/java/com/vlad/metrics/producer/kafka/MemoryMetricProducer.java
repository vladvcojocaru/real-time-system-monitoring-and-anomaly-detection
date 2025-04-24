package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.MemoryMetric;

public class MemoryMetricProducer extends MetricProducer<MemoryMetric> {

    public MemoryMetricProducer(String topic, String machineId) {
        super(topic, machineId);
    }

    @Override
    protected byte[] serializeMetric(MemoryMetric metric) {
        return metric.toByteArray(); // Convert OsMetric to JSON
    }
}

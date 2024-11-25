package com.vlad.metrics.kafka;

import com.vlad.metrics.models.MemoryMetric;
import com.vlad.metrics.models.OsMetric;

public class MemoryMetricProducer extends MetricProducer<MemoryMetric>{
    public MemoryMetricProducer(String topic) {
        super(topic);
    }

    @Override
    protected String serializeMetric(MemoryMetric metric) {
        return metric.toJson(); // Convert OsMetric to JSON
    }
}

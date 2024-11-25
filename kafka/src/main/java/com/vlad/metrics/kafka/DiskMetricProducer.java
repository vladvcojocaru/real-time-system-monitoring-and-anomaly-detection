package com.vlad.metrics.kafka;

import com.vlad.metrics.models.DiskMetric;
import com.vlad.metrics.models.MemoryMetric;

public class DiskMetricProducer extends MetricProducer<DiskMetric>{
    public DiskMetricProducer(String topic) {
        super(topic);
    }

    @Override
    protected String serializeMetric(DiskMetric metric) {
        return metric.toJson(); // Convert OsMetric to JSON
    }
}

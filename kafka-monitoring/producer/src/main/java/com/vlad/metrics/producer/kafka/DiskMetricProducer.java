package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.DiskMetric;

public class DiskMetricProducer extends MetricProducer<DiskMetric>{
    public DiskMetricProducer(String topic, String machineId) {
        super(topic, machineId);
    }

    @Override
    protected byte[] serializeMetric(DiskMetric metric) {
        return metric.toByteArray();
    }


}

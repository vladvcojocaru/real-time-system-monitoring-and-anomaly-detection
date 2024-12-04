package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.DiskMetric;

public class DiskMetricProducer extends MetricProducer<DiskMetric>{
    public DiskMetricProducer(String topic) {
        super(topic);
    }

    @Override
    protected String serializeMetric(DiskMetric metric) {
        return metric.toJson();
    }


}

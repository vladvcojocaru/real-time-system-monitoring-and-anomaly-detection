package com.vlad.metrics.kafka;

import com.vlad.metrics.models.CpuMetric;


public class CpuMetricProducer extends MetricProducer<CpuMetric>{
    public CpuMetricProducer(String topic){
        super(topic);
    }

    @Override
    protected String serializeMetric(CpuMetric metric) {
        return metric.toJson(); // Convert OsMetric to JSON
    }
}
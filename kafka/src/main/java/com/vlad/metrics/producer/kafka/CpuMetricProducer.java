package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.CpuMetric;


public class CpuMetricProducer extends MetricProducer<CpuMetric>{
    public CpuMetricProducer(String topic){
        super(topic);
    }

    @Override
    protected byte[] serializeMetric(CpuMetric metric) {
        return metric.toByteArray();
    }
}
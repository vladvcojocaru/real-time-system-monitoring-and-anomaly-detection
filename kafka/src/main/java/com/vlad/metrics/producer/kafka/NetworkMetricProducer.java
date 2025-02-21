package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.NetworkMetric;

public class NetworkMetricProducer extends MetricProducer<NetworkMetric> {

    public NetworkMetricProducer(String topic, String machineId) {
        super(topic, machineId);
    }

    @Override
    protected byte[] serializeMetric(NetworkMetric metric) {
        return metric.toByteArray();
    }
}

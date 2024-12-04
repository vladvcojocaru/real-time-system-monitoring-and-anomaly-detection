package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.NetworkMetric;

public class NetworkMetricProducer extends MetricProducer<NetworkMetric> {

    public NetworkMetricProducer(String topic) {
        super(topic);
    }

    @Override
    protected String serializeMetric(NetworkMetric metric) {
        return metric.toJson();
    }
}

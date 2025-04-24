package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.SensorMetric;

public class SensorMetricProducer extends MetricProducer<SensorMetric> {
    public SensorMetricProducer(String topic, String machineId) {
        super(topic, machineId);
    }

    @Override
    protected byte[] serializeMetric(SensorMetric metric) {
        return metric.toByteArray(); // Convert OsMetric to JSON
    }
}
